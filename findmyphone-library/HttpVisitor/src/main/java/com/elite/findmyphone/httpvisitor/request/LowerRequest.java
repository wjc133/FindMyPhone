package com.elite.findmyphone.httpvisitor.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

/**
 * Create by wjc133
 * Date: 2016/1/5
 * Time: 12:09
 */
public class LowerRequest extends Request<Object> {
    public LowerRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Response<Object> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(Object response) {

    }
}
