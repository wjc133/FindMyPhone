package com.elite.findmyphone.core.loader.callback;

import android.os.Bundle;
import android.support.v4.content.Loader;

import com.elite.findmyphone.core.loader.Data;

/**
 * Created by lijun on 2015/4/9.
 */
public interface FilterDataLoaderCallback<D> {

    Loader<Data<D>> onCreateLoader(int id, Bundle args);

    void onLoadFinished(Loader<Data<D>> loader, D data);

    void onLoaderReset(Loader<Data<D>> loader);
}

