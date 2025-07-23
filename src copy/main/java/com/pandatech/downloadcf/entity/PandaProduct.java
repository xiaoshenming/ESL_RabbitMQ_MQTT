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

    private String productBrand;

    private BigDecimal productCostPrice;

    private BigDecimal productRetailPrice;

    private BigDecimal productMembershipPrice;

    private BigDecimal productDiscountPrice;

    private BigDecimal productDiscount;

    private BigDecimal productWholesalePrice;

    private String productMaterial;

    private String productImage;

    private String productOrigin;

    private String productQrcode;

    private String productBarcode;

    private String productUnit;

    private BigDecimal productWeight;

    private String productStatus;

    private Integer productStock;

    private String eslTemplateCode;

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

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand == null ? null : productBrand.trim();
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

    public BigDecimal getProductMembershipPrice() {
        return productMembershipPrice;
    }

    public void setProductMembershipPrice(BigDecimal productMembershipPrice) {
        this.productMembershipPrice = productMembershipPrice;
    }

    public BigDecimal getProductDiscountPrice() {
        return productDiscountPrice;
    }

    public void setProductDiscountPrice(BigDecimal productDiscountPrice) {
        this.productDiscountPrice = productDiscountPrice;
    }

    public BigDecimal getProductDiscount() {
        return productDiscount;
    }

    public void setProductDiscount(BigDecimal productDiscount) {
        this.productDiscount = productDiscount;
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

    public String getProductOrigin() {
        return productOrigin;
    }

    public void setProductOrigin(String productOrigin) {
        this.productOrigin = productOrigin == null ? null : productOrigin.trim();
    }

    public String getProductQrcode() {
        return productQrcode;
    }

    public void setProductQrcode(String productQrcode) {
        this.productQrcode = productQrcode == null ? null : productQrcode.trim();
    }

    public String getProductBarcode() {
        return productBarcode;
    }

    public void setProductBarcode(String productBarcode) {
        this.productBarcode = productBarcode == null ? null : productBarcode.trim();
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

    public String getEslTemplateCode() {
        return eslTemplateCode;
    }

    public void setEslTemplateCode(String eslTemplateCode) {
        this.eslTemplateCode = eslTemplateCode == null ? null : eslTemplateCode.trim();
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
        sb.append(", productBrand=").append(productBrand);
        sb.append(", productCostPrice=").append(productCostPrice);
        sb.append(", productRetailPrice=").append(productRetailPrice);
        sb.append(", productMembershipPrice=").append(productMembershipPrice);
        sb.append(", productDiscountPrice=").append(productDiscountPrice);
        sb.append(", productDiscount=").append(productDiscount);
        sb.append(", productWholesalePrice=").append(productWholesalePrice);
        sb.append(", productMaterial=").append(productMaterial);
        sb.append(", productImage=").append(productImage);
        sb.append(", productOrigin=").append(productOrigin);
        sb.append(", productQrcode=").append(productQrcode);
        sb.append(", productBarcode=").append(productBarcode);
        sb.append(", productUnit=").append(productUnit);
        sb.append(", productWeight=").append(productWeight);
        sb.append(", productStatus=").append(productStatus);
        sb.append(", productStock=").append(productStock);
        sb.append(", eslTemplateCode=").append(eslTemplateCode);
        sb.append("]");
        return sb.toString();
    }
}