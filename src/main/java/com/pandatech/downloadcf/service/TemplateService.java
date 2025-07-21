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

    /**
     * 加载模板内容
     *
     * @param request 模板请求参数
     * @return 模板内容的字节数组
     */
    byte[] loadTemple(LoadTemplateRequest request);
    
    /**
     * 获取模板文件名
     * 保持 {模板名称}_{屏幕类型}.json 的格式
     *
     * @param request 模板请求参数
     * @return 格式化的模板文件名
     */
    String getTemplateFileName(LoadTemplateRequest request);
}