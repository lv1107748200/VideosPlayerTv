package com.hr.videosplayertv.ui.activity;

import android.content.Intent;
import android.util.DisplayMetrics;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.utils.NLog;



/**
 * Created by 吕 on 2018/3/13.
 */

public class SplashActiity extends BaseActivity {

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        super.init();

        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void getV(){
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
// 屏幕宽度（像素）
        int width = metric.widthPixels;
// 屏幕高度（像素）
        int height = metric.heightPixels;
// 屏幕密度（1.0 / 1.5 / 2.0）
        float density = metric.density;
// 屏幕密度DPI（160 / 240 / 320）
        int densityDpi = metric.densityDpi;
        String info = "机顶盒型号: " + android.os.Build.MODEL
                + ",\nSDK版本:" + android.os.Build.VERSION.SDK
                + ",\n系统版本:" + android.os.Build.VERSION.RELEASE
                + "\n屏幕宽度（像素）: "+width
                + "\n屏幕高度（像素）: " + height
                + "\n屏幕密度 : "+density
                +"\n屏幕密度DPI: "+densityDpi;
        NLog.e(NLog.TAGOther, info);
    }
}
