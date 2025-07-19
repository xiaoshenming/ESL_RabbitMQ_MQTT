package com.pandatech.downloadcf.entity;

import java.io.Serializable;
import java.util.Date;

public class EslModel implements Serializable {
    private String id;

    private String tenantId;

    private Integer sortCode;

    private String deleteFlag;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String modelCode;

    private String modelName;

    private String brandCode;

    private String eslCategory;

    private String screenColor;

    private String communicationMethod;

    private String operatingTempRange;

    private String ipRating;

    private Float screenSizeWidth;

    private Float screenSizeHigh;

    private Float resolution;

    private Integer batteryLife;

    private String hardwareVersion;

    private String softwareVersion;

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

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode == null ? null : modelCode.trim();
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode == null ? null : brandCode.trim();
    }

    public String getEslCategory() {
        return eslCategory;
    }

    public void setEslCategory(String eslCategory) {
        this.eslCategory = eslCategory == null ? null : eslCategory.trim();
    }

    public String getScreenColor() {
        return screenColor;
    }

    public void setScreenColor(String screenColor) {
        this.screenColor = screenColor == null ? null : screenColor.trim();
    }

    public String getCommunicationMethod() {
        return communicationMethod;
    }

    public void setCommunicationMethod(String communicationMethod) {
        this.communicationMethod = communicationMethod == null ? null : communicationMethod.trim();
    }

    public String getOperatingTempRange() {
        return operatingTempRange;
    }

    public void setOperatingTempRange(String operatingTempRange) {
        this.operatingTempRange = operatingTempRange == null ? null : operatingTempRange.trim();
    }

    public String getIpRating() {
        return ipRating;
    }

    public void setIpRating(String ipRating) {
        this.ipRating = ipRating == null ? null : ipRating.trim();
    }

    public Float getScreenSizeWidth() {
        return screenSizeWidth;
    }

    public void setScreenSizeWidth(Float screenSizeWidth) {
        this.screenSizeWidth = screenSizeWidth;
    }

    public Float getScreenSizeHigh() {
        return screenSizeHigh;
    }

    public void setScreenSizeHigh(Float screenSizeHigh) {
        this.screenSizeHigh = screenSizeHigh;
    }

    public Float getResolution() {
        return resolution;
    }

    public void setResolution(Float resolution) {
        this.resolution = resolution;
    }

    public Integer getBatteryLife() {
        return batteryLife;
    }

    public void setBatteryLife(Integer batteryLife) {
        this.batteryLife = batteryLife;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion == null ? null : hardwareVersion.trim();
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion == null ? null : softwareVersion.trim();
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
        sb.append(", modelCode=").append(modelCode);
        sb.append(", modelName=").append(modelName);
        sb.append(", brandCode=").append(brandCode);
        sb.append(", eslCategory=").append(eslCategory);
        sb.append(", screenColor=").append(screenColor);
        sb.append(", communicationMethod=").append(communicationMethod);
        sb.append(", operatingTempRange=").append(operatingTempRange);
        sb.append(", ipRating=").append(ipRating);
        sb.append(", screenSizeWidth=").append(screenSizeWidth);
        sb.append(", screenSizeHigh=").append(screenSizeHigh);
        sb.append(", resolution=").append(resolution);
        sb.append(", batteryLife=").append(batteryLife);
        sb.append(", hardwareVersion=").append(hardwareVersion);
        sb.append(", softwareVersion=").append(softwareVersion);
        sb.append(", extJson=").append(extJson);
        sb.append("]");
        return sb.toString();
    }
}