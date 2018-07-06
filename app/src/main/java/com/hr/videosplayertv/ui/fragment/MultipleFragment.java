package com.hr.videosplayertv.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.EightLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.base.BaseFragment;
import com.hr.videosplayertv.net.base.BaseDataResponse;
import com.hr.videosplayertv.net.base.BaseResponse;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.net.entry.response.WhatType;
import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpException;
import com.hr.videosplayertv.ui.activity.DetailActivity;
import com.hr.videosplayertv.ui.activity.ListDataActivity;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.adapter.HomeAdapter;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.ui.adapter.MetroAdapter;
import com.hr.videosplayertv.ui.adapter.SubAdapter;
import com.hr.videosplayertv.ui.adapter.viewStub.ClassifyLayout;
import com.hr.videosplayertv.ui.adapter.viewStub.CommonLayout;
import com.hr.videosplayertv.ui.adapter.viewStub.HomeLayout;
import com.hr.videosplayertv.ui.adapter.viewholder.MainViewHolder;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.widget.dialog.LoadingDialog;
import com.hr.videosplayertv.widget.focus.FocusBorder;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.VirtualLayoutManager.LayoutParams;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.hr.videosplayertv.common.ImmobilizationData.ANIME;
import static com.hr.videosplayertv.common.ImmobilizationData.FILM;
import static com.hr.videosplayertv.common.ImmobilizationData.OVERSEAS;
import static com.hr.videosplayertv.common.ImmobilizationData.SPORTS;
import static com.hr.videosplayertv.common.ImmobilizationData.TELEPLAY;
import static com.hr.videosplayertv.common.ImmobilizationData.VARIETY;

/**
 * 通用版 根据数据不同来区分页面
 */
public class MultipleFragment extends BaseFragment {

    private  int type ;
    private  ListData typeData;

    private HomeLayout homeLayout;
    private ClassifyLayout classifyLayout;
    private CommonLayout commonLayout;

    public MultipleFragment setType(ListData typeData) {
        this.type = typeData.getType();
        this.typeData = typeData;
        return this;
    }

    public static MultipleFragment getmultipleFragment(){
        return new MultipleFragment();
    }


    @BindView(R.id.view_stub)
    ViewStub viewStub;

    @Override
    public int getLayout() {
        return R.layout.fragment_multiple;
    }

    @Override
    public void init() {
        super.init();

        switch (type){

            case 0:
                viewStub.setLayoutResource(R.layout.item_multiple_home);
                homeLayout = new HomeLayout(viewStub.inflate(),mContext);
                break;
            case 1:
                viewStub.setLayoutResource(R.layout.item_multiple_classify);
                classifyLayout =  new ClassifyLayout(viewStub.inflate(),mContext);
                break;
            default:
                viewStub.setLayoutResource(R.layout.item_multiple_common);
                commonLayout = new CommonLayout(viewStub.inflate(),mContext);
                break;
        }

    }

    @Override
    public void loadData() {
        super.loadData();

      //  LoadingDialog.showProgress(mContext);
        switch (type){

            case 0:

                break;
            case 1:

                break;
            default:
                commonLayout.setType(typeData.getTitle());
                switch (typeData.getTitle()){
                    case FILM:
                        FilmType();
                        break;
                    case TELEPLAY:
                        TVType();
                        break;
                    case VARIETY:
                        VarietyType();
                        break;
                    case ANIME:
                        AnimeType();
                        break;
                    case SPORTS:
                        SportType();
                        break;
                    case OVERSEAS:
                        CircleType();
                        break;
                }
                break;
        }


    }

    @Override
    public void stopLoad() {
        super.stopLoad();
    }

    //首页布局


    //分类试图


    //common
    private void FilmType(){
        baseService.FilmType(new HttpCallback<BaseResponse<BaseDataResponse<WhatType>>>() {
            @Override
            public void onError(HttpException e) {

                if(e.getCode() == 1){
                    LoadingDialog.showText(mContext,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatType>> baseDataResponseBaseResponse) {
                LoadingDialog.disMiss();

                BaseDataResponse<WhatType> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<WhatType> whatTypeList = baseDataResponse.getInfo();
                commonLayout.setListDataMenuAdapter(whatTypeList);
            }
        },MultipleFragment.this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
    private void TVType(){
        baseService.TVType(new HttpCallback<BaseResponse<BaseDataResponse<WhatType>>>() {
            @Override
            public void onError(HttpException e) {

                if(e.getCode() == 1){
                    LoadingDialog.showText(mContext,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatType>> baseDataResponseBaseResponse) {
                LoadingDialog.disMiss();

                BaseDataResponse<WhatType> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<WhatType> whatTypeList = baseDataResponse.getInfo();
                commonLayout.setListDataMenuAdapter(whatTypeList);
            }
        },MultipleFragment.this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
    //综艺
    private void VarietyType(){
        baseService.VarietyType(new HttpCallback<BaseResponse<BaseDataResponse<WhatType>>>() {
            @Override
            public void onError(HttpException e) {

                if(e.getCode() == 1){
                    LoadingDialog.showText(mContext,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatType>> baseDataResponseBaseResponse) {
                LoadingDialog.disMiss();

                BaseDataResponse<WhatType> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<WhatType> whatTypeList = baseDataResponse.getInfo();
                commonLayout.setListDataMenuAdapter(whatTypeList);
            }
        },MultipleFragment.this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
    private void AnimeType(){
        baseService.AnimeType(new HttpCallback<BaseResponse<BaseDataResponse<WhatType>>>() {
            @Override
            public void onError(HttpException e) {

                if(e.getCode() == 1){
                    LoadingDialog.showText(mContext,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatType>> baseDataResponseBaseResponse) {
                LoadingDialog.disMiss();

                BaseDataResponse<WhatType> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<WhatType> whatTypeList = baseDataResponse.getInfo();
                commonLayout.setListDataMenuAdapter(whatTypeList);
            }
        },MultipleFragment.this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
    private void SportType(){
        baseService.SportType(new HttpCallback<BaseResponse<BaseDataResponse<WhatType>>>() {
            @Override
            public void onError(HttpException e) {

                if(e.getCode() == 1){
                    LoadingDialog.showText(mContext,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatType>> baseDataResponseBaseResponse) {
                LoadingDialog.disMiss();

                BaseDataResponse<WhatType> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<WhatType> whatTypeList = baseDataResponse.getInfo();
                commonLayout.setListDataMenuAdapter(whatTypeList);
            }
        },MultipleFragment.this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
    private void CircleType(){
        baseService.CircleType(new HttpCallback<BaseResponse<BaseDataResponse<WhatType>>>() {
            @Override
            public void onError(HttpException e) {

                if(e.getCode() == 1){
                    LoadingDialog.showText(mContext,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatType>> baseDataResponseBaseResponse) {
                LoadingDialog.disMiss();

                BaseDataResponse<WhatType> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<WhatType> whatTypeList = baseDataResponse.getInfo();
                commonLayout.setListDataMenuAdapter(whatTypeList);
            }
        },MultipleFragment.this.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
}
