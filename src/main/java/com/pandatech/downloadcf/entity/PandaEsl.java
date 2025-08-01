package com.pandatech.downloadcf.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table panda_esl
 */
public class PandaEsl implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.ID
     *
     * @mbg.generated
     */
    private String id;

    /**
     * Database Column Remarks:
     *   租户id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.TENANT_ID
     *
     * @mbg.generated
     */
    private String tenantId;

    /**
     * Database Column Remarks:
     *   排序码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.SORT_CODE
     *
     * @mbg.generated
     */
    private Integer sortCode;

    /**
     * Database Column Remarks:
     *   删除标志
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.DELETE_FLAG
     *
     * @mbg.generated
     */
    private String deleteFlag;

    /**
     * Database Column Remarks:
     *   创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.CREATE_TIME
     *
     * @mbg.generated
     */
    private LocalDateTime createTime;

    /**
     * Database Column Remarks:
     *   创建用户
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.CREATE_USER
     *
     * @mbg.generated
     */
    private String createUser;

    /**
     * Database Column Remarks:
     *   修改时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.UPDATE_TIME
     *
     * @mbg.generated
     */
    private LocalDateTime updateTime;

    /**
     * Database Column Remarks:
     *   修改用户
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.UPDATE_USER
     *
     * @mbg.generated
     */
    private String updateUser;

    /**
     * Database Column Remarks:
     *   电子价签编号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.ESL_ID
     *
     * @mbg.generated
     */
    private String eslId;

    /**
     * Database Column Remarks:
     *   门店编码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.STORE_CODE
     *
     * @mbg.generated
     */
    private String storeCode;

    /**
     * Database Column Remarks:
     *   门店编码
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.AP_SN
     *
     * @mbg.generated
     */
    private String apSn;

    /**
     * Database Column Remarks:
     *   电子价签型号
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.ESL_MODEL
     *
     * @mbg.generated
     */
    private String eslModel;

    /**
     * Database Column Remarks:
     *   绑定商品
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.BOUND_PRODUCT
     *
     * @mbg.generated
     */
    private String boundProduct;

    /**
     * Database Column Remarks:
     *   电子价签电量（百分比）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.BATTERY_LEVEL
     *
     * @mbg.generated
     */
    private Float batteryLevel;

    /**
     * Database Column Remarks:
     *   电子价签温度（摄氏度）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.TEMPERATURE
     *
     * @mbg.generated
     */
    private Float temperature;

    /**
     * Database Column Remarks:
     *   信号值
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.SIGNAL_STRENGTH
     *
     * @mbg.generated
     */
    private Integer signalStrength;

    /**
     * Database Column Remarks:
     *   通讯次数
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.COMMUNICATION_COUNT
     *
     * @mbg.generated
     */
    private Integer communicationCount;

    /**
     * Database Column Remarks:
     *   失败次数
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.FAILURE_COUNT
     *
     * @mbg.generated
     */
    private Integer failureCount;

    /**
     * Database Column Remarks:
     *   ESL分类
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.ESL_CATEGORY
     *
     * @mbg.generated
     */
    private String eslCategory;

    /**
     * Database Column Remarks:
     *   电子价签状态
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.ESL_STATUS
     *
     * @mbg.generated
     */
    private String eslStatus;

    /**
     * Database Column Remarks:
     *   电子价签屏幕颜色
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.SCREEN_COLOR
     *
     * @mbg.generated
     */
    private String screenColor;

    /**
     * Database Column Remarks:
     *   电子价签通讯方式（蓝牙/NFC/Wifi/ZigBee）
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.COMMUNICATION_METHOD
     *
     * @mbg.generated
     */
    private String communicationMethod;

    /**
     * Database Column Remarks:
     *   版本
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.VERSION
     *
     * @mbg.generated
     */
    private String version;

    /**
     * Database Column Remarks:
     *   硬件
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.HARDWARE
     *
     * @mbg.generated
     */
    private String hardware;

    /**
     * Database Column Remarks:
     *   扩展信息
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column panda_esl.EXT_JSON
     *
     * @mbg.generated
     */
    private String extJson;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table panda_esl
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.ID
     *
     * @return the value of panda_esl.ID
     *
     * @mbg.generated
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.ID
     *
     * @param id the value for panda_esl.ID
     *
     * @mbg.generated
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.TENANT_ID
     *
     * @return the value of panda_esl.TENANT_ID
     *
     * @mbg.generated
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.TENANT_ID
     *
     * @param tenantId the value for panda_esl.TENANT_ID
     *
     * @mbg.generated
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId == null ? null : tenantId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.SORT_CODE
     *
     * @return the value of panda_esl.SORT_CODE
     *
     * @mbg.generated
     */
    public Integer getSortCode() {
        return sortCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.SORT_CODE
     *
     * @param sortCode the value for panda_esl.SORT_CODE
     *
     * @mbg.generated
     */
    public void setSortCode(Integer sortCode) {
        this.sortCode = sortCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.DELETE_FLAG
     *
     * @return the value of panda_esl.DELETE_FLAG
     *
     * @mbg.generated
     */
    public String getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.DELETE_FLAG
     *
     * @param deleteFlag the value for panda_esl.DELETE_FLAG
     *
     * @mbg.generated
     */
    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag == null ? null : deleteFlag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.CREATE_TIME
     *
     * @return the value of panda_esl.CREATE_TIME
     *
     * @mbg.generated
     */
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.CREATE_TIME
     *
     * @param createTime the value for panda_esl.CREATE_TIME
     *
     * @mbg.generated
     */
    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.CREATE_USER
     *
     * @return the value of panda_esl.CREATE_USER
     *
     * @mbg.generated
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.CREATE_USER
     *
     * @param createUser the value for panda_esl.CREATE_USER
     *
     * @mbg.generated
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.UPDATE_TIME
     *
     * @return the value of panda_esl.UPDATE_TIME
     *
     * @mbg.generated
     */
    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.UPDATE_TIME
     *
     * @param updateTime the value for panda_esl.UPDATE_TIME
     *
     * @mbg.generated
     */
    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.UPDATE_USER
     *
     * @return the value of panda_esl.UPDATE_USER
     *
     * @mbg.generated
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.UPDATE_USER
     *
     * @param updateUser the value for panda_esl.UPDATE_USER
     *
     * @mbg.generated
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.ESL_ID
     *
     * @return the value of panda_esl.ESL_ID
     *
     * @mbg.generated
     */
    public String getEslId() {
        return eslId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.ESL_ID
     *
     * @param eslId the value for panda_esl.ESL_ID
     *
     * @mbg.generated
     */
    public void setEslId(String eslId) {
        this.eslId = eslId == null ? null : eslId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.STORE_CODE
     *
     * @return the value of panda_esl.STORE_CODE
     *
     * @mbg.generated
     */
    public String getStoreCode() {
        return storeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.STORE_CODE
     *
     * @param storeCode the value for panda_esl.STORE_CODE
     *
     * @mbg.generated
     */
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.AP_SN
     *
     * @return the value of panda_esl.AP_SN
     *
     * @mbg.generated
     */
    public String getApSn() {
        return apSn;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.AP_SN
     *
     * @param apSn the value for panda_esl.AP_SN
     *
     * @mbg.generated
     */
    public void setApSn(String apSn) {
        this.apSn = apSn == null ? null : apSn.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.ESL_MODEL
     *
     * @return the value of panda_esl.ESL_MODEL
     *
     * @mbg.generated
     */
    public String getEslModel() {
        return eslModel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.ESL_MODEL
     *
     * @param eslModel the value for panda_esl.ESL_MODEL
     *
     * @mbg.generated
     */
    public void setEslModel(String eslModel) {
        this.eslModel = eslModel == null ? null : eslModel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.BOUND_PRODUCT
     *
     * @return the value of panda_esl.BOUND_PRODUCT
     *
     * @mbg.generated
     */
    public String getBoundProduct() {
        return boundProduct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.BOUND_PRODUCT
     *
     * @param boundProduct the value for panda_esl.BOUND_PRODUCT
     *
     * @mbg.generated
     */
    public void setBoundProduct(String boundProduct) {
        this.boundProduct = boundProduct == null ? null : boundProduct.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.BATTERY_LEVEL
     *
     * @return the value of panda_esl.BATTERY_LEVEL
     *
     * @mbg.generated
     */
    public Float getBatteryLevel() {
        return batteryLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.BATTERY_LEVEL
     *
     * @param batteryLevel the value for panda_esl.BATTERY_LEVEL
     *
     * @mbg.generated
     */
    public void setBatteryLevel(Float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.TEMPERATURE
     *
     * @return the value of panda_esl.TEMPERATURE
     *
     * @mbg.generated
     */
    public Float getTemperature() {
        return temperature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.TEMPERATURE
     *
     * @param temperature the value for panda_esl.TEMPERATURE
     *
     * @mbg.generated
     */
    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.SIGNAL_STRENGTH
     *
     * @return the value of panda_esl.SIGNAL_STRENGTH
     *
     * @mbg.generated
     */
    public Integer getSignalStrength() {
        return signalStrength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.SIGNAL_STRENGTH
     *
     * @param signalStrength the value for panda_esl.SIGNAL_STRENGTH
     *
     * @mbg.generated
     */
    public void setSignalStrength(Integer signalStrength) {
        this.signalStrength = signalStrength;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.COMMUNICATION_COUNT
     *
     * @return the value of panda_esl.COMMUNICATION_COUNT
     *
     * @mbg.generated
     */
    public Integer getCommunicationCount() {
        return communicationCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.COMMUNICATION_COUNT
     *
     * @param communicationCount the value for panda_esl.COMMUNICATION_COUNT
     *
     * @mbg.generated
     */
    public void setCommunicationCount(Integer communicationCount) {
        this.communicationCount = communicationCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.FAILURE_COUNT
     *
     * @return the value of panda_esl.FAILURE_COUNT
     *
     * @mbg.generated
     */
    public Integer getFailureCount() {
        return failureCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.FAILURE_COUNT
     *
     * @param failureCount the value for panda_esl.FAILURE_COUNT
     *
     * @mbg.generated
     */
    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.ESL_CATEGORY
     *
     * @return the value of panda_esl.ESL_CATEGORY
     *
     * @mbg.generated
     */
    public String getEslCategory() {
        return eslCategory;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.ESL_CATEGORY
     *
     * @param eslCategory the value for panda_esl.ESL_CATEGORY
     *
     * @mbg.generated
     */
    public void setEslCategory(String eslCategory) {
        this.eslCategory = eslCategory == null ? null : eslCategory.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.ESL_STATUS
     *
     * @return the value of panda_esl.ESL_STATUS
     *
     * @mbg.generated
     */
    public String getEslStatus() {
        return eslStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.ESL_STATUS
     *
     * @param eslStatus the value for panda_esl.ESL_STATUS
     *
     * @mbg.generated
     */
    public void setEslStatus(String eslStatus) {
        this.eslStatus = eslStatus == null ? null : eslStatus.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.SCREEN_COLOR
     *
     * @return the value of panda_esl.SCREEN_COLOR
     *
     * @mbg.generated
     */
    public String getScreenColor() {
        return screenColor;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.SCREEN_COLOR
     *
     * @param screenColor the value for panda_esl.SCREEN_COLOR
     *
     * @mbg.generated
     */
    public void setScreenColor(String screenColor) {
        this.screenColor = screenColor == null ? null : screenColor.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.COMMUNICATION_METHOD
     *
     * @return the value of panda_esl.COMMUNICATION_METHOD
     *
     * @mbg.generated
     */
    public String getCommunicationMethod() {
        return communicationMethod;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.COMMUNICATION_METHOD
     *
     * @param communicationMethod the value for panda_esl.COMMUNICATION_METHOD
     *
     * @mbg.generated
     */
    public void setCommunicationMethod(String communicationMethod) {
        this.communicationMethod = communicationMethod == null ? null : communicationMethod.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.VERSION
     *
     * @return the value of panda_esl.VERSION
     *
     * @mbg.generated
     */
    public String getVersion() {
        return version;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.VERSION
     *
     * @param version the value for panda_esl.VERSION
     *
     * @mbg.generated
     */
    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.HARDWARE
     *
     * @return the value of panda_esl.HARDWARE
     *
     * @mbg.generated
     */
    public String getHardware() {
        return hardware;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.HARDWARE
     *
     * @param hardware the value for panda_esl.HARDWARE
     *
     * @mbg.generated
     */
    public void setHardware(String hardware) {
        this.hardware = hardware == null ? null : hardware.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column panda_esl.EXT_JSON
     *
     * @return the value of panda_esl.EXT_JSON
     *
     * @mbg.generated
     */
    public String getExtJson() {
        return extJson;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column panda_esl.EXT_JSON
     *
     * @param extJson the value for panda_esl.EXT_JSON
     *
     * @mbg.generated
     */
    public void setExtJson(String extJson) {
        this.extJson = extJson == null ? null : extJson.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table panda_esl
     *
     * @mbg.generated
     */
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

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table panda_esl
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        PandaEsl other = (PandaEsl) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getSortCode() == null ? other.getSortCode() == null : this.getSortCode().equals(other.getSortCode()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getEslId() == null ? other.getEslId() == null : this.getEslId().equals(other.getEslId()))
            && (this.getStoreCode() == null ? other.getStoreCode() == null : this.getStoreCode().equals(other.getStoreCode()))
            && (this.getApSn() == null ? other.getApSn() == null : this.getApSn().equals(other.getApSn()))
            && (this.getEslModel() == null ? other.getEslModel() == null : this.getEslModel().equals(other.getEslModel()))
            && (this.getBoundProduct() == null ? other.getBoundProduct() == null : this.getBoundProduct().equals(other.getBoundProduct()))
            && (this.getBatteryLevel() == null ? other.getBatteryLevel() == null : this.getBatteryLevel().equals(other.getBatteryLevel()))
            && (this.getTemperature() == null ? other.getTemperature() == null : this.getTemperature().equals(other.getTemperature()))
            && (this.getSignalStrength() == null ? other.getSignalStrength() == null : this.getSignalStrength().equals(other.getSignalStrength()))
            && (this.getCommunicationCount() == null ? other.getCommunicationCount() == null : this.getCommunicationCount().equals(other.getCommunicationCount()))
            && (this.getFailureCount() == null ? other.getFailureCount() == null : this.getFailureCount().equals(other.getFailureCount()))
            && (this.getEslCategory() == null ? other.getEslCategory() == null : this.getEslCategory().equals(other.getEslCategory()))
            && (this.getEslStatus() == null ? other.getEslStatus() == null : this.getEslStatus().equals(other.getEslStatus()))
            && (this.getScreenColor() == null ? other.getScreenColor() == null : this.getScreenColor().equals(other.getScreenColor()))
            && (this.getCommunicationMethod() == null ? other.getCommunicationMethod() == null : this.getCommunicationMethod().equals(other.getCommunicationMethod()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getHardware() == null ? other.getHardware() == null : this.getHardware().equals(other.getHardware()))
            && (this.getExtJson() == null ? other.getExtJson() == null : this.getExtJson().equals(other.getExtJson()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table panda_esl
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getSortCode() == null) ? 0 : getSortCode().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getEslId() == null) ? 0 : getEslId().hashCode());
        result = prime * result + ((getStoreCode() == null) ? 0 : getStoreCode().hashCode());
        result = prime * result + ((getApSn() == null) ? 0 : getApSn().hashCode());
        result = prime * result + ((getEslModel() == null) ? 0 : getEslModel().hashCode());
        result = prime * result + ((getBoundProduct() == null) ? 0 : getBoundProduct().hashCode());
        result = prime * result + ((getBatteryLevel() == null) ? 0 : getBatteryLevel().hashCode());
        result = prime * result + ((getTemperature() == null) ? 0 : getTemperature().hashCode());
        result = prime * result + ((getSignalStrength() == null) ? 0 : getSignalStrength().hashCode());
        result = prime * result + ((getCommunicationCount() == null) ? 0 : getCommunicationCount().hashCode());
        result = prime * result + ((getFailureCount() == null) ? 0 : getFailureCount().hashCode());
        result = prime * result + ((getEslCategory() == null) ? 0 : getEslCategory().hashCode());
        result = prime * result + ((getEslStatus() == null) ? 0 : getEslStatus().hashCode());
        result = prime * result + ((getScreenColor() == null) ? 0 : getScreenColor().hashCode());
        result = prime * result + ((getCommunicationMethod() == null) ? 0 : getCommunicationMethod().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getHardware() == null) ? 0 : getHardware().hashCode());
        result = prime * result + ((getExtJson() == null) ? 0 : getExtJson().hashCode());
        return result;
    }
}