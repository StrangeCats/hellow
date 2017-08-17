package com.example.administrator.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.ItemAdapter;
import com.example.administrator.myapplication.mode.CodeMode;
import com.example.administrator.myapplication.mode.DataMode;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */

public class ItemActivity extends Activity {
    @ViewInject(R.id.lv)
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.itemlayout);
        ViewUtils.inject(this);
        Intent intent =this.getIntent();
        CodeMode.ResultBean.ListBean.RecipeBean db=(CodeMode.ResultBean.ListBean.RecipeBean) intent.getSerializableExtra("my");
        List<CodeMode.ResultBean.ListBean.RecipeBean.methodBean> dbs= new ArrayList<CodeMode.ResultBean.ListBean.RecipeBean.methodBean>();
        dbs=db.getMethodBeanList();
        ItemAdapter itemAdapter = new ItemAdapter(this,dbs);
        lv.setAdapter(itemAdapter);
    }
}
