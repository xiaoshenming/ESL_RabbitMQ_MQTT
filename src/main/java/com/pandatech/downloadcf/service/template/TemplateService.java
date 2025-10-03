package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.dto.LoadTemplateRequest;
import com.pandatech.downloadcf.dto.RefreshDto;
import com.pandatech.downloadcf.dto.TemplateDto;

/**
 * 模板服务接口
 * 提供模板下发、价签刷新和模板加载功能
 */
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

    /**
     * 加载模板内容
     *
     * @param request 模板请求参数
     * @return 模板内容的字节数组
     */
    byte[] loadTemple(LoadTemplateRequest request);

    /**
     * 获取模板文件名
     *
     * @param request 模板请求参数
     * @return 模板文件名
     */
    String getTemplateFileName(LoadTemplateRequest request);
}