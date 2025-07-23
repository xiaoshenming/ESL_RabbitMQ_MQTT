package com.pandatech.downloadcf.entity;

import java.io.Serializable;

public class PandaEslWithBLOBs extends PandaEsl implements Serializable {
    private String extJson;

    private String eslTemplateJson;

    private static final long serialVersionUID = 1L;

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson == null ? null : extJson.trim();
    }

    public String getEslTemplateJson() {
        return eslTemplateJson;
    }

    public void setEslTemplateJson(String eslTemplateJson) {
        this.eslTemplateJson = eslTemplateJson == null ? null : eslTemplateJson.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", extJson=").append(extJson);
        sb.append(", eslTemplateJson=").append(eslTemplateJson);
        sb.append("]");
        return sb.toString();
    }
}