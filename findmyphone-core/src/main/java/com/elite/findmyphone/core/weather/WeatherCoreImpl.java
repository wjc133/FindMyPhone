package com.elite.findmyphone.core.weather;

import com.elite.findmyphone.api.ServerResult;
import com.elite.findmyphone.api.weather.City;
import com.elite.findmyphone.api.weather.Weather;
import com.elite.findmyphone.core.ApiCore;
import com.elite.findmyphone.core.UriProvider;
import com.elite.findmyphone.core.utils.HttpUtils;
import com.elite.findmyphone.httpvisitor.Request;
import com.elite.findmyphone.httpvisitor.RequestFuture;
import com.elite.findmyphone.httpvisitor.request.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by wjc133.
 * Date: 2016/1/17
 * Time: 1:29
 * Description:
 */
public class WeatherCoreImpl extends ApiCore implements WeatherCore {
    @SuppressWarnings("unchecked")
    @Override
    public ServerResult<List<City>> getCityInfo(String cityName) {
        String url = UriProvider.CITY_INFO_GET;
        final ServerResult<List<City>> result = new ServerResult<>();
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, future, future);
        HttpUtils.INSTANCE.getRequestQueue().add(request);
        try {
            JSONObject response = future.get();
            parseResponse(response, result);
        } catch (InterruptedException | ExecutionException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void parseResponse(JSONObject response, ServerResult<List<City>> result) throws JSONException {
        result.setCode(response.getInt("errNum"));
        result.setMessage(response.getString("errMsg"));
        if ("success".equals(result.getMessage())) {
            JSONArray retData = response.getJSONArray("retData");
            List<City> cities = new ArrayList<>();
            for (int i = 0; i < retData.length(); i++) {
                JSONObject object = retData.getJSONObject(i);
                City city = parseCity(object);
                cities.add(city);
            }
            result.setData(cities);
        }
    }

    private City parseCity(JSONObject object) throws JSONException {
        City city = new City();
        city.setProvince_cn(object.getString("province_cn"));
        city.setDistrict_cn(object.getString("district_cn"));
        city.setName_cn(object.getString("name_cn"));
        city.setName_en(object.getString("name_en"));
        city.setArea_id(object.getString("area_id"));
        return city;
    }

    @Override
    public ServerResult<Weather> getWeather(String cityName) {
        return null;
    }
}
