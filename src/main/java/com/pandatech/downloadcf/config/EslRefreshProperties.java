package com.pandatech.downloadcf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 价签刷新配置属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "app.esl")
public class EslRefreshProperties {

    /**
     * 价签刷新配置
     */
    private RefreshConfig refresh = new RefreshConfig();

    /**
     * 字段映射配置
     */
    private FieldMappingConfig fieldMapping = new FieldMappingConfig();

    @Data
    public static class RefreshConfig {
        /**
         * 默认租户ID
         */
        private String defaultTenantId = "396a5189-53d8-4354-bcfa-27d57d9d69ad";

        /**
         * 默认门店编码
         */
        private String defaultStoreCode = "STORE001";

        /**
         * 强制刷新标志
         */
        private Boolean forceRefresh = true;

        /**
         * 默认模板ID（当价签没有绑定模板时使用）
         */
        private String defaultTemplateId = "PRICEPROMO";

        /**
         * 校验码计算方式（md5, crc32等）
         */
        private String checksumAlgorithm = "md5";
    }

    @Data
    public static class FieldMappingConfig {
        /**
         * 默认品牌编码
         */
        private String defaultBrandCode = "DEFAULT";

        /**
         * 强制转换字段列表
         */
        private List<String> forceConvertFields = List.of("GOODS_NAME", "GOODS_CODE");
    }
}