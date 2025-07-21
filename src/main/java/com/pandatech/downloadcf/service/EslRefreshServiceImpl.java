package com.pandatech.downloadcf.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pandatech.downloadcf.config.EslRefreshProperties;
import com.pandatech.downloadcf.config.RabbitMQConfig;
import com.pandatech.downloadcf.dto.*;
import com.pandatech.downloadcf.entity.*;
import com.pandatech.downloadcf.mapper.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 价签刷新服务实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EslRefreshServiceImpl implements EslRefreshService {
    
    private final PandaEslMapper pandaEslMapper;
    private final PandaProductMapper pandaProductMapper;
    private final EslBrandFieldMappingMapper eslBrandFieldMappingMapper;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final EslRefreshProperties eslRefreshProperties;
    
    @Override
    public MqttMessageDto buildRefreshMessage(RefreshDto refreshDto) {
        try {
            // 1. 根据ESL ID查找价签信息
            PandaEsl eslInfo = findEslById(refreshDto.getEslId());
            if (eslInfo == null) {
                log.error("未找到ESL信息，ESL ID: {}", refreshDto.getEslId());
                return null;
            }
            
            // 2. 根据绑定的商品ID查找商品信息
            PandaProductWithBLOBs productInfo = findProductById(eslInfo.getBoundProduct());
            if (productInfo == null) {
                log.error("未找到商品信息，商品ID: {}", eslInfo.getBoundProduct());
                return null;
            }
            
            // 3. 获取字段映射配置
            List<EslBrandFieldMapping> fieldMappings = getFieldMappings(eslInfo);
            
            // 4. 构造MQTT消息
            MqttMessageDto mqttMessage = new MqttMessageDto();
            mqttMessage.setCommand("wtag");
            mqttMessage.setId(UUID.randomUUID().toString());
            mqttMessage.setTimestamp(System.currentTimeMillis() / 1000.0);
            mqttMessage.setShop(eslInfo.getStoreCode() != null ? eslInfo.getStoreCode() : 
                    eslRefreshProperties.getRefresh().getDefaultStoreCode());
            
            // 5. 构造数据部分
            MqttDataDto dataDto = new MqttDataDto();
            // 将十六进制的ESL ID转换为十进制Long类型
            dataDto.setTag(convertHexEslIdToLong(eslInfo.getEslId()));
            dataDto.setTmpl(productInfo.getEslTemplateCode() != null ? productInfo.getEslTemplateCode() : 
                    eslRefreshProperties.getRefresh().getDefaultTemplateId());
            dataDto.setModel(getModelFromEslModel(eslInfo.getEslModel()));
            dataDto.setForcefrash(eslRefreshProperties.getRefresh().getForceRefresh() ? 1 : 0);
            dataDto.setTaskid(generateTaskId());
            dataDto.setToken(generateToken());
            
            // 6. 构造value字段映射
            Map<String, Object> valueMap = buildValueMap(productInfo, fieldMappings);
            dataDto.setValue(valueMap);
            
            // 7. 计算checksum
            String checksum = calculateChecksum(dataDto);
            dataDto.setChecksum(checksum);
            
            mqttMessage.setData(Arrays.asList(dataDto));
            
            log.info("构造MQTT消息成功，ESL ID: {}, 门店: {}", refreshDto.getEslId(), eslInfo.getStoreCode());
            return mqttMessage;
            
        } catch (Exception e) {
            log.error("构造MQTT消息失败，ESL ID: {}", refreshDto.getEslId(), e);
            return null;
        }
    }
    
    @Override
    public void sendRefreshMessage(RefreshDto refreshDto) {
        try {
            MqttMessageDto mqttMessage = buildRefreshMessage(refreshDto);
            if (mqttMessage != null) {
                String message = objectMapper.writeValueAsString(mqttMessage);
                rabbitTemplate.convertAndSend(RabbitMQConfig.REFRESH_QUEUE, message);
                log.info("发送价签刷新消息到队列成功: {}", message);
            } else {
                log.error("构造MQTT消息失败，无法发送刷新消息");
            }
        } catch (Exception e) {
            log.error("发送价签刷新消息失败", e);
        }
    }
    
    /**
     * 根据ESL ID查找价签信息
     */
    private PandaEsl findEslById(String eslId) {
        try {
            PandaEslExample example = new PandaEslExample();
            example.createCriteria().andIdEqualTo(eslId);
            List<PandaEsl> eslList = pandaEslMapper.selectByExample(example);
            return eslList.isEmpty() ? null : eslList.get(0);
        } catch (Exception e) {
            log.error("查找ESL信息失败，ESL ID: {}", eslId, e);
            return null;
        }
    }
    
    /**
     * 根据商品ID查找商品信息
     */
    private PandaProductWithBLOBs findProductById(String productId) {
        try {
            return pandaProductMapper.selectByPrimaryKey(productId);
        } catch (Exception e) {
            log.error("查找商品信息失败，商品ID: {}", productId, e);
            return null;
        }
    }
    
    /**
     * 获取字段映射配置
     */
    private List<EslBrandFieldMapping> getFieldMappings(PandaEsl esl) {
        try {
            // 优先使用价签的品牌编码，如果没有则使用默认品牌编码
            String brandCode = esl.getEslCategory() != null ? esl.getEslCategory() : 
                    eslRefreshProperties.getFieldMapping().getDefaultBrandCode();
            
            // 根据品牌编码查询字段映射
            List<EslBrandFieldMapping> mappings = eslBrandFieldMappingMapper.findByBrandCode(brandCode);
            
            if (mappings.isEmpty()) {
                log.warn("未找到品牌编码 {} 的字段映射配置，使用默认品牌编码", brandCode);
                // 如果没有找到，尝试使用默认品牌编码
                if (!brandCode.equals(eslRefreshProperties.getFieldMapping().getDefaultBrandCode())) {
                    mappings = eslBrandFieldMappingMapper.findByBrandCode(
                            eslRefreshProperties.getFieldMapping().getDefaultBrandCode());
                }
            }
            
            log.debug("获取到 {} 个字段映射配置", mappings.size());
            return mappings;
        } catch (Exception e) {
            log.error("获取字段映射配置失败", e);
            return new ArrayList<>();
        }
    }
    
    /**
     * 根据ESL型号获取model值
     */
    private Integer getModelFromEslModel(String eslModel) {
        if (eslModel == null) return 6;
        
        // 根据ESL型号映射到model值
        switch (eslModel.toLowerCase()) {
            case "2.13t":
                return 6;
            case "2.9t":
                return 7;
            case "4.2t":
                return 8;
            default:
                return 6;
        }
    }
    
    /**
     * 生成任务ID
     */
    private Integer generateTaskId() {
        return (int) (System.currentTimeMillis() % 100000);
    }
    
    /**
     * 生成token
     */
    private Integer generateToken() {
        return new Random().nextInt(999999) + 100000;
    }
    
    /**
     * 构造value字段映射
     */
    private Map<String, Object> buildValueMap(PandaProductWithBLOBs product, List<EslBrandFieldMapping> fieldMappings) {
        Map<String, Object> valueMap = new HashMap<>();
        
        if (product == null) {
            log.warn("商品信息为空，返回空的value映射");
            return valueMap;
        }
        
        // 根据配置的强制转换字段列表添加固定字段
        List<String> forceConvertFields = eslRefreshProperties.getFieldMapping().getForceConvertFields();
        if (forceConvertFields.contains("GOODS_NAME")) {
            valueMap.put("GOODS_NAME", product.getProductName());
        }
        if (forceConvertFields.contains("GOODS_CODE")) {
            valueMap.put("GOODS_CODE", product.getProductId());
        }
        
        // 根据字段映射配置动态映射其他字段
        for (EslBrandFieldMapping mapping : fieldMappings) {
            String templateField = mapping.getTemplateField();
            String fieldCode = mapping.getFieldCode();
            Object fieldValue = getProductFieldValue(product, fieldCode);
            
            // 应用格式化规则
            if (mapping.getFormatRule() != null && fieldValue != null) {
                fieldValue = applyFormatRule(fieldValue, mapping.getFormatRule());
            }
            
            valueMap.put(templateField, fieldValue);
        }
        
        log.debug("构造value映射完成，字段数量: {}", valueMap.size());
        return valueMap;
    }
    
    /**
     * 根据字段编码获取商品字段值
     */
    private Object getProductFieldValue(PandaProductWithBLOBs product, String fieldCode) {
        if (product == null) {
            return null;
        }
        
        switch (fieldCode) {
            case "PRODUCT_ID":
                return product.getProductId();
            case "PRODUCT_NAME":
                return product.getProductName();
            case "PRODUCT_RETAIL_PRICE":
                return product.getProductRetailPrice();
            case "PRODUCT_CATEGORY":
                return product.getProductCategory();
            case "PRODUCT_COST_PRICE":
                return product.getProductCostPrice();
            case "PRODUCT_SPECIFICATION":
                return product.getProductSpecification();
            case "PRODUCT_MEMBERSHIP_PRICE":
                return product.getProductMembershipPrice();
            case "PRODUCT_DISCOUNT_PRICE":
                return product.getProductDiscountPrice();
            case "PRODUCT_DISCOUNT":
                return product.getProductDiscount();
            case "PRODUCT_WHOLESALE_PRICE":
                return product.getProductWholesalePrice();
            case "PRODUCT_MATERIAL":
                return product.getProductMaterial();
            case "PRODUCT_IMAGE":
                return product.getProductImage();
            case "PRODUCT_ORIGIN":
                return product.getProductOrigin();
            case "PRODUCT_DESCRIPTION":
                return product.getProductDescription();
            case "PRODUCT_UNIT":
                return product.getProductUnit();
            case "PRODUCT_WEIGHT":
                return product.getProductWeight();
            case "PRODUCT_STATUS":
                return product.getProductStatus();
            case "PRODUCT_STOCK":
                return product.getProductStock();
            case "PRODUCT_QRCODE":
                return product.getProductQrcode();
            case "PRODUCT_BARCODE":
                return product.getProductBarcode();
            default:
                log.warn("未知的字段编码: {}", fieldCode);
                return null;
        }
    }
    
    /**
     * 应用格式化规则
     */
    private Object applyFormatRule(Object value, String formatRule) {
        // 这里可以根据格式化规则对值进行处理
        // 例如：价格保留两位小数、日期格式化等
        return value;
    }
    
    /**
     * 计算checksum
     */
    private String calculateChecksum(MqttDataDto dataDto) {
        try {
            // 构造用于计算checksum的字符串
            StringBuilder sb = new StringBuilder();
            sb.append(dataDto.getTag());
            sb.append(dataDto.getTmpl());
            sb.append(dataDto.getModel());
            
            // 添加value中的值
            if (dataDto.getValue() != null) {
                dataDto.getValue().entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        if (entry.getValue() != null) {
                            sb.append(entry.getValue().toString());
                        }
                    });
            }
            
            // 计算MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(sb.toString().getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            log.error("计算checksum失败", e);
            return "default_checksum";
        }
    }
    
    /**
     * 将十六进制的ESL ID转换为十进制Long类型
     * 例如：将 "06000000195A" 转换为对应的十进制数字
     */
    private Long convertHexEslIdToLong(String hexEslId) {
        if (hexEslId == null || hexEslId.trim().isEmpty()) {
            log.warn("ESL ID为空，使用默认值0");
            return 0L;
        }
        
        try {
            // 移除可能的前缀和空格
            String cleanHexId = hexEslId.trim().toUpperCase();
            
            // 如果包含0x前缀，移除它
            if (cleanHexId.startsWith("0X")) {
                cleanHexId = cleanHexId.substring(2);
            }
            
            // 将十六进制字符串转换为Long
            Long result = Long.parseUnsignedLong(cleanHexId, 16);
            log.debug("ESL ID转换成功: {} -> {}", hexEslId, result);
            return result;
            
        } catch (NumberFormatException e) {
            log.error("ESL ID转换失败，无效的十六进制格式: {}", hexEslId, e);
            // 如果转换失败，尝试提取数字部分
            try {
                String numericPart = hexEslId.replaceAll("[^0-9A-Fa-f]", "");
                if (!numericPart.isEmpty()) {
                    Long result = Long.parseUnsignedLong(numericPart, 16);
                    log.warn("使用提取的数字部分进行转换: {} -> {}", numericPart, result);
                    return result;
                }
            } catch (Exception ex) {
                log.error("提取数字部分转换也失败", ex);
            }
            
            // 最后的备用方案：返回hashCode的绝对值
            long fallback = Math.abs(hexEslId.hashCode());
            log.warn("使用hashCode作为备用方案: {} -> {}", hexEslId, fallback);
            return fallback;
        }
    }
}