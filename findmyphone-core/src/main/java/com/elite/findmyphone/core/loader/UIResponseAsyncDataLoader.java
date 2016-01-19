package com.elite.findmyphone.core.loader;

import android.content.Context;

import com.elite.findmyphone.core.CoreError;
import com.elite.findmyphone.core.common.exception.CoreException;
import com.elite.findmyphone.core.common.model.UIResponse;

/**
 * Create by wjc133
 * Date: 2016/1/19
 * Time: 17:48
 */
public abstract class UIResponseAsyncDataLoader<T> extends AsyncDataLoader<UIResponse<T>> {

    public UIResponseAsyncDataLoader(Context context) {
        super(context);
    }

    public UIResponseAsyncDataLoader(Context context, boolean destroyOnLoadFinish) {
        super(context, destroyOnLoadFinish);
    }

    @Override
    public Data<UIResponse<T>> loadInBackground() {
        UIResponse<T> response;
        try {
            return loadInBackgroundSafety();
        } catch (CoreException e) {
            response = UIResponse.error(e.getError());
        } catch (Exception e) {
            response = UIResponse.error(CoreError.SERVER_ERROR);
        }

        return new Data<>(response);
    }

    protected abstract Data<UIResponse<T>> loadInBackgroundSafety() throws CoreException;
}
