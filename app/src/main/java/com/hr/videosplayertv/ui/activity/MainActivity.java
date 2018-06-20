package com.hr.videosplayertv.ui.activity;



import android.view.View;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.base.BaseFragment;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.ui.adapter.MainFragmentAdapter;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.widget.focus.FocusBorder;
import com.hr.videosplayertv.widget.view.TvViewPager;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
}
