package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mode.CodeMode;

import java.util.List;

import utils.SPUtil;

/**
 * Created by Administrator on 2017/3/27.
 */

public class NewAdapter extends BaseAdapter {
    public List<CodeMode.ResultBean.ListBean> data;
    public Context context;
    public NewAdapter(Context context,List<CodeMode.ResultBean.ListBean> myListBeen){
        this.context=context;
        this.data=myListBeen;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHold=null;
        if (convertView==null){
            convertView=View.inflate(context, R.layout.item,null);
            viewHold=new ViewHolder();
            viewHold.im= (ImageView)convertView.findViewById(R.id.im);
            viewHold.tv_cm=(TextView)convertView.findViewById(R.id.tv_cm);
            viewHold.tv_ab=(TextView)convertView.findViewById(R.id.tv_about);
            convertView.setTag(viewHold);

        }else {
            viewHold = (ViewHolder) convertView.getTag();
//            viewHold.tv_ab.setTypeface(SPUtil.getTypeface(context));
        }
        String imgurl = data.get(position).getRecipe().getImg();

        String tatle=data.get(position).getName();
        String imtro=data.get(position).getRecipe().getIngredients();

        Glide.with(context).load(imgurl)
                .placeholder(R.drawable.a0623)// 默认图片
                .crossFade().into(viewHold.im);
        if (tatle == null || ("").endsWith(tatle)){
            viewHold.tv_cm.setText("无");
        }else {
            viewHold.tv_cm.setText(tatle);
        }
        if (imtro == null || ("").endsWith(imtro)){
            viewHold.tv_ab.setText("无");
        }else {
            viewHold.tv_ab.setText(imtro);

        }



        return convertView;
    }
    private class ViewHolder{
        public ImageView im;
        public TextView tv_cm;
        public TextView tv_ab;
    }
}
