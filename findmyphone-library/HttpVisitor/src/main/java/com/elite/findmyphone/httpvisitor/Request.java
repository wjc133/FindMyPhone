package com.elite.findmyphone.httpvisitor;


import com.elite.findmyphone.httpvisitor.utils.InternalUtils;

/**
 * Created by wjc133.
 * Date: 2016/1/8
 * Time: 17:43
 * Description:请求实体类
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

    private final String mUrl;
    private final int mMethod;
    // TODO: 2016/1/8 暂时不考虑重定向
    private String mIdentifier;
    private Response.ErrorListener mErrorListener;
    private Integer mSequence;
    private RequestQueue mRequestQueue;
    private boolean mCanceled = false;
    /**
     * 当前response是否已经被分发
     **/
    private boolean mResponseDelivered = false;
    private Object mTag;

    //为什么只有ErrorListener没有Listener
    //因为Listener返回的Response根据具体场景所包含的data不同

    public Request(String url, int method, Response.ErrorListener errorListener) {
        this.mMethod = method;
        this.mUrl = url;
        this.mIdentifier = createIdentifier(method, url);
        this.mErrorListener = errorListener;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getMethod() {
        return mMethod;
    }

    public Object getTag() {
        return mTag;
    }

    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public Response.ErrorListener getErrorListener() {
        return mErrorListener;
    }

    protected void finish(final String tag) {
        if (mRequestQueue != null) {
            mRequestQueue.finish(this);
            onFinish();
        }
    }

    protected void onFinish() {
        mErrorListener = null;
    }

    public void setmRequestQueue(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }

    public final Request<?> setSequence(int sequence) {
        mSequence = sequence;
        return this;
    }

    private static long sCounter;

    /**
     * sha1(Request:method:url:timestamp:counter)
     *
     * @param method http method
     * @param url    http request url
     * @return sha1 hash string
     */
    private static String createIdentifier(final int method, final String url) {
        return InternalUtils.sha1Hash("Request:" + method + ":" + url +
                ":" + System.currentTimeMillis() + ":" + (sCounter++));
    }

    public int compareTo(Request<T> another) {
        return 0;
    }
}
