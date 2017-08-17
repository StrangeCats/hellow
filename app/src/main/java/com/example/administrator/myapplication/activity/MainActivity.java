package com.example.administrator.myapplication.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.adapter.NewAdapter;
import com.example.administrator.myapplication.mode.CodeMode;
import com.example.administrator.myapplication.mode.DataMode;
import com.example.administrator.myapplication.utils.OkHttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import utils.SPUtil;


public class MainActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @ViewInject(R.id.lv)
    ListView lv;
    @ViewInject(R.id.tv_search_bg)
    TextView search_bg;
    private BGARefreshLayout mRefreshLayout;
    private int a = 1;
    CodeMode mode;
    public static String data = null;
    List<DataMode.ResultBean.DataBean> as = new ArrayList<DataMode.ResultBean.DataBean>();
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
// TODO Auto-generated method stub
            if (msg.what == 1) {
                CodeMode mode = (CodeMode) msg.obj;

                NewAdapter myAdapter = new NewAdapter(MainActivity.this, mode.getResult().getList());
                Log.e("Http",mode.getResult().getList().toString());
                lv.setAdapter(myAdapter);
                mRefreshLayout.endRefreshing();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);

        initRefreshLayout();
        loadMore(1, "鱼");

        search_bg.setTypeface(SPUtil.getTypeface(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (data == null) {

        } else {
            loadMore(0, data);
        }
    }

    private void initRefreshLayout() {
        mRefreshLayout = (BGARefreshLayout) findViewById(R.id.rl_modulename_refresh);
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(this, true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);


        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置正在加载更多时不显示加载更多控件
         mRefreshLayout.setIsShowLoadingMoreView(false);
        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("加载更多.......");
        // 设置整个加载更多控件的背景颜色资源id
        refreshViewHolder.setLoadMoreBackgroundColorRes(R.color.huise);
        // 设置整个加载更多控件的背景drawable资源id
        refreshViewHolder.setLoadMoreBackgroundDrawableRes(R.color.juhong);
        // 设置下拉刷新控件的背景颜色资源id
        refreshViewHolder.setRefreshViewBackgroundColorRes(R.color.juhong);
        // 设置下拉刷新控件的背景drawable资源id
        refreshViewHolder.setRefreshViewBackgroundDrawableRes(R.color.juhong);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
//          mRefreshLayout.setCustomHeaderView(mBanner, false);
        // 可选配置  -------------END
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this, ItemActivity.class);
//                Bundle bundle = new Bundle();
                CodeMode.ResultBean.ListBean.RecipeBean bundles=(CodeMode.ResultBean.ListBean.RecipeBean)mode.getResult().getList().get(position).getRecipe();
                Log.e("HTTPS",bundles.getMethodBeanList().size()+"");
//                bundle.putSerializable("my",bundles);
//                intent.putExtras(bundle);
//                startActivity(intent);


            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        loadMore(1, "鱼");


    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        a = a + 1;
        loadMore(a, "鱼");
        return false;
    }

    public void loadMore(int b, String name) {
        Map<String, String> params = new HashMap<>();
        params.put("key", "1c770123c1757");
        params.put("cid", "");
        params.put("name", name);
        params.put("page", b+"");
        params.put("size", "10");


        OkHttpUtil.getInstance().addRequest("http://apicloud.mob.com/v1/cook/menu/search", 1, params,
                new OkHttpUtil.HttpCallBack<CodeMode>() {

                    @Override
                    public void onSuccss(final CodeMode mode) {
                        // TODO Auto-generated method stub

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                msg.obj = mode;
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }
                        }).start();


                    }

                    @Override
                    public void onFailure(String error) {
                        // TODO Auto-generated method stub
                    }
                });
    }

    @OnClick(R.id.tv_search_bg)
    public void search(View v) {
        Intent intent = new Intent(MainActivity.this, EleSearchActivity.class);
        int location[] = new int[2];
        search_bg.getLocationOnScreen(location);
        intent.putExtra("x", location[0]);
        intent.putExtra("y", location[1]);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }




}
