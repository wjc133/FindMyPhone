package com.elite.findmyphone.core.loader.callback;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.elite.findmyphone.core.loader.Data;
import com.elite.findmyphone.core.loader.ILoader;

/**
 * Created by lijun on 2015/4/9.
 * <p>
 * Filter the loadFinished callback which has the same data.
 * Client should init Loader("getLoaderManager().initLoader(0, null, FilterLoaderCallbackAdapter)") by this class instance,
 * keep the lifecycle of instance same as activity or fragment. And implement {@link FilterDataLoaderCallback} for receive original loader callback.
 */
public class FilterDataLoaderCallbackAdapter<T> implements LoaderManager.LoaderCallbacks<Data<T>> {

    private long mDataLastModified;

    private FilterDataLoaderCallback<T> mCallback;

    private LoaderManager mLoadManager;

    public FilterDataLoaderCallbackAdapter(FilterDataLoaderCallback<T> mCallback, LoaderManager mLoadManager) {
        this.mCallback = mCallback;
        this.mLoadManager = mLoadManager;
    }

    @Override
    public Loader<Data<T>> onCreateLoader(int id, Bundle args) {
        if (null != mCallback) {
            return mCallback.onCreateLoader(id, args);
        }

        return null;
    }

    @Override
    public void onLoadFinished(Loader<Data<T>> loader, Data<T> data) {
        if (null != data) {
            if (mDataLastModified != data.getLastModified()) {
                mDataLastModified = data.getLastModified();
                if (null != mCallback) {
                    mCallback.onLoadFinished(loader, data.getData());
                }
            }
        }

        if (loader != null && mLoadManager != null) {
            boolean isDestroy = loader instanceof ILoader && ((ILoader) loader).isDestroyOnLoadFinish();
            if (isDestroy) {
                mLoadManager.destroyLoader(loader.getId());
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Data<T>> loader) {
        if (null != mCallback) {
            mCallback.onLoaderReset(loader);
        }
    }

}
