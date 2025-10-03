package com.pandatech.downloadcf.service.data;

import com.pandatech.downloadcf.entity.PandaEsl;
import com.pandatech.downloadcf.entity.PandaEslCriteria;
import com.pandatech.downloadcf.mapper.PandaEslMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 价签数据访问器
 * 专门负责价签相关的数据库操作
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EslDataAccessor {
    
    private final PandaEslMapper pandaEslMapper;
    
    /**
     * 根据价签ID获取价签信息
     */
    public PandaEsl getEslById(String eslId) {
        log.debug("查询价签信息: {}", eslId);
        PandaEsl esl = pandaEslMapper.selectByPrimaryKey(eslId);
        if (esl == null) {
            log.warn("未找到价签信息: {}", eslId);
        }
        return esl;
    }
    
    /**
     * 根据门店代码获取价签列表
     */
    public List<PandaEsl> getEslsByStore(String storeCode) {
        log.debug("查询门店价签列表: {}", storeCode);
        
        PandaEslCriteria criteria = new PandaEslCriteria();
        criteria.createCriteria().andStoreCodeEqualTo(storeCode);
        
        List<PandaEsl> esls = pandaEslMapper.selectByExample(criteria);
        log.debug("门店 {} 共有 {} 个价签", storeCode, esls.size());
        
        return esls;
    }
    
    /**
     * 根据商品ID获取价签列表
     */
    public List<PandaEsl> getEslsByProduct(String productId) {
        log.debug("查询商品绑定的价签列表: {}", productId);
        
        PandaEslCriteria criteria = new PandaEslCriteria();
        criteria.createCriteria().andBoundProductEqualTo(productId);
        
        List<PandaEsl> esls = pandaEslMapper.selectByExample(criteria);
        log.debug("商品 {} 绑定了 {} 个价签", productId, esls.size());
        
        return esls;
    }
    
    /**
     * 根据门店和商品获取价签列表
     */
    public List<PandaEsl> getEslsByStoreAndProduct(String storeCode, String productId) {
        log.debug("查询门店商品价签列表: storeCode={}, productId={}", storeCode, productId);
        
        PandaEslCriteria criteria = new PandaEslCriteria();
        criteria.createCriteria()
                .andStoreCodeEqualTo(storeCode)
                .andBoundProductEqualTo(productId);
        
        List<PandaEsl> esls = pandaEslMapper.selectByExample(criteria);
        log.debug("门店 {} 商品 {} 共有 {} 个价签", storeCode, productId, esls.size());
        
        return esls;
    }
    
    /**
     * 检查价签是否存在
     */
    public boolean existsEsl(String eslId) {
        PandaEsl esl = pandaEslMapper.selectByPrimaryKey(eslId);
        return esl != null;
    }
    
    /**
     * 获取门店价签总数
     */
    public long countEslsByStore(String storeCode) {
        PandaEslCriteria criteria = new PandaEslCriteria();
        criteria.createCriteria().andStoreCodeEqualTo(storeCode);
        
        long count = pandaEslMapper.countByExample(criteria);
        log.debug("门店 {} 共有 {} 个价签", storeCode, count);
        
        return count;
    }
    
    /**
     * 获取所有价签列表（分页）
     */
    public List<PandaEsl> getAllEsls(int offset, int limit) {
        log.debug("查询所有价签列表: offset={}, limit={}", offset, limit);
        
        PandaEslCriteria criteria = new PandaEslCriteria();
        criteria.setOrderByClause("id ASC");
        criteria.setOffset(offset);
        criteria.setLimit(limit);
        
        return pandaEslMapper.selectByExample(criteria);
    }
}