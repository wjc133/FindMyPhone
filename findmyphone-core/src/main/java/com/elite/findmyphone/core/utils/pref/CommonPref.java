package com.elite.findmyphone.core.utils.pref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Create by wjc133
 * Date: 2016/1/19
 * Time: 13:12
 */
public class CommonPref extends EltSharedPref {
    private static CommonPref sInst;

    private CommonPref(SharedPreferences preferences){
        super(preferences);
    }

    public synchronized static CommonPref instance(Context applicationContext) {
        if(sInst == null){
            SharedPreferences pref = applicationContext.getSharedPreferences("CommonPref", Context.MODE_PRIVATE);
            sInst = new CommonPref(pref);
        }
        return sInst;
    }
}
