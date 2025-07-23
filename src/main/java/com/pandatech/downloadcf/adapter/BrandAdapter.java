package com.pandatech.downloadcf.adapter;

import com.pandatech.downloadcf.dto.BrandOutputData;
import com.pandatech.downloadcf.dto.EslCompleteData;

/**
 * 品牌适配器接口 - 用于不同品牌的数据格式转换
 */
public interface BrandAdapter {
    
    /**
     * 获取支持的品牌编码
     */
    String getSupportedBrandCode();
    
    /**
     * 将完整的价签数据转换为品牌特定的输出格式
     * 
     * @param completeData 完整的价签数据
     * @return 转换后的品牌输出数据
     */
    BrandOutputData transform(EslCompleteData completeData);
    
    /**
     * 验证数据是否符合品牌要求
     * 
     * @param completeData 完整的价签数据
     * @return 验证结果
     */
    boolean validate(EslCompleteData completeData);
    
    /**
     * 计算校验码
     * 
     * @param templateContent 模板内容
     * @return 校验码
     */
    String calculateChecksum(String templateContent);
}