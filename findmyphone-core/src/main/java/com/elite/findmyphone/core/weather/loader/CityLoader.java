package com.elite.findmyphone.core.weather.loader;

import android.content.Context;

import com.elite.findmyphone.api.weather.City;
import com.elite.findmyphone.core.common.exception.CoreException;
import com.elite.findmyphone.core.common.model.UIResponse;
import com.elite.findmyphone.core.loader.Data;
import com.elite.findmyphone.core.loader.UIResponseAsyncDataLoader;
import com.elite.findmyphone.core.weather.api.WeatherCore;
import com.elite.findmyphone.core.weather.api.WeatherCoreImpl;

import java.util.List;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 13:59
 * Description:城市信息Loader
 */
public class CityLoader extends UIResponseAsyncDataLoader<List<City>> {
    private String cityName;
    private WeatherCore mApiCore;

    public CityLoader(Context context, String cityName) {
        super(context);
        this.mApiCore = new WeatherCoreImpl();
        this.cityName = cityName;
    }

    @Override
    protected Data<UIResponse<List<City>>> loadInBackgroundSafety() throws CoreException {
        if (cityName == null) {
            return null;
        }
        return new Data<>(UIResponse.success(getCityInfo()));
    }

    private List<City> getCityInfo() throws CoreException {
        return mApiCore.getCityInfo(cityName);
    }

}
