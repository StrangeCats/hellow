package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.view.StatusBarCompat;


/**
 * Created by Administrator on 2017/1/18.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(this, getResources().getColor(R.color.my_black));
        AndroidNetworking.initialize(getApplicationContext());



    }


}
