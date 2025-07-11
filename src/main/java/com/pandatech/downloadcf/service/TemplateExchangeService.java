package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.entity.TemplateExchange;

public interface TemplateExchangeService {
    /**
     * 根据名称查找模板
     * @param name 模板名称
     * @return TemplateExchange对象
     */
    TemplateExchange findByName(String name);
    
    /**
     * 根据名称获取JSON内容
     * @param name 模板名称
     * @return JSON字符串内容
     */
    String getJsonContentByName(String name);
}