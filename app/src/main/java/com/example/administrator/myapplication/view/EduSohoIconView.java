package com.example.administrator.myapplication.view;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/1/18.
 */

public class EduSohoIconView extends TextView {

    private Context mContext;

    public EduSohoIconView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public EduSohoIconView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView()
    {
        Typeface iconfont = Typeface.createFromAsset(mContext.getAssets(), "iconfont.ttf");
        setTypeface(iconfont);
    }
}

