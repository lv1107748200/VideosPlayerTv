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
 * 个人中心
 */
public class UserCenterActivity extends BaseActivity {

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;

    @OnClick({R.id.image_one,R.id.image_two,R.id.image_three})
    public void Onclick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.image_one:
                intent.setClass(this,DiversityActivity.class);
                intent.putExtra("DiversityType",DiversityActivity.PLAYERRECORD);
                startActivity(intent);
                break;
            case R.id.image_two:
                intent.setClass(this,DiversityActivity.class);
                intent.putExtra("DiversityType",DiversityActivity.FAVORITE);
                startActivity(intent);
                break;
            case R.id.image_three:

                break;
            case R.id.image_four:

                break;
            case R.id.image_five:

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
