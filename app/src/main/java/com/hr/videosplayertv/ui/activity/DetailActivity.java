package com.hr.videosplayertv.ui.activity;

import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.CommentAdapter;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.widget.tablayout.TabLayout;
import com.hr.videosplayertv.widget.tablayout.TvTabLayout;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 详情页
 */
public class DetailActivity extends BaseActivity {

    private CommentAdapter CommentAdapter;

    @BindView(R.id.tv_list)
    TvRecyclerView tvList;

    @BindView(R.id.select_collect)
    TvRecyclerView selectCollect;

    @BindView(R.id.com_layout)
    LinearLayout comLayout;

    @BindView(R.id.tab_layout)
    TvTabLayout tabLayout;

    private ListDataMenuAdapter listDataMenuAdapter;


    private BottomSheetBehavior behavior;

    @OnClick({R.id.btn_player,R.id.btn_collect})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.btn_player:
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btn_collect:
                behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void init() {
        super.init();
        setBottomSheetBehavior();
        setTab();

        setListener();
        CommentAdapter = new CommentAdapter(this);
        tvList.setSpacingWithMargins(DisplayUtils.dip2px(10), 0);
        tvList.setAdapter(CommentAdapter);

        selectCollect.setSpacingWithMargins(10, 10);
        listDataMenuAdapter = new ListDataMenuAdapter(this);
        selectCollect.setAdapter(listDataMenuAdapter);

        initData();
    }

    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 37; i++){
            listData.add(new ListData());
        }


        CommentAdapter.repaceDatas(listData);

        listData.clear();
        for (int i =0 ;i< 20; i++){
            listData.add(new ListData());
        }
        listDataMenuAdapter.repaceDatas(listData);

    }

    private void setTab(){
        tabLayout.setScaleValue(1.1f);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.addTab(
                tabLayout.newTab()
                        .setText("1-20")
//                        .setIcon(R.drawable.ic_staggered)
                , true);
        tabLayout.addTab(
                tabLayout.newTab()
                        .setText("21-40")
//                        .setIcon(R.drawable.ic_list)
        );
        tabLayout.addTab(
                tabLayout.newTab()
                        .setText("40-50")
//                        .setIcon(R.drawable.ic_grid)
        );
    }

    private void setListener() {

        selectCollect.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });

        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.01f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });

        selectCollect.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tvList.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });

        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                //                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tvList.getLayoutParams();
//                if(hasFocus){
//                    layoutParams.height =  DisplayUtils.dip2px(400);
//                }else {
//                    layoutParams.height =  DisplayUtils.dip2px(200);
//                }
//
//                tvList.setLayoutParams(layoutParams);

                if(selectCollect.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
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



    //初始化滚动
    private void setBottomSheetBehavior(){
        View bottomSheet = findViewById(R.id.com_layout);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(DisplayUtils.getScreenHeight(this)-DisplayUtils.dip2px(300)-DisplayUtils.getStatusBarHeight(this));

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                String state = "null";
                switch (newState) {
                    case 1:
                        state = "STATE_DRAGGING";
                        break;
                    case 2:
                        state = "STATE_SETTLING";
                        break;
                    case 3:
                        state = "STATE_EXPANDED";
                        break;
                    case 4:
                        state = "STATE_COLLAPSED";
                        break;
                    case 5:
                        state = "STATE_HIDDEN";
                        break;
                }
                Log.d("MainActivity", "newState:" + state);
            }
            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
//                Log.d("MainActivity", "slideOffset:" + slideOffset);
            }
        });
    }
}
