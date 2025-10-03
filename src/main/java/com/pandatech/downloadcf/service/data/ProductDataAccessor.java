package com.pandatech.downloadcf.service.data;

import com.pandatech.downloadcf.entity.PandaProductWithBLOBs;
import com.pandatech.downloadcf.mapper.PandaProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 商品数据访问器
 * 专门负责商品相关的数据库操作
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ProductDataAccessor {
    
    private final PandaProductMapper pandaProductMapper;
    
    /**
     * 根据商品ID获取商品信息
     */
    public PandaProductWithBLOBs getProductById(String productId) {
        log.debug("查询商品信息: {}", productId);
        
        if (productId == null || productId.trim().isEmpty()) {
            log.warn("商品ID为空");
            return null;
        }
        
        PandaProductWithBLOBs product = pandaProductMapper.selectByPrimaryKey(productId);
        if (product == null) {
            log.warn("未找到商品信息: {}", productId);
        } else {
            log.debug("成功获取商品信息: productId={}, productName={}", 
                    productId, product.getProductName());
        }
        
        return product;
    }
    
    /**
     * 根据价签ID获取绑定的商品信息
     * 通过价签的绑定商品字段查询
     */
    public PandaProductWithBLOBs getProductByEslId(String eslId) {
        log.debug("通过价签ID查询绑定的商品: {}", eslId);
        
        // 这里需要先通过EslDataAccessor获取价签信息，然后获取绑定的商品
        // 为了避免循环依赖，这个方法应该在更高层的服务中实现
        // 这里只提供基础的商品查询功能
        throw new UnsupportedOperationException("请使用DataService.getProductByEslId()方法");
    }
    
    /**
     * 检查商品是否存在
     */
    public boolean existsProduct(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return false;
        }
        
        PandaProductWithBLOBs product = pandaProductMapper.selectByPrimaryKey(productId);
        return product != null;
    }
    
    /**
     * 获取商品的模板代码
     */
    public String getTemplateCodeByProductId(String productId) {
        log.debug("获取商品模板代码: {}", productId);
        
        PandaProductWithBLOBs product = getProductById(productId);
        if (product != null) {
            String templateCode = product.getEslTemplateCode();
            log.debug("商品 {} 的模板代码: {}", productId, templateCode);
            return templateCode;
        }
        
        return null;
    }
    
    /**
     * 检查商品是否有有效的模板代码
     */
    public boolean hasValidTemplateCode(String productId) {
        String templateCode = getTemplateCodeByProductId(productId);
        return templateCode != null && !templateCode.trim().isEmpty();
    }
}