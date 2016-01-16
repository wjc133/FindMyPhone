package com.elite.findmyphone.httpvisitor;

import com.elite.findmyphone.httpvisitor.request.JsonObjectRequest;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create by wjc133
 * Date: 2015/12/30
 * Time: 22:00
 */
public class HttpVisitor {
    private static final HttpVisitor HTTP_VISITOR = new HttpVisitor();
    private static final RequestQueue REQUEST_QUEUE = newRequestQueue(null);
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public static RequestQueue newRequestQueue(NetWorker worker) {
        if (worker == null) {
            worker = new NormalNetWorker();
        }
        RequestQueue queue = new RequestQueue(worker);
        queue.start();
        return queue;
    }

    public static RequestQueue newRequestQueue() {
        return REQUEST_QUEUE;
    }

    private HttpVisitor() {
    }

    public static HttpVisitor getInstance() {
        return HTTP_VISITOR;
    }

    public JSONObject doGetJsonObjectSync(int method, String url) {
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, future, future);
        REQUEST_QUEUE.add(request);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
