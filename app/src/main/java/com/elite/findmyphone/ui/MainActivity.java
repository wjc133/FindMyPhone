package com.elite.findmyphone.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elite.findmyphone.R;
import com.elite.findmyphone.api.ServerResult;
import com.elite.findmyphone.api.weather.City;
import com.elite.findmyphone.core.weather.api.WeatherCoreImpl;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                ServerResult<List<City>> result = (ServerResult<List<City>>) msg.obj;
                TextView textView = (TextView) findViewById(R.id.result);
                textView.setText(result.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button) findViewById(R.id.send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        ServerResult<List<City>> result = new WeatherCoreImpl().getCityInfo("北京");
                        Message message = new Message();
                        message.what = 1;
                        message.obj = result;
                        handler.sendMessage(message);
                    }
                }.start();
            }
        });
    }
}
