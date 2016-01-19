package com.elite.findmyphone.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.elite.findmyphone.R;
import com.elite.findmyphone.common.Env;

/**
 * Create by wjc133
 * Date: 2016/1/19
 * Time: 13:19
 */
public class EnvSettingActivity extends PreferenceActivity{
    private Env.UriSetting uriSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_env);
        Button btn_dev = (Button) findViewById(R.id.btn_setting_env_dev);
        Button btn_test = (Button) findViewById(R.id.btn_setting_env_test);
        Button btn_product = (Button) findViewById(R.id.btn_setting_env_product);
        btn_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriSetting = Env.UriSetting.DEV;
            }
        });
        btn_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriSetting = Env.UriSetting.PRODUCT;
            }
        });
        btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriSetting = Env.UriSetting.TEST;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Env.instance().setUriSetting(uriSetting);
    }
}
