package com.elite.findmyphone.core.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by wjc133
 * Refer to the class create by lijun
 * Date: 2016/1/18
 * Time: 10:01
 */
public abstract class BaseLoader<T> extends AsyncTaskLoader<T> implements ILoader {
    private T mData;
    private List<DataObserver> mObservers = new ArrayList<>();
    private boolean destroyOnLoadFinish;

    public BaseLoader(Context context) {
        super(context);
    }

    public BaseLoader(Context context, boolean destroyOnLoadFinish) {
        super(context);
        this.destroyOnLoadFinish = destroyOnLoadFinish;
    }

    /*DataObserver相关操作*/
    public void addObserver(DataObserver observer) {
        this.mObservers.add(observer);
    }

    public void clearObserver() {
        this.mObservers.clear();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mData != null) {
            deliverResult(mData);
        }

        if (mObservers.size() == 0) {
            onCreateObserver();
            if (mObservers.size() > 0) {
                for (DataObserver ob : mObservers) {
                    registerObserver(ob);
                }
            }
        }

        if (takeContentChanged() || mData == null) {
            // When the observer detects a change, it should call onContentChanged()
            // on the Loader, which will cause the next call to takeContentChanged()
            // to return true. If this is ever the case (or if the current data is
            // null), we force a new load.
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }
        if (mObservers != null && mObservers.size() > 0) {
            for (DataObserver ob : mObservers) {
                unregisterObserver(ob);
            }
            clearObserver();
            mObservers = null;
        }
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
        executeLoad();
    }

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            if (data != null) {
                releaseResources(data);
            }
        }

        T oldData = mData;
        mData = data;

        if (isStarted()) {
            super.deliverResult(mData);
        }

        if (oldData != null) {
            releaseResources(oldData);
        }
    }

    private void unregisterObserver(DataObserver ob) {

    }

    private void registerObserver(DataObserver ob) {

    }

    private void onCreateObserver() {
        // TODO: 2016/1/18
    }

    @Override
    public T loadInBackground() {
        return null;
    }

    @Override
    public boolean haveData() {
        return null != mData;
    }

    @Override
    public boolean isDestroyOnLoadFinish() {
        return false;
    }

    protected void dispatchOnLoadComplete(T data) {
        commitContentChanged();
        deliverResult(data);
    }

    /**
     * Subclasses must implement this to load data
     */
    protected abstract void executeLoad();

    protected void releaseResources(T data) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.

    }
}
