package com.hr.videosplayertv.ui.activity;



import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.base.BaseFragment;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.ui.adapter.MainFragmentAdapter;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.widget.focus.FocusBorder;
import com.hr.videosplayertv.widget.single.WhatView;
import com.hr.videosplayertv.widget.view.TvViewPager;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/*
*
* */

public class MainActivity extends BaseActivity implements BaseFragment.FocusBorderHelper {

    private MainFragmentAdapter mainFragmentAdapter;
    private List<MultipleFragment> multipleFragments;

    private ListDataMenuAdapter listDataMenuAdapter;

    @BindView(R.id.tv_view_pager)
    TvViewPager tvViewPager;
    @BindView(R.id.main_menu)
    TvRecyclerView mainMenu;
    @BindView(R.id.image_log)
    ImageView imageLog;
    @BindView(R.id.tv_notification)
    TextView tvNotification;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @OnClick({R.id.btn_search,R.id.btn_personal_center})
    public void Onclick(View v){
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_search:
                intent.setClass(this, SearchActivity.class);
                startActivity(intent);

                break;
            case R.id.btn_personal_center:
                intent.setClass(this, UserCenterActivity.class);
                startActivity(intent);

                break;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        super.init();
        setListener();
        listDataMenuAdapter = new ListDataMenuAdapter(this,true);
        mainMenu.setSpacingWithMargins(10, 30);
        mainMenu.setAdapter(listDataMenuAdapter);

        mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager());
        tvViewPager.setOffscreenPageLimit(3);
        tvViewPager.setScrollerDuration(200);
        tvViewPager.setAdapter(mainFragmentAdapter);
        multipleFragments = new ArrayList<>();

        initData();
    }
    private void setListener() {

        mainMenu.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
             //   onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(MainActivity.this,3));
                if(position == tvViewPager.getCurrentItem()){

                }else {
                    tvViewPager.setCurrentItem(position);
                }
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });

        mainMenu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

              //  mFocusBorder.setVisible(hasFocus);

            }
        });

    }

    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 8; i++){
            listData.add(new ListData());
            multipleFragments.add(MultipleFragment.getmultipleFragment().setType(i));
        }
        listDataMenuAdapter.repaceDatas(listData);
        mainFragmentAdapter.upData(multipleFragments);
    }

    @Override
    public FocusBorder getFocusBorder() {
        return mFocusBorder;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        View rootview = MainActivity.this.getWindow().getDecorView();

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键
            case KeyEvent.KEYCODE_DPAD_RIGHT:

                WhatView.getInstance().whatOperation(rootview.findFocus(),true);

                break;
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:

                WhatView.getInstance().whatOperation(rootview.findFocus(),false);

                break;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        return super.onKeyDown(keyCode, event);
    }

}
