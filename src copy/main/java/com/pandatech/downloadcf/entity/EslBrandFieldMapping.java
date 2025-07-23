package com.pandatech.downloadcf.entity;

import java.io.Serializable;
import java.util.Date;

public class EslBrandFieldMapping implements Serializable {
    private String id;

    private String tenantId;

    private Integer sortCode;

    private String deleteFlag;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String brandCode;

    private String templateField;

    private String fieldCode;

    private String formatRule;

    private String extJson;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    public Integer getSortCode() {
        return sortCode;
    }

    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode == null ? null : brandCode.trim();
    }

    public String getTemplateField() {
        return templateField;
    }

    public void setTemplateField(String templateField) {
        this.templateField = templateField == null ? null : templateField.trim();
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode == null ? null : fieldCode.trim();
    }

    public String getFormatRule() {
        return formatRule;
    }

    public void setFormatRule(String formatRule) {
        this.formatRule = formatRule == null ? null : formatRule.trim();
    }

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson == null ? null : extJson.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", sortCode=").append(sortCode);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", brandCode=").append(brandCode);
        sb.append(", templateField=").append(templateField);
        sb.append(", fieldCode=").append(fieldCode);
        sb.append(", formatRule=").append(formatRule);
        sb.append(", extJson=").append(extJson);
        sb.append("]");
        return sb.toString();
    }
}