package com.elite.findmyphone.core.loader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.elite.findmyphone.core.loader.callback.FilterDataLoaderCallbackAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijun on 2015/4/9.
 *
 */
public abstract class BaseAsyncLoader<T> extends AsyncTaskLoader<T> implements ILoader {
    private T mData;

    // The observer could be anything so long as it is able to detect content changes
    // and report them to the loader with a call to onContentChanged(). For example,
    // if you were writing a Loader which loads a list of all installed applications
    // on the device, the observer could be a BroadcastReceiver that listens for the
    // ACTION_PACKAGE_ADDED intent, and calls onContentChanged() on the particular
    // Loader whenever the receiver detects that a new application has been installed.
    // Please don’t hesitate to leave a comment if you still find this confusing! :)
    private List<DataObserver> mObservers = new ArrayList<>();

    private boolean destroyOnLoadFinish = false;

    public BaseAsyncLoader(Context context) {
        super(context);
    }

    /**
     *
     * @param context
     * @param destroyOnLoadFinish 是否在load finish时destroy，适用于一次性的loader。如执行一次POST请求，则只希望接收到一次回调，不需要缓存执行结果。
     *                            需搭配{@link FilterDataLoaderCallbackAdapter} 使用。
     */
    public BaseAsyncLoader(Context context, boolean destroyOnLoadFinish) {
        super(context);
        this.destroyOnLoadFinish = destroyOnLoadFinish;
    }

    @Override
    public boolean isDestroyOnLoadFinish() {
        return destroyOnLoadFinish;
    }

    @Override
    public abstract T loadInBackground();

    protected void onCreateObserver() {

    }

    protected void addObserver(DataObserver observer) {
        mObservers.add(observer);
    }

    protected void clearObserver() {
        mObservers.clear();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mData != null) {
            // Deliver any previously loaded data immediately.
            deliverResult(mData);
        }

        // Begin monitoring the underlying data source.
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
    public void onCanceled(T data) {
        super.onCanceled(data);
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }

        // The Loader is being reset, so we should stop monitoring for changes.
        if (mObservers != null && mObservers.size() > 0) {
            for (DataObserver ob : mObservers) {
                unregisterObserver(ob);
            }

            clearObserver();
        }
    }

    @Override
    public void deliverResult(T data) {
        if (isReset()) {
            // An async query came in while the loader is stopped.  We
            // don't need the result.
            if (data != null) {
                releaseResources(data);
            }
        }
        T oldData = mData;
        mData = data;

        if (isStarted()) {
            // If the Loader is currently started, we can immediately
            // deliver its results.
            super.deliverResult(mData);
        }

        // At this point we can release the resources associated with
        // 'oldApps' if needed; now that the new result is delivered we
        // know that it is no longer in use.
        if (oldData != null) {
            releaseResources(oldData);
        }
    }

    public T getData() {
        return mData;
    }

    public boolean haveData() {
        return null != mData;
    }

    protected void registerObserver(DataObserver observer) {

    }

    protected void unregisterObserver(DataObserver observer) {

    }

    protected void releaseResources(T data) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.

    }
}
