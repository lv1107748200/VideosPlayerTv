package com.hr.videosplayertv.ui.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.CommentAdapter;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.NLog;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 详情页
 */
public class DetailActivity extends BaseActivity {

    private CommentAdapter CommentAdapter;

    @BindView(R.id.tv_list)
    TvRecyclerView tvList;

    @BindView(R.id.com_layout)
    LinearLayout comLayout;
    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void init() {
        super.init();
        setListener();
        CommentAdapter = new CommentAdapter(this);
        tvList.setSpacingWithMargins(DisplayUtils.dip2px(10), 0);
        tvList.setAdapter(CommentAdapter);

        initData();
    }

    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 37; i++){
            listData.add(new ListData());
        }

        CommentAdapter.repaceDatas(listData);
    }

    private void setListener() {

        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.01f, 0);
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });
        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvList.getLayoutParams();
                if(hasFocus){
                    layoutParams.height =  DisplayUtils.dip2px(400);
                }else {
                    layoutParams.height =  DisplayUtils.dip2px(200);
                }

                tvList.setLayoutParams(layoutParams);

                mFocusBorder.setVisible(hasFocus);

               // NLog.e(NLog.TAGOther, "hasFocus--->"+hasFocus);
            }
        });

        //边界监听
//        mRecyclerView.setOnInBorderKeyEventListener(new TvRecyclerView.OnInBorderKeyEventListener() {
//            @Override
//            public boolean onInBorderKeyEvent(int direction, int keyCode, KeyEvent event) {
//                Log.i("zzzz", "onInBorderKeyEvent: ");
//                return false;//需要拦截返回true,否则返回false
//            }
//        });

        /*mRecyclerView.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                Log.i("@@@@", "onLoadMore: ");
                mRecyclerView.setLoadingMore(true); //正在加载数据
                mLayoutAdapter.appendDatas(); //加载数据
                mRecyclerView.setLoadingMore(false); //加载数据完毕
                return false; //是否还有更多数据
            }
        });*/
    }
}
