package com.elite.findmyphone.core.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.net.InetSocketAddress;

/**
 * Created by wjc133.
 * Date: 2016/1/3
 * Time: 13:54
 * Description: 网络工具类
 */
public class NetworkUtils {
    private static WifiManager.WifiLock sWifiLocker;
    private static final String TAG = NetworkUtils.class.getSimpleName();

    //同步的作用是保证单例
    static synchronized WifiManager.WifiLock wifiLocker(Context context) {
        if (sWifiLocker == null) {
            Log.d(TAG, "Create wifiManager for " + (Build.VERSION.SDK_INT >= 9 ? "WIFI_MODE_HIPREF"
                    : "WIFI_MODE_FULL"));
            sWifiLocker = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).createWifiLock(
                    Build.VERSION.SDK_INT >= 9 ? 3 : WifiManager.WIFI_MODE_FULL, "Elite");
        }
        return sWifiLocker;
    }

    public static void lockWifi(Context context) {
        Log.d(TAG, "lock wifi");
        if (!wifiLocker(context).isHeld()) {
            wifiLocker(context).acquire();
        }
    }

    public static void unlockWifi(Context context) {
        Log.d(TAG, "unlock wifi");
        if (wifiLocker(context).isHeld()) {
            wifiLocker(context).release();
        }
    }

    public static boolean isWifiActive(Context context) {
        if (context == null) {
            Log.e(TAG, "isWifiActive Param Context is NULL");
            return false;
        }
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static boolean isMobileActive(Context context) {
        if (context == null) {
            Log.e(TAG, "isMobileActive Param Context is NULL");
            return false;
        }
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = mgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public static boolean isNetworkStrictlyAvailable(Context context) {
        if (context == null) {
            Log.e(TAG, "isNetworkStrictlyAvailable Param Context is NULL");
            return false;
        }
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mgr == null) {
            Log.e(TAG, "isNetworkStrictlyAcailable mgr is NULL");
            return false;
        }
        NetworkInfo info = mgr.getActiveNetworkInfo();
        if (info != null && info.isAvailable() && info.isConnected()) {
            return true;
        } else {
            String str = null;
            if (info != null) {
                str = "network type =" + info.getType() + ", "
                        + (info.isAvailable() ? "available" : "not available")
                        + ", " + (info.isConnected() ? "" : "not") + " connected";
            } else {
                str = "no active network";
            }
            Log.i(TAG, "network information: " + str);
            return false;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        if (null == context) {
            return false;
        }

        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mgr.getActiveNetworkInfo();
        return info != null && (info.isConnected() || (info.isAvailable() && info.isConnectedOrConnecting()));
    }

    /**
     * 打开系统的无线网络设置Activity
     * @param context 上下文
     */
    public static void openNetworkConfig(Context context) {
        Intent intent;
        if (Build.VERSION.SDK_INT > 10) {
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setAction(Intent.ACTION_MAIN);
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int MIN_PORT=0;
    public static final int MAX_PORT = 65535;
    public static final int DEFAULT_PROXY_PORT = 80;

    public static InetSocketAddress getTunnelProxy(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.WRITE_APN_SETTINGS") == PackageManager.PERMISSION_DENIED) {
            return null;
        }
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = mgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return null;
            }
        }
        //todo 没写完
        return null;
    }

}
