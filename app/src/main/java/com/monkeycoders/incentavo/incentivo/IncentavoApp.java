package com.monkeycoders.incentavo.incentivo;

import android.app.Application;

import com.monkeycoders.incentavo.incentivo.network.RequestManager;
import com.monkeycoders.incentavo.incentivo.utils.DBHelper;

import ollie.Ollie;

public class IncentavoApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.getInstance(getApplicationContext());
        DBHelper.init(getApplicationContext());
    }
}
