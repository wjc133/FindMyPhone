package com.elite.findmyphone.core.weather.api;

import com.elite.findmyphone.api.ServerResult;
import com.elite.findmyphone.api.weather.City;
import com.elite.findmyphone.api.weather.Weather;
import com.elite.findmyphone.core.base.BaseCore;

import java.util.List;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 1:15
 * Description: 天气相关的业务接口
 */
public interface WeatherCore extends BaseCore {
    ServerResult<List<City>> getCityInfo(String cityName);

    ServerResult<Weather> getWeather(String cityName);
}
