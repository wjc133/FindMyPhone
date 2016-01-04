package com.elite.findmyphone.core.locate;

import com.elite.findmyphone.api.locate.Location;
import com.elite.findmyphone.core.base.BaseCore;

/**
 * Create by wjc133
 * Date: 2016/1/4
 * Time: 18:41
 */
public interface LocateCore extends BaseCore {
    void reportLocation(long uid, Location location);
}
