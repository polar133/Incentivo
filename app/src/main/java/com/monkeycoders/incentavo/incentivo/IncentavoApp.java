package com.monkeycoders.incentavo.incentivo;

import android.app.Application;

import com.monkeycoders.incentavo.incentivo.network.RequestManager;
public class IncentavoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.getInstance(getApplicationContext());
    }
}
