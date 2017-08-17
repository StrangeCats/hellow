package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.myapplication.R;

/**
 * Created by Administrator on 2017/3/23.
 */

public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutmap);
        init();
    }
    public void init(){
        WebView view=(WebView) findViewById(R.id.my_web);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl("file:///android_asset/amap.html");
//        view.loadUrl("www.baidu.com");
        view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //调用JS方法，将商家坐标设置到地图上
                view.loadUrl("javascript:addMarker(" + 104.065794 + "," + 37.657483 + ")");
            }
        });
    }
}
