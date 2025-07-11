package com.pandatech.downloadcf.entity;

import java.io.Serializable;

public class PlatformExchangeWithBLOBs extends PlatformExchange implements Serializable {
    private String chuangfengTemplate;

    private String platformTemplate;

    private String yaliangTemplate;

    private static final long serialVersionUID = 1L;

    public String getChuangfengTemplate() {
        return chuangfengTemplate;
    }

    public void setChuangfengTemplate(String chuangfengTemplate) {
        this.chuangfengTemplate = chuangfengTemplate == null ? null : chuangfengTemplate.trim();
    }

    public String getPlatformTemplate() {
        return platformTemplate;
    }

    public void setPlatformTemplate(String platformTemplate) {
        this.platformTemplate = platformTemplate == null ? null : platformTemplate.trim();
    }

    public String getYaliangTemplate() {
        return yaliangTemplate;
    }

    public void setYaliangTemplate(String yaliangTemplate) {
        this.yaliangTemplate = yaliangTemplate == null ? null : yaliangTemplate.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", chuangfengTemplate=").append(chuangfengTemplate);
        sb.append(", platformTemplate=").append(platformTemplate);
        sb.append(", yaliangTemplate=").append(yaliangTemplate);
        sb.append("]");
        return sb.toString();
    }
}