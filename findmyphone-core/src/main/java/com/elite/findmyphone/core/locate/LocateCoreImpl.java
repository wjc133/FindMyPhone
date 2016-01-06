package com.elite.findmyphone.core.locate;

import com.elite.findmyphone.api.locate.Location;
import com.elite.findmyphone.core.ApiCore;
import com.elite.findmyphone.core.UriProvider;

/**
 * Create by wjc133
 * Date: 2016/1/4
 * Time: 18:42
 */
public class LocateCoreImpl extends ApiCore implements LocateCore {
    @Override
    public void reportLocation(long uid, Location location) {
        String url = UriProvider.UPLOAD_LOCATE;
        // TODO: 2016/1/6 拿到了url，就可以执行网络请求了
    }
}
