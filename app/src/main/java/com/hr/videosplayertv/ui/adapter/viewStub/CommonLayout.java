package com.hr.videosplayertv.ui.adapter.viewStub;

import android.content.Intent;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.net.entry.response.WhatType;
import com.hr.videosplayertv.ui.activity.DetailActivity;
import com.hr.videosplayertv.ui.activity.ListDataActivity;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.CheckUtil;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommonLayout {

    private ListDataMenuAdapter listDataMenuAdapter;
    @BindView(R.id.common_menu)
    TvRecyclerView mainMenu;

    @BindView(R.id.image_one)
    ImageView image_one;
    @BindView(R.id.image_two)
    ImageView image_two;
    @BindView(R.id.image_three)
    ImageView image_three;
    @BindView(R.id.image_four)
    ImageView image_four;
    @BindView(R.id.image_five)
    ImageView image_five;

    private BaseActivity mContext;

    private String type;

    @OnClick({R.id.image_one,R.id.image_two,R.id.image_three,R.id.image_four,R.id.image_five})
    public void Onclick(View view){
        Intent intent = new Intent(mContext,DetailActivity.class);
        switch (view.getId()){
            case R.id.image_one:
                break;
            case R.id.image_two:
                break;
            case R.id.image_three:
                break;
            case R.id.image_four:
                break;
            case R.id.image_five:
                break;

        }
        mContext. startActivity(intent);
    }

    public CommonLayout(View view,BaseActivity context) {
        ButterKnife.bind(this,view);
        mContext = context;
        GlideUtil.setGlideImage(mContext, ImgDatasUtils.getUrl(),image_one);
        GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_two);
        GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_three);
        GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_four);
        GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_five);

        setListener();
        listDataMenuAdapter = new ListDataMenuAdapter(mContext,ListDataMenuAdapter.TWO);
        mainMenu.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x10), DisplayUtils.getDimen(R.dimen.x40));
        mainMenu.setAdapter(listDataMenuAdapter);

    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setListDataMenuAdapter(List<WhatType> whatTypeList){
        if(!CheckUtil.isEmpty(whatTypeList)){
            if(whatTypeList.size()>=5){
                listDataMenuAdapter.repaceDatas(whatTypeList.subList(0, 5));
            }else {
                listDataMenuAdapter.repaceDatas(whatTypeList);
            }
        }
    }

    private void setListener() {

        mainMenu.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                mContext. onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));

            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Intent intent = new Intent(mContext, ListDataActivity.class);
                intent.putExtra("TYPE",type);
                mContext.startActivity(intent);
            }
        });

        mainMenu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                //  mFocusBorder.setVisible(hasFocus);

            }
        });

    }
}
