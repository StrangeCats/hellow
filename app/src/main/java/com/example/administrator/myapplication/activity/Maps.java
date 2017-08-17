package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.view.EduSohoIconView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import utils.SPUtil;

/**
 * Created by Administrator on 2017/5/3.
 */

public class Maps extends BaseActivity implements SeekBar.OnSeekBarChangeListener{
    public int i=20;
    @ViewInject(R.id.text)
    TextView tv;
    @ViewInject(R.id.seekBar)
            SeekBar seekBar;
    @ViewInject(R.id.back)
    EduSohoIconView back;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                int a=(int)msg.obj;

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maps_layout);
        ViewUtils.inject(this);
        seekBar.setOnSeekBarChangeListener(this);
        back.setText(R.string.back);
        back.setTextSize(20);

    }
    public void refresh(View view){
        i+=20;
       new Thread(new Runnable() {
           @Override
           public void run() {

                   Message message= new Message();
                   message.obj=i;
                   message.what=1;
                    handler.sendMessage(message);


           }
       }).start();


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tv.setTextSize(progress*10);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
