package com.pandatech.downloadcf.service.impl;

import com.pandatech.downloadcf.entity.TemplateExchange;
import com.pandatech.downloadcf.entity.TemplateExchangeExample;
import com.pandatech.downloadcf.mapper.TemplateExchangeMapper;
import com.pandatech.downloadcf.service.TemplateExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class TemplateExchangeServiceImpl implements TemplateExchangeService {
    
    @Autowired
    private TemplateExchangeMapper templateExchangeMapper;
    
    @Override
    public TemplateExchange findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        
        TemplateExchangeExample example = new TemplateExchangeExample();
        example.createCriteria().andNameEqualTo(name.trim());
        
        List<TemplateExchange> list = templateExchangeMapper.selectByExampleWithBLOBs(example);
        
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        
        return list.get(0);
    }
    
    @Override
    public String getJsonContentByName(String name) {
        TemplateExchange template = findByName(name);
        if (template == null) {
            return null;
        }
        return template.getChuangfengTemplate();
    }
}