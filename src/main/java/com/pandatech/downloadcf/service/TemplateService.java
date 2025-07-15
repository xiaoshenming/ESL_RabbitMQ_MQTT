package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;

public interface TemplateService {

    /**
     * 下发模板
     *
     * @param templateDto 模板数据
     */
    void sendTemplate(TemplateDto templateDto);

    /**
     * 刷新价签
     *
     * @param refreshDto 刷新数据
     */
    void refreshEsl(RefreshDto refreshDto);

    byte[] loadTemple(LoadTemplateRequest request);
}