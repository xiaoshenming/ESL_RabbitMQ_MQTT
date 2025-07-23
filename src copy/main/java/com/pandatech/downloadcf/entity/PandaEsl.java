package com.pandatech.downloadcf.entity;

import java.io.Serializable;
import java.util.Date;

public class PandaEsl implements Serializable {
    private String id;

    private String tenantId;

    private Integer sortCode;

    private String deleteFlag;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String eslId;

    private String storeCode;

    private String apSn;

    private String eslModel;

    private String boundProduct;

    private Float batteryLevel;

    private Float temperature;

    private Integer signalStrength;

    private Integer communicationCount;

    private Integer failureCount;

    private String eslCategory;

    private String eslStatus;

    private String screenColor;

    private String communicationMethod;

    private String version;

    private String hardware;

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

    public String getEslId() {
        return eslId;
    }

    public void setEslId(String eslId) {
        this.eslId = eslId == null ? null : eslId.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public String getApSn() {
        return apSn;
    }

    public void setApSn(String apSn) {
        this.apSn = apSn == null ? null : apSn.trim();
    }

    public String getEslModel() {
        return eslModel;
    }

    public void setEslModel(String eslModel) {
        this.eslModel = eslModel == null ? null : eslModel.trim();
    }

    public String getBoundProduct() {
        return boundProduct;
    }

    public void setBoundProduct(String boundProduct) {
        this.boundProduct = boundProduct == null ? null : boundProduct.trim();
    }

    public Float getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    public Integer getCommunicationCount() {
        return communicationCount;
    }

    public void setCommunicationCount(Integer communicationCount) {
        this.communicationCount = communicationCount;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public String getEslCategory() {
        return eslCategory;
    }

    public void setEslCategory(String eslCategory) {
        this.eslCategory = eslCategory == null ? null : eslCategory.trim();
    }

    public String getEslStatus() {
        return eslStatus;
    }

    public void setEslStatus(String eslStatus) {
        this.eslStatus = eslStatus == null ? null : eslStatus.trim();
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware == null ? null : hardware.trim();
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
        sb.append(", eslId=").append(eslId);
        sb.append(", storeCode=").append(storeCode);
        sb.append(", apSn=").append(apSn);
        sb.append(", eslModel=").append(eslModel);
        sb.append(", boundProduct=").append(boundProduct);
        sb.append(", batteryLevel=").append(batteryLevel);
        sb.append(", temperature=").append(temperature);
        sb.append(", signalStrength=").append(signalStrength);
        sb.append(", communicationCount=").append(communicationCount);
        sb.append(", failureCount=").append(failureCount);
        sb.append(", eslCategory=").append(eslCategory);
        sb.append(", eslStatus=").append(eslStatus);
        sb.append(", screenColor=").append(screenColor);
        sb.append(", communicationMethod=").append(communicationMethod);
        sb.append(", version=").append(version);
        sb.append(", hardware=").append(hardware);
        sb.append(", extJson=").append(extJson);
        sb.append("]");
        return sb.toString();
    }
}