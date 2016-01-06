package com.elite.findmyphone.common;

import com.elite.findmyphone.core.utils.BasicConfig;

/**
 * Create by wjc133
 * Date: 2016/1/6
 * Time: 22:36
 * 环境配置类
 */
public enum Env {
    // TODO: 2016/1/6 暂时不区分环境
    INSTANCE;

    public void init() {
        UriManager.init(BasicConfig.INSTANCE.getAppContext());
    }
}
