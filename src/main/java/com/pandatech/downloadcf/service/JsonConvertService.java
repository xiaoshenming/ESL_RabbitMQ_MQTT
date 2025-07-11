package com.pandatech.downloadcf.service;

import com.pandatech.downloadcf.entity.CFJson;
import com.pandatech.downloadcf.entity.PlatformJson;

public interface JsonConvertService {
    /**
     * 将PlatformJson转换为CFJson
     * @param platformJson 源数据
     * @return 转换后的CFJson
     */
    CFJson convertPlatformToCF(PlatformJson platformJson);
    
    /**
     * 将CFJson转换为PlatformJson
     * @param cfJson 源数据
     * @return 转换后的PlatformJson
     */
    PlatformJson convertCFToPlatform(CFJson cfJson);
}