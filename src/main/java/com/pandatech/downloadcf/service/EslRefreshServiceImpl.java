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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
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

            // 处理模板字段，如果为空或"为空"则使用默认模板
            String templateCode = productInfo.getEslTemplateCode();
            if (templateCode == null || templateCode.trim().isEmpty() || "为空".equals(templateCode.trim())) {
                templateCode = eslRefreshProperties.getRefresh().getDefaultTemplateId();
                log.info("商品模板为空，使用默认模板: {}", templateCode);
            }
            dataDto.setTmpl(templateCode);

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

        // 添加默认的F_1到F_20字段（如果没有通过映射配置）
        for (int i = 1; i <= 20; i++) {
            String fieldKey = "F_" + i;
            if (!valueMap.containsKey(fieldKey)) {
                // 根据字段编号映射到不同的商品属性
                Object defaultValue = getDefaultFieldValue(product, i);
                valueMap.put(fieldKey, defaultValue);
            }
        }

        // 添加QRCODE字段（如果没有通过映射配置）
        if (!valueMap.containsKey("QRCODE")) {
            valueMap.put("QRCODE", product.getProductQrcode());
        }

        log.debug("构造value映射完成，字段数量: {}", valueMap.size());
        return valueMap;
    }
    
    /**
     * 获取默认字段值（F_1到F_20）
     */
    private Object getDefaultFieldValue(PandaProductWithBLOBs product, int fieldNumber) {
        switch (fieldNumber) {
            case 1:
                return product.getProductRetailPrice(); // F_1 通常是零售价
            case 2:
                return product.getProductMembershipPrice(); // F_2 会员价
            case 3:
                return product.getProductCostPrice(); // F_3 成本价
            case 4:
                return product.getProductDiscountPrice(); // F_4 折扣价
            case 5:
                return product.getProductWholesalePrice(); // F_5 批发价
            case 6:
                return product.getProductUnit(); // F_6 单位
            case 7:
                return product.getProductWeight(); // F_7 重量
            case 8:
                return product.getProductSpecification(); // F_8 规格
            case 9:
                return product.getProductOrigin(); // F_9 产地
            case 10:
                return product.getProductBrand(); // F_10 品牌
            case 11:
                return product.getProductBarcode(); // F_11 条形码
            case 20:
                return product.getProductStock(); // F_20 库存
            default:
                return null; // 其他字段默认为null
        }
    }
    
    /**
     * 根据字段编码获取商品字段值
     */
    private Object getProductFieldValue(PandaProductWithBLOBs product, String fieldCode) {
        if (product == null || fieldCode == null) {
            return null;
        }
        
        switch (fieldCode.toUpperCase()) {
            // 基本信息
            case "PRODUCT_ID":
            case "GOODS_CODE":
                return product.getProductId();
            case "PRODUCT_NAME":
            case "GOODS_NAME":
                return product.getProductName();
            case "PRODUCT_BARCODE":
            case "BARCODE":
                return product.getProductBarcode();
            case "PRODUCT_QRCODE":
            case "QRCODE":
                return product.getProductQrcode();
                
            // 价格相关
            case "PRODUCT_RETAIL_PRICE":
            case "RETAIL_PRICE":
            case "PRICE":
                return product.getProductRetailPrice();
            case "PRODUCT_MEMBERSHIP_PRICE":
            case "MEMBERSHIP_PRICE":
            case "MEMBER_PRICE":
                return product.getProductMembershipPrice();
            case "PRODUCT_COST_PRICE":
            case "COST_PRICE":
                return product.getProductCostPrice();
            case "PRODUCT_DISCOUNT_PRICE":
            case "DISCOUNT_PRICE":
                return product.getProductDiscountPrice();
            case "PRODUCT_DISCOUNT":
            case "DISCOUNT":
                return product.getProductDiscount();
            case "PRODUCT_WHOLESALE_PRICE":
            case "WHOLESALE_PRICE":
                return product.getProductWholesalePrice();
                
            // 商品属性
            case "PRODUCT_UNIT":
            case "UNIT":
                return product.getProductUnit();
            case "PRODUCT_WEIGHT":
            case "WEIGHT":
                return product.getProductWeight();
            case "PRODUCT_SPECIFICATION":
            case "SPECIFICATION":
            case "SPEC":
                return product.getProductSpecification();
            case "PRODUCT_ORIGIN":
            case "ORIGIN":
                return product.getProductOrigin();
            case "PRODUCT_BRAND":
            case "BRAND":
                return product.getProductBrand();
            case "PRODUCT_STOCK":
            case "STOCK":
                return product.getProductStock();
            case "PRODUCT_MATERIAL":
            case "MATERIAL":
                return product.getProductMaterial();
                
            // 分类相关
            case "PRODUCT_CATEGORY":
            case "CATEGORY":
                return product.getProductCategory();
                
            // 状态相关
            case "PRODUCT_STATUS":
            case "STATUS":
                return product.getProductStatus();
                
            // 其他字段
            case "PRODUCT_DESCRIPTION":
            case "DESCRIPTION":
                return product.getProductDescription();
            case "PRODUCT_IMAGE":
            case "IMAGE":
                return product.getProductImage();
                
            default:
                log.warn("未知的字段编码: {}", fieldCode);
                return null;
        }
    }
    
    /**
     * 应用格式化规则
     */
    private Object applyFormatRule(Object value, String formatRule) {
        if (value == null || formatRule == null || formatRule.trim().isEmpty()) {
            return value;
        }
        
        try {
            String rule = formatRule.trim().toLowerCase();
            
            // 价格格式化规则
            if (rule.startsWith("price")) {
                return formatPrice(value, rule);
            }
            // 文本格式化规则
            else if (rule.startsWith("text")) {
                return formatText(value, rule);
            }
            // 数字格式化规则
            else if (rule.startsWith("number")) {
                return formatNumber(value, rule);
            }
            // 日期格式化规则
            else if (rule.startsWith("date")) {
                return formatDate(value, rule);
            }
            // 自定义格式化规则
            else if (rule.contains("format:")) {
                return customFormat(value, rule);
            }
            
            log.debug("未识别的格式化规则: {}, 返回原值", formatRule);
            return value;
            
        } catch (Exception e) {
            log.error("应用格式化规则失败: {}, 原值: {}", formatRule, value, e);
            return value;
        }
    }
    
    /**
     * 价格格式化
     */
    private Object formatPrice(Object value, String rule) {
        if (value == null) return null;
        
        try {
            java.math.BigDecimal price = new java.math.BigDecimal(value.toString());
            
            if (rule.contains("yuan")) {
                // 格式化为元，保留2位小数
                return String.format("%.2f元", price);
            } else if (rule.contains("fen")) {
                // 转换为分
                return price.multiply(new java.math.BigDecimal("100")).intValue();
            } else if (rule.contains("decimal:")) {
                // 指定小数位数
                String[] parts = rule.split("decimal:");
                if (parts.length > 1) {
                    int decimals = Integer.parseInt(parts[1].trim());
                    return price.setScale(decimals, java.math.RoundingMode.HALF_UP);
                }
            }
            
            // 默认保留2位小数
            return price.setScale(2, java.math.RoundingMode.HALF_UP);
            
        } catch (Exception e) {
            log.warn("价格格式化失败: {}", value, e);
            return value;
        }
    }
    
    /**
     * 文本格式化
     */
    private Object formatText(Object value, String rule) {
        if (value == null) return null;
        
        String text = value.toString();
        
        if (rule.contains("upper")) {
            return text.toUpperCase();
        } else if (rule.contains("lower")) {
            return text.toLowerCase();
        } else if (rule.contains("trim")) {
            return text.trim();
        } else if (rule.contains("maxlength:")) {
            String[] parts = rule.split("maxlength:");
            if (parts.length > 1) {
                try {
                    int maxLength = Integer.parseInt(parts[1].trim());
                    return text.length() > maxLength ? text.substring(0, maxLength) : text;
                } catch (NumberFormatException e) {
                    log.warn("解析最大长度失败: {}", parts[1]);
                }
            }
        }
        
        return text;
    }
    
    /**
     * 数字格式化
     */
    private Object formatNumber(Object value, String rule) {
        if (value == null) return null;
        
        try {
            if (rule.contains("int")) {
                return Integer.valueOf(value.toString());
            } else if (rule.contains("long")) {
                return Long.valueOf(value.toString());
            } else if (rule.contains("double")) {
                return Double.valueOf(value.toString());
            }
            
            return value;
            
        } catch (Exception e) {
            log.warn("数字格式化失败: {}", value, e);
            return value;
        }
    }
    
    /**
     * 日期格式化
     */
    private Object formatDate(Object value, String rule) {
        if (value == null) return null;
        
        try {
            if (rule.contains("yyyy-mm-dd")) {
                if (value instanceof java.util.Date) {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    return sdf.format((java.util.Date) value);
                }
            } else if (rule.contains("timestamp")) {
                if (value instanceof java.util.Date) {
                    return ((java.util.Date) value).getTime();
                }
            }
            
            return value;
            
        } catch (Exception e) {
            log.warn("日期格式化失败: {}", value, e);
            return value;
        }
    }
    
    /**
     * 自定义格式化
     */
    private Object customFormat(Object value, String rule) {
        if (value == null) return null;
        
        try {
            String[] parts = rule.split("format:");
            if (parts.length > 1) {
                String format = parts[1].trim();
                
                // 支持简单的字符串模板替换
                if (format.contains("{value}")) {
                    return format.replace("{value}", value.toString());
                }
                
                // 支持printf风格的格式化
                if (format.startsWith("%")) {
                    return String.format(format, value);
                }
            }
            
            return value;
            
        } catch (Exception e) {
            log.warn("自定义格式化失败: {}", value, e);
            return value;
        }
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