package com.hr.videosplayertv.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.widget.focus.FocusBorder;
import com.hr.videosplayertv.widget.single.WhatView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户中心
 */
public class UserCenterActivity extends BaseActivity {

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_user_say_more)
    TextView tv_user_say_more;

    @OnClick({R.id.layout_played_videos,R.id.layout_favorite,R.id.layout_recharge_center,
            R.id.layout_help_center,R.id.layout_about_men})
    public void Onclick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.layout_played_videos:
                intent.setClass(this,DiversityActivity.class);
                intent.putExtra("DiversityType",DiversityActivity.PLAYERRECORD);
                startActivity(intent);
                break;
            case R.id.layout_favorite:
                intent.setClass(this,DiversityActivity.class);
                intent.putExtra("DiversityType",DiversityActivity.FAVORITE);
                startActivity(intent);
                break;
            case R.id.layout_recharge_center:

                break;
            case R.id.layout_help_center:

                break;
            case R.id.layout_about_men:

                break;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public void init() {
        super.init();

        tvTitleChild.setText(getString(R.string.svp_personal_center));

        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0); //返回null表示不使用焦点框框架
            }
        });

    }

}
