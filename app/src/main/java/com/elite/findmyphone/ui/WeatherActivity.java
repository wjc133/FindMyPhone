package com.elite.findmyphone.ui;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.elite.findmyphone.R;
import com.elite.findmyphone.api.weather.City;
import com.elite.findmyphone.api.weather.Weather;
import com.elite.findmyphone.core.CoreError;
import com.elite.findmyphone.core.common.model.UIResponse;
import com.elite.findmyphone.core.loader.Data;
import com.elite.findmyphone.core.loader.callback.UIResponseLoaderCallback;
import com.elite.findmyphone.core.weather.loader.CityLoader;
import com.elite.findmyphone.ui.common.BaseActivity;

import java.util.List;

/**
 * Create by wjc133
 * Date: 2016/1/19
 * Time: 18:47
 */
public class WeatherActivity extends BaseActivity {
    private Button startBtn;
    private TextView resultTextView;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        startBtn = (Button) findViewById(R.id.btn_start);
        resultTextView = (TextView) findViewById(R.id.texture);
        input = (EditText) findViewById(R.id.edit_cityname);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLoader(0, null, new CityLoaderCallback());
            }
        });
    }

    private class CityLoaderCallback extends UIResponseLoaderCallback<List<City>> {
        @Override
        protected void onLoadFinishedSafety(Loader<Data<UIResponse<List<City>>>> loader, UIResponse<List<City>> data) {
            loadData(data.getData());
        }

        @Override
        public Loader<Data<UIResponse<List<City>>>> onCreateLoader(int id, Bundle args) {
            return new CityLoader(WeatherActivity.this, input.getText().toString());
        }

        @Override
        public void onLoaderReset(Loader<Data<UIResponse<List<City>>>> loader) {

        }

        @Override
        public void onLoadError(Loader<Data<UIResponse<List<City>>>> loader, CoreError error) {
            super.onLoadError(loader, error);
            Toast.makeText(WeatherActivity.this, "请求失败", Toast.LENGTH_LONG).show();
        }
    }

    private void loadData(List<City> data) {
        resultTextView.setText(data.toString());
    }

}
