package com.elite.findmyphone.core.loader;

import android.os.SystemClock;

/**
 * Create by wjc133
 * Refer to the class create by lijun
 * Date: 2016/1/18
 * Time: 18:45
 */
public class Data<T> {

    private long mLastModified;

    private T mData;

    public Data(T mData) {
        this.mData = mData;
        this.mLastModified = SystemClock.uptimeMillis();
    }

    public long getLastModified() {
        return mLastModified;
    }

    public T getData() {
        return mData;
    }

    public void updateLastModified() {
        this.mLastModified = SystemClock.uptimeMillis();
    }
}