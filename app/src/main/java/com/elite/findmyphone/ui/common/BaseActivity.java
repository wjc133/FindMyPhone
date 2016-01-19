package com.elite.findmyphone.ui.common;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.elite.findmyphone.core.loader.callback.FilterDataLoaderCallback;
import com.elite.findmyphone.core.loader.callback.FilterDataLoaderCallbackAdapter;

/**
 * Create by wjc133
 * Date: 2016/1/19
 * Time: 18:45
 */
public class BaseActivity extends AppCompatActivity implements LoaderController {
    private SparseArray<FilterDataLoaderCallbackAdapter> mFilterDataLoaderCallbackAdapters = new SparseArray<>();

    @SuppressWarnings("unchecked")
    private FilterDataLoaderCallbackAdapter getFilterDataLoaderCallbackAdapter(int loaderId, FilterDataLoaderCallback callback, LoaderManager manager) {
        if (mFilterDataLoaderCallbackAdapters.indexOfKey(loaderId) < 0) {
            mFilterDataLoaderCallbackAdapters.put(loaderId, new FilterDataLoaderCallbackAdapter<>(callback, manager));
        }

        return mFilterDataLoaderCallbackAdapters.get(loaderId);
    }

    @Override
    public void initLoader(int loaderId, Bundle bundle, FilterDataLoaderCallback callback) {
        getSupportLoaderManager().initLoader(loaderId, bundle, getFilterDataLoaderCallbackAdapter(loaderId, callback, getSupportLoaderManager()));
    }

    @Override
    public <D> void restartLoader(boolean isForceLoad, int loaderId, Bundle bundle, FilterDataLoaderCallback<D> callback) {
        getSupportLoaderManager().restartLoader(loaderId, bundle, getFilterDataLoaderCallbackAdapter(loaderId, callback, getSupportLoaderManager()));
    }
}
