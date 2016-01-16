package com.elite.findmyphone.httpvisitor.exception;


import com.elite.findmyphone.httpvisitor.NetworkResponse;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 0:47
 * Description:
 */
public class NetworkError extends HttpVisitorError {
    public NetworkError() {
        super();
    }

    public NetworkError(Throwable cause) {
        super(cause);
    }

    public NetworkError(NetworkResponse networkResponse) {
        super(networkResponse);
    }
}
