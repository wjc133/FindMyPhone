package com.elite.findmyphone.core.loader;

import android.content.Context;

/**
 * Create by wjc133
 * Refer to the class create by lijun
 * Date: 2016/1/18
 * Time: 18:32
 * 同步加载数据的Loader
 */
public abstract class DataLoader<T> extends BaseLoader<Data<T>> {
    public DataLoader(Context context) {
        super(context);
    }

    public DataLoader(Context context, boolean destroyOnLoadFinish) {
        super(context, destroyOnLoadFinish);
    }

    protected void dispatchDataOnLoadComplete(T data) {
        super.dispatchOnLoadComplete(new Data<T>(data));
    }

    @Override
    protected void dispatchOnLoadComplete(Data<T> data) {
        throw new RuntimeException("Should use dispatchDataOnLoadComplete(T data)");
    }
}
