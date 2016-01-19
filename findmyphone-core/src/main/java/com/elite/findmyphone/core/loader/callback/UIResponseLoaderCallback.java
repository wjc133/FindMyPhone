package com.elite.findmyphone.core.loader.callback;

import android.support.v4.content.Loader;

import com.elite.findmyphone.core.CoreError;
import com.elite.findmyphone.core.common.exception.CoreException;
import com.elite.findmyphone.core.common.model.UIResponse;
import com.elite.findmyphone.core.loader.Data;

/**
 * Create by wjc133
 * Date: 2015/11/4
 * Time: 16:56
 */
public abstract class UIResponseLoaderCallback<T> implements FilterDataLoaderCallback<UIResponse<T>> {
    @Override
    public final void onLoadFinished(Loader<Data<UIResponse<T>>> loader, UIResponse<T> data) {
        if (data == null) {
            //// TODO: 2015/11/4 错误处理
        }else if (null != data.getError()) {
            onLoadError(loader,data.getError());
        } else {
            onLoadFinishedSafety(loader, data);
        }

    }

    protected abstract void onLoadFinishedSafety(Loader<Data<UIResponse<T>>> loader, UIResponse<T> data);

    /**
     * 子类重载时需要调用 父类的方法才能保证错误被处理。
     * @param loader
     * @param error
     */
    public void onLoadError(Loader<Data<UIResponse<T>>> loader, CoreError error) {
//        ExceptionService.handleException(onGetContext(), error, onGetMethodType() == REQUEST_METHOD_GET);
    }
}

