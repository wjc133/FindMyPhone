package com.elite.findmyphone.httpvisitor.request;


import com.elite.findmyphone.httpvisitor.NetworkResponse;
import com.elite.findmyphone.httpvisitor.Response;
import com.elite.findmyphone.httpvisitor.exception.ParseError;
import com.elite.findmyphone.httpvisitor.utils.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 1:42
 * Description:返回JSON的Request
 */
public class JsonObjectRequest extends JsonRequest<JSONObject> {
    public JsonObjectRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    public JsonObjectRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            return Response.success(new JSONObject(jsonString));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
