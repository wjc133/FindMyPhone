package com.elite.findmyphone.core.loader;

import android.content.Context;

/**
 * Created by wjc133.
 * Refer to the class create by lijun
 * Date: 2016/1/17
 * Time: 14:00
 * Description: 异步加载数据的Loader
 */
public abstract class AsyncDataLoader<T> extends BaseLoader<T> {

    public AsyncDataLoader(Context context) {
        super(context);
    }

    public AsyncDataLoader(Context context, boolean destroyOnLoadFinish) {
        super(context, destroyOnLoadFinish);
    }
}
