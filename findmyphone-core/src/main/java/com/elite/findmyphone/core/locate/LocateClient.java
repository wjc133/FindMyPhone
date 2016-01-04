package com.elite.findmyphone.core.locate;

import com.elite.findmyphone.api.ServerResult;
import com.elite.findmyphone.core.base.CoreClient;

/**
 * Create by wjc133
 * Date: 2016/1/4
 * Time: 16:56
 */
public interface LocateClient extends CoreClient {
    void onLocateResult(ServerResult result);
}
