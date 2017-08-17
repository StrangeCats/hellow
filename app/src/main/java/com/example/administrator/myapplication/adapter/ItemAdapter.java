package com.example.administrator.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.mode.CodeMode;
import com.example.administrator.myapplication.mode.DataMode;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ItemAdapter extends BaseAdapter {
    private List<CodeMode.ResultBean.ListBean.RecipeBean.methodBean> step;
    private Context context;

    public ItemAdapter(Context context, List<CodeMode.ResultBean.ListBean.RecipeBean.methodBean> step) {
        this.context = context;
        this.step = step;
    }

    @Override
    public int getCount() {
        return step.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item2, null);

            viewHolder = new ViewHolder();

            viewHolder.im = (ImageView) convertView.findViewById(R.id.im);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.tv_about);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String ims = step.get(position).getImg();
        String tvs = step.get(position).getStep();
        Glide.with(context).load(ims)
                .placeholder(R.drawable.a0623)// 默认图片
                .crossFade().into(viewHolder.im);
        viewHolder.tv.setText(tvs);
        return convertView;
    }

    public class ViewHolder {
        public ImageView im;
        public TextView tv;
    }
}
