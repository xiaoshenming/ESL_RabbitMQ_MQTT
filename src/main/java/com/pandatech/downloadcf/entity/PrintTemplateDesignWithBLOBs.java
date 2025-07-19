package com.pandatech.downloadcf.entity;

import java.io.Serializable;

public class PrintTemplateDesignWithBLOBs extends PrintTemplateDesign implements Serializable {
    private String content;

    private String extJson;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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
        sb.append(", content=").append(content);
        sb.append(", extJson=").append(extJson);
        sb.append("]");
        return sb.toString();
    }
}