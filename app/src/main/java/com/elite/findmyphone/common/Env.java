package com.elite.findmyphone.common;

import com.elite.findmyphone.R;
import com.elite.findmyphone.core.utils.BasicConfig;
import com.elite.findmyphone.core.utils.pref.CommonPref;

/**
 * Create by wjc133
 * Date: 2016/1/6
 * Time: 22:36
 * 环境配置类
 */
public class Env {
    public static final String PREF_URI_SETTING = "PREF_URI_SETTING";

    private static final Env mEnv = new Env();

    private Env() {
    }

    public static Env instance() {
        return mEnv;
    }

    public void init() {
        if (!isReleaseEnv()) {
            UriManager.init(getUriSetting(), BasicConfig.INSTANCE.getAppContext());
        } else {
            UriManager.init(UriSetting.PRODUCT, BasicConfig.INSTANCE.getAppContext());
        }
    }

    /**
     * 设置uri环境
     *
     * @param uriSetting
     */
    public void setUriSetting(UriSetting uriSetting) {
        if (uriSetting != null && !isReleaseEnv()) {
            CommonPref.instance(BasicConfig.INSTANCE.getAppContext()).putInt(PREF_URI_SETTING, uriSetting.ordinal());
            UriManager.init(uriSetting, BasicConfig.INSTANCE.getAppContext());
        }
    }

    /**
     * 取uri环境
     *
     * @return
     */
    public UriSetting getUriSetting() {
        if (isReleaseEnv()) {
            return UriSetting.PRODUCT;
        }

        int ordinal = CommonPref.instance(BasicConfig.INSTANCE.getAppContext()).getInt(PREF_URI_SETTING, -1);
        if (ordinal > -1 && ordinal < UriSetting.values().length) {
            return UriSetting.values()[ordinal];
        } else {
            if (getBuildEnv() == BuildEnv.DEV) {
                return UriSetting.DEV;
            }

            return UriSetting.TEST;
        }
    }

    public static boolean isReleaseEnv() {
        return getBuildEnv() == BuildEnv.PRODUCT;
    }

    public static BuildEnv getBuildEnv() {
        BuildEnv env = BuildEnv.PRODUCT;
        String mvnEnv = BasicConfig.INSTANCE.getAppContext().getString(R.string.current_env);
        switch (mvnEnv) {
            case "dev":
                env = BuildEnv.DEV;
                break;
            case "test":
                env = BuildEnv.TEST;
                break;
            case "release":
                env = BuildEnv.PRODUCT;
                break;
            default:
                break;
        }

        return env;
    }

    public enum UriSetting {
        DEV, PRODUCT, TEST
    }

    public enum BuildEnv {
        DEV, TEST, PRODUCT
    }
}
