package com.pandatech.downloadcf.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PandaProduct implements Serializable {
    private String id;

    private String tenantId;

    private String deleteFlag;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private String productId;

    private String storeCode;

    private String productName;

    private String productCategory;

    private String productSpecification;

    private BigDecimal productCostPrice;

    private BigDecimal productRetailPrice;

    private BigDecimal productSalePrice;

    private BigDecimal productDiscountPrice;

    private BigDecimal productWholesalePrice;

    private String productMaterial;

    private String productImage;

    private String productUnit;

    private BigDecimal productWeight;

    private String productStatus;

    private Integer productStock;

    private String eslTemplate;

    private String eslTemplateId;

    private String storeCodeId;

    private String goodsSpec;

    private String goodsUnit;

    private String goodsOrigin;

    private String goodsPromotion;

    private String model;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode == null ? null : storeCode.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory == null ? null : productCategory.trim();
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification == null ? null : productSpecification.trim();
    }

    public BigDecimal getProductCostPrice() {
        return productCostPrice;
    }

    public void setProductCostPrice(BigDecimal productCostPrice) {
        this.productCostPrice = productCostPrice;
    }

    public BigDecimal getProductRetailPrice() {
        return productRetailPrice;
    }

    public void setProductRetailPrice(BigDecimal productRetailPrice) {
        this.productRetailPrice = productRetailPrice;
    }

    public BigDecimal getProductSalePrice() {
        return productSalePrice;
    }

    public void setProductSalePrice(BigDecimal productSalePrice) {
        this.productSalePrice = productSalePrice;
    }

    public BigDecimal getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(BigDecimal productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public BigDecimal getProductWholesalePrice() {
        return productWholesalePrice;
    }

    public void setProductWholesalePrice(BigDecimal productWholesalePrice) {
        this.productWholesalePrice = productWholesalePrice;
    }

    public String getProductMaterial() {
        return productMaterial;
    }

    public void setProductMaterial(String productMaterial) {
        this.productMaterial = productMaterial == null ? null : productMaterial.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public String getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit == null ? null : productUnit.trim();
    }

    public BigDecimal getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(BigDecimal productWeight) {
        this.productWeight = productWeight;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus == null ? null : productStatus.trim();
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public String getEslTemplate() {
        return eslTemplate;
    }

    public void setEslTemplate(String eslTemplate) {
        this.eslTemplate = eslTemplate == null ? null : eslTemplate.trim();
    }

    public String getEslTemplateId() {
        return eslTemplateId;
    }

    public void setEslTemplateId(String eslTemplateId) {
        this.eslTemplateId = eslTemplateId == null ? null : eslTemplateId.trim();
    }

    public String getStoreCodeId() {
        return storeCodeId;
    }

    public void setStoreCodeId(String storeCodeId) {
        this.storeCodeId = storeCodeId == null ? null : storeCodeId.trim();
    }

    public String getGoodsSpec() {
        return goodsSpec;
    }

    public void setGoodsSpec(String goodsSpec) {
        this.goodsSpec = goodsSpec;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsOrigin() {
        return goodsOrigin;
    }

    public void setGoodsOrigin(String goodsOrigin) {
        this.goodsOrigin = goodsOrigin;
    }

    public String getGoodsPromotion() {
        return goodsPromotion;
    }

    public void setGoodsPromotion(String goodsPromotion) {
        this.goodsPromotion = goodsPromotion;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", createTime=").append(createTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", productId=").append(productId);
        sb.append(", storeCode=").append(storeCode);
        sb.append(", productName=").append(productName);
        sb.append(", productCategory=").append(productCategory);
        sb.append(", productSpecification=").append(productSpecification);
        sb.append(", productCostPrice=").append(productCostPrice);
        sb.append(", productRetailPrice=").append(productRetailPrice);
        sb.append(", productSalePrice=").append(productSalePrice);
        sb.append(", productDiscountPrice=").append(productDiscountPrice);
        sb.append(", productWholesalePrice=").append(productWholesalePrice);
        sb.append(", productMaterial=").append(productMaterial);
        sb.append(", productImage=").append(productImage);
        sb.append(", productUnit=").append(productUnit);
        sb.append(", productWeight=").append(productWeight);
        sb.append(", productStatus=").append(productStatus);
        sb.append(", productStock=").append(productStock);
        sb.append(", eslTemplate=").append(eslTemplate);
        sb.append(", eslTemplateId=").append(eslTemplateId);
        sb.append(", storeCodeId=").append(storeCodeId);
        sb.append("]");
        return sb.toString();
    }
}