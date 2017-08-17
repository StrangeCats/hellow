package com.example.administrator.myapplication.activity;

import android.app.Application;

import com.example.administrator.myapplication.utils.FontsOverride;

/**
 * Created by Administrator on 2017/3/28.
 */

public class Myapp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "MONOSPACE", "PingFang Regular.ttf");
    }
}
