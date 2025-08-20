#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
SQL数据库表名和字段名大小写转换脚本
功能：将SQL文件中的所有表名和字段名从小写转换为大写
作者：AI助手
日期：2025年
"""

import re
import os
import shutil
from datetime import datetime

def convert_sql_to_uppercase(input_file_path, output_file_path=None):
    """
    将SQL文件中的表名和字段名转换为大写
    
    Args:
        input_file_path (str): 输入SQL文件路径
        output_file_path (str): 输出SQL文件路径，如果为None则覆盖原文件
    """
    
    print(f"开始处理SQL文件: {input_file_path}")
    
    # 如果没有指定输出文件，则创建备份并覆盖原文件
    if output_file_path is None:
        # 创建备份文件
        backup_path = input_file_path + f".backup_{datetime.now().strftime('%Y%m%d_%H%M%S')}"
        shutil.copy2(input_file_path, backup_path)
        print(f"已创建备份文件: {backup_path}")
        output_file_path = input_file_path
    
    try:
        # 读取原始SQL文件
        with open(input_file_path, 'r', encoding='utf-8') as file:
            content = file.read()
        
        print("开始转换表名和字段名...")
        
        # 转换后的内容
        converted_content = convert_sql_content(content)
        
        # 写入转换后的内容
        with open(output_file_path, 'w', encoding='utf-8') as file:
            file.write(converted_content)
        
        print(f"转换完成！输出文件: {output_file_path}")
        
    except Exception as e:
        print(f"处理文件时发生错误: {str(e)}")
        raise

def convert_sql_content(content):
    """
    转换SQL内容中的表名和字段名为大写
    
    Args:
        content (str): 原始SQL内容
        
    Returns:
        str: 转换后的SQL内容
    """
    
    # 统计转换信息
    table_count = 0
    field_count = 0
    
    # 1. 转换 DROP TABLE 语句中的表名
    def replace_drop_table(match):
        nonlocal table_count
        table_count += 1
        table_name = match.group(1)
        return f"DROP TABLE IF EXISTS `{table_name.upper()}`;"
    
    content = re.sub(r'DROP TABLE IF EXISTS `([^`]+)`;', replace_drop_table, content)
    
    # 2. 转换 CREATE TABLE 语句中的表名
    def replace_create_table(match):
        nonlocal table_count
        table_name = match.group(1)
        rest_of_statement = match.group(2)
        return f"CREATE TABLE `{table_name.upper()}`{rest_of_statement}"
    
    content = re.sub(r'CREATE TABLE `([^`]+)`(\s+\()', replace_create_table, content)
    
    # 3. 转换字段名（在CREATE TABLE语句中的字段定义）
    def replace_field_names(match):
        nonlocal field_count
        field_count += 1
        field_name = match.group(1)
        rest_of_definition = match.group(2)
        return f"  `{field_name.upper()}`{rest_of_definition}"
    
    # 匹配字段定义行（以两个空格开头，然后是反引号包围的字段名）
    content = re.sub(r'^  `([^`]+)`([^\n]+)$', replace_field_names, content, flags=re.MULTILINE)
    
    # 4. 转换 PRIMARY KEY 中的字段名
    def replace_primary_key(match):
        field_name = match.group(1)
        return f"  PRIMARY KEY (`{field_name.upper()}`)"
    
    content = re.sub(r'  PRIMARY KEY \(`([^`]+)`\)', replace_primary_key, content)
    
    # 5. 转换 INDEX 中的字段名
    def replace_index_fields(match):
        prefix = match.group(1)
        fields_part = match.group(2)
        suffix = match.group(3)
        
        # 转换字段名
        fields_part = re.sub(r'`([^`]+)`', lambda m: f"`{m.group(1).upper()}`", fields_part)
        
        return f"{prefix}{fields_part}{suffix}"
    
    content = re.sub(r'(  INDEX [^(]+\()([^)]+)(\)[^\n]*)', replace_index_fields, content)
    
    # 6. 转换 CONSTRAINT 外键约束中的表名和字段名（简化处理）
    # 先转换 REFERENCES 后面的表名
    content = re.sub(r'REFERENCES `([^`]+)`', lambda m: f'REFERENCES `{m.group(1).upper()}`', content)
    
    # 转换 CONSTRAINT 中的字段名
    content = re.sub(r'CONSTRAINT ([^\s]+) FOREIGN KEY \(`([^`]+)`\)', 
                    lambda m: f'CONSTRAINT {m.group(1)} FOREIGN KEY (`{m.group(2).upper()}`)', content)
    
    # 7. 转换 INSERT INTO 语句中的表名
    def replace_insert_table(match):
        table_name = match.group(1)
        rest_of_statement = match.group(2)
        return f"INSERT INTO `{table_name.upper()}`{rest_of_statement}"
    
    content = re.sub(r'INSERT INTO `([^`]+)`([^\n]+)', replace_insert_table, content)
    
    print(f"转换统计: 处理了 {table_count} 个表名, {field_count} 个字段名")
    
    return content

def main():
    """
    主函数
    """
    print("=" * 60)
    print("SQL数据库表名和字段名大小写转换工具")
    print("=" * 60)
    
    # 输入文件路径
    input_file = r"e:\IdeaProjects\cfdownloadexample\eslplatform2025年8月20日18点33分.sql"
    
    # 检查文件是否存在
    if not os.path.exists(input_file):
        print(f"错误：文件不存在 - {input_file}")
        return
    
    # 获取文件信息
    file_size = os.path.getsize(input_file)
    print(f"文件大小: {file_size / 1024 / 1024:.2f} MB")
    
    try:
        # 执行转换
        convert_sql_to_uppercase(input_file)
        print("\n转换成功完成！")
        print("\n注意事项:")
        print("1. 原文件已自动备份")
        print("2. 请检查转换后的SQL文件是否符合预期")
        print("3. 建议在测试环境中先验证转换结果")
        
    except Exception as e:
        print(f"\n转换失败: {str(e)}")
        return
    
    print("\n" + "=" * 60)

if __name__ == "__main__":
    main()