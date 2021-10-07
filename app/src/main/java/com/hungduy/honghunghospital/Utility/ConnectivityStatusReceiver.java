package com.hungduy.honghunghospital.Utility;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.hungduy.honghunghospital.Activity.BaseActivity;

public class ConnectivityStatusReceiver extends BroadcastReceiver {

    BaseActivity a;
    public void setActivity(BaseActivity a){
        this.a = a;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) {
            a.Connected();
        } else {
            a.Disconnect();
        }
    }

}