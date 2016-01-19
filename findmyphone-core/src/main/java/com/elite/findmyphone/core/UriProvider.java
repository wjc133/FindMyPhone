package com.elite.findmyphone.core;

/**
 * Create by wjc133
 * Date: 2016/1/6
 * Time: 22:17
 */
public class UriProvider {
    private static final String PROTOCOL_HEADER_HTTP = "http://";
    private static final String PROTOCOL_HEADER_HTTPS = "https://";
    private static String TCLOUD_HOST;
    private static String BAIDU_HOST;

    public static String UPDATE;    //请求更新接口
    public static String COMMOND_GET;   //获取命令接口
    public static String UPLOAD_LOCATE;   //上传位置接口

    //for test
    public static String WEATHER_GET;
    public static String CITY_INFO_GET;


    /**
     * 初始化
     *
     * @param tcloudHostIp
     * @param baiduHostIp  用于测试HttpVisitor功能是否正常
     */
    public static void init(String tcloudHostIp, String baiduHostIp) {
        TCLOUD_HOST = PROTOCOL_HEADER_HTTP + tcloudHostIp;
        BAIDU_HOST = PROTOCOL_HEADER_HTTP + baiduHostIp;

        UPDATE = TCLOUD_HOST + "/fmp/update";
        COMMOND_GET = TCLOUD_HOST + "/fmp/commond";
        UPLOAD_LOCATE = TCLOUD_HOST + "/fmp/upload-locate";

        CITY_INFO_GET = BAIDU_HOST + "/apistore/weatherservice/citylist";
        WEATHER_GET = BAIDU_HOST + "/apistore/weatherservice/cityname";
    }
}
