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

    private String operatingTemperatureRange;

    private String ipRating;

    private String version;

    private String hardware;

    private String storeCodeId;

    private String boundProductId;

    private String eslIdX;

    private String apSn;

    private String apSnId;

    private String upc1;

    private String upc2;

    private String upc3;

    private String upc4;

    private String name;

    private String code;

    private String f01;

    private String f02;

    private String f03;

    private String f04;

    private String f05;

    private String f06;

    private String f07;

    private String f08;

    private String f09;

    private String f10;

    private String f11;

    private String f20;

    private String f12;

    private String f13;

    private String f14;

    private String f32;

    private String f15;

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

    public String getOperatingTemperatureRange() {
        return operatingTemperatureRange;
    }

    public void setOperatingTemperatureRange(String operatingTemperatureRange) {
        this.operatingTemperatureRange = operatingTemperatureRange == null ? null : operatingTemperatureRange.trim();
    }

    public String getIpRating() {
        return ipRating;
    }

    public void setIpRating(String ipRating) {
        this.ipRating = ipRating == null ? null : ipRating.trim();
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

    public String getStoreCodeId() {
        return storeCodeId;
    }

    public void setStoreCodeId(String storeCodeId) {
        this.storeCodeId = storeCodeId == null ? null : storeCodeId.trim();
    }

    public String getBoundProductId() {
        return boundProductId;
    }

    public void setBoundProductId(String boundProductId) {
        this.boundProductId = boundProductId == null ? null : boundProductId.trim();
    }

    public String getEslIdX() {
        return eslIdX;
    }

    public void setEslIdX(String eslIdX) {
        this.eslIdX = eslIdX == null ? null : eslIdX.trim();
    }

    public String getApSn() {
        return apSn;
    }

    public void setApSn(String apSn) {
        this.apSn = apSn == null ? null : apSn.trim();
    }

    public String getApSnId() {
        return apSnId;
    }

    public void setApSnId(String apSnId) {
        this.apSnId = apSnId == null ? null : apSnId.trim();
    }

    public String getUpc1() {
        return upc1;
    }

    public void setUpc1(String upc1) {
        this.upc1 = upc1 == null ? null : upc1.trim();
    }

    public String getUpc2() {
        return upc2;
    }

    public void setUpc2(String upc2) {
        this.upc2 = upc2 == null ? null : upc2.trim();
    }

    public String getUpc3() {
        return upc3;
    }

    public void setUpc3(String upc3) {
        this.upc3 = upc3 == null ? null : upc3.trim();
    }

    public String getUpc4() {
        return upc4;
    }

    public void setUpc4(String upc4) {
        this.upc4 = upc4 == null ? null : upc4.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getF01() {
        return f01;
    }

    public void setF01(String f01) {
        this.f01 = f01 == null ? null : f01.trim();
    }

    public String getF02() {
        return f02;
    }

    public void setF02(String f02) {
        this.f02 = f02 == null ? null : f02.trim();
    }

    public String getF03() {
        return f03;
    }

    public void setF03(String f03) {
        this.f03 = f03 == null ? null : f03.trim();
    }

    public String getF04() {
        return f04;
    }

    public void setF04(String f04) {
        this.f04 = f04 == null ? null : f04.trim();
    }

    public String getF05() {
        return f05;
    }

    public void setF05(String f05) {
        this.f05 = f05 == null ? null : f05.trim();
    }

    public String getF06() {
        return f06;
    }

    public void setF06(String f06) {
        this.f06 = f06 == null ? null : f06.trim();
    }

    public String getF07() {
        return f07;
    }

    public void setF07(String f07) {
        this.f07 = f07 == null ? null : f07.trim();
    }

    public String getF08() {
        return f08;
    }

    public void setF08(String f08) {
        this.f08 = f08 == null ? null : f08.trim();
    }

    public String getF09() {
        return f09;
    }

    public void setF09(String f09) {
        this.f09 = f09 == null ? null : f09.trim();
    }

    public String getF10() {
        return f10;
    }

    public void setF10(String f10) {
        this.f10 = f10 == null ? null : f10.trim();
    }

    public String getF11() {
        return f11;
    }

    public void setF11(String f11) {
        this.f11 = f11 == null ? null : f11.trim();
    }

    public String getF20() {
        return f20;
    }

    public void setF20(String f20) {
        this.f20 = f20 == null ? null : f20.trim();
    }

    public String getF12() {
        return f12;
    }

    public void setF12(String f12) {
        this.f12 = f12 == null ? null : f12.trim();
    }

    public String getF13() {
        return f13;
    }

    public void setF13(String f13) {
        this.f13 = f13 == null ? null : f13.trim();
    }

    public String getF14() {
        return f14;
    }

    public void setF14(String f14) {
        this.f14 = f14 == null ? null : f14.trim();
    }

    public String getF32() {
        return f32;
    }

    public void setF32(String f32) {
        this.f32 = f32 == null ? null : f32.trim();
    }

    public String getF15() {
        return f15;
    }

    public void setF15(String f15) {
        this.f15 = f15 == null ? null : f15.trim();
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
        sb.append(", operatingTemperatureRange=").append(operatingTemperatureRange);
        sb.append(", ipRating=").append(ipRating);
        sb.append(", version=").append(version);
        sb.append(", hardware=").append(hardware);
        sb.append(", storeCodeId=").append(storeCodeId);
        sb.append(", boundProductId=").append(boundProductId);
        sb.append(", eslIdX=").append(eslIdX);
        sb.append(", apSn=").append(apSn);
        sb.append(", apSnId=").append(apSnId);
        sb.append(", upc1=").append(upc1);
        sb.append(", upc2=").append(upc2);
        sb.append(", upc3=").append(upc3);
        sb.append(", upc4=").append(upc4);
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", f01=").append(f01);
        sb.append(", f02=").append(f02);
        sb.append(", f03=").append(f03);
        sb.append(", f04=").append(f04);
        sb.append(", f05=").append(f05);
        sb.append(", f06=").append(f06);
        sb.append(", f07=").append(f07);
        sb.append(", f08=").append(f08);
        sb.append(", f09=").append(f09);
        sb.append(", f10=").append(f10);
        sb.append(", f11=").append(f11);
        sb.append(", f20=").append(f20);
        sb.append(", f12=").append(f12);
        sb.append(", f13=").append(f13);
        sb.append(", f14=").append(f14);
        sb.append(", f32=").append(f32);
        sb.append(", f15=").append(f15);
        sb.append("]");
        return sb.toString();
    }
}