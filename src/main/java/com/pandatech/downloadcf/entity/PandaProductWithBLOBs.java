package com.pandatech.downloadcf.entity;

import java.io.Serializable;

public class PandaProductWithBLOBs extends PandaProduct implements Serializable {
    private String extJson;

    private String productDescription;

    private static final long serialVersionUID = 1L;

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson == null ? null : extJson.trim();
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription == null ? null : productDescription.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", extJson=").append(extJson);
        sb.append(", productDescription=").append(productDescription);
        sb.append("]");
        return sb.toString();
    }
}