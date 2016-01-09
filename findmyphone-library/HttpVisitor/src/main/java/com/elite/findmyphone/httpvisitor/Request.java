package com.elite.findmyphone.httpvisitor;

/**
 * Created by wjc133.
 * Date: 2016/1/8
 * Time: 17:43
 * Description:
 */
public class Request<T> implements Comparable<Request<T>> {

    private static final String DEFAULT_PARAMS_ENCODING = "UTF-8";

    /**
     * 各种请求方法的定义，目前仅支持Get和Post
     */
    public interface Method {
        int GET = 0;
        int POST = 1;
    }

//    private final String mUrl;
//
//    // TODO: 2016/1/8 暂时不考虑重定向
//
//    private String mIdentifier;
//    //// TODO: 2016/1/8 需要有Response
//    private
//
    @Override
    public int compareTo(Request<T> another) {
        return 0;
    }
}
