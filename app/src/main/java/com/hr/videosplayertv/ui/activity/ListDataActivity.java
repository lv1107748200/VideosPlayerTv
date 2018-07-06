package com.hr.videosplayertv.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.net.base.BaseDataResponse;
import com.hr.videosplayertv.net.base.BaseResponse;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.net.entry.request.WhatCom;
import com.hr.videosplayertv.net.entry.response.UserToken;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.net.entry.response.WhatType;
import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpException;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.utils.CheckUtil;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.widget.dialog.LoadingDialog;
import com.hr.videosplayertv.widget.single.UserInfoManger;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.hr.videosplayertv.common.ImmobilizationData.ANIME;
import static com.hr.videosplayertv.common.ImmobilizationData.FILM;
import static com.hr.videosplayertv.common.ImmobilizationData.OVERSEAS;
import static com.hr.videosplayertv.common.ImmobilizationData.SPORTS;
import static com.hr.videosplayertv.common.ImmobilizationData.TELEPLAY;
import static com.hr.videosplayertv.common.ImmobilizationData.VARIETY;


/**
 * 内容列表页
 */
public class ListDataActivity extends BaseActivity {

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;
    @BindView(R.id.list_menu)
    TvRecyclerView listMenu;

    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 1;

    private GridAdapter gridAdapter;
    private ListDataMenuAdapter listDataMenuAdapter;

    private ArrayList<WhatType> whatTypeList;
    private String type;

    @Override
    public int getLayout() {
        return R.layout.activity_list_data;
    }

    @Override
    public void init() {
        super.init();

        setListener();
        tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));
        gridAdapter = new GridAdapter(this);
        tvList.setAdapter(gridAdapter);

        listMenu.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x10), 0);
        listDataMenuAdapter = new ListDataMenuAdapter(this,ListDataMenuAdapter.THREE,true);
        listMenu.setAdapter(listDataMenuAdapter);
        listMenu.setmSelectedPosition(1);


        initData();
    }

    private void initData(){

        Intent intent = getIntent();

        whatTypeList = intent.getParcelableArrayListExtra("WHATTYPELIST");
        type = intent.getStringExtra("TYPE");
        if(!CheckUtil.isEmpty(whatTypeList)){
            listDataMenuAdapter.repaceDatas(whatTypeList);
        }

        tvTitleChild.setText(type+getString(R.string.svp_list));

        load();
    }

    private void setListener() {

        listMenu.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.0f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                if(position == 0){
                 Intent intent = new Intent();
                intent.setClass(ListDataActivity.this, SearchActivity.class);
                startActivity(intent);
                }

            }
        });

        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });

        listMenu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                if(listMenu.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });



        tvList.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {

                tvList.setLoadingMore(true); //正在加载数据
                isLoadMore = true;
                load();
                return isMore; //是否还有更多数据
            }
        });

    }

    private void load(){
        if(null != type){
            switch (type){
                case FILM:
                    Film();
                    break;
                case TELEPLAY:
                    TV();
                    break;
                case VARIETY:
                    Film();
                    break;
                case ANIME:
                    Film();
                    break;
                case SPORTS:
                    Film();
                    break;
                case OVERSEAS:
                    Film();
                    break;
            }
        }
    }

    private void Film(){
         UserToken userToken = UserInfoManger.getInstance().getUserToken();
        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0,1",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "20",
                ""+pageNo
                );
        baseService.Film(data, new HttpCallback<BaseResponse<BaseDataResponse<WhatList>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    LoadingDialog.showText(ListDataActivity.this,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {

                setTvList(baseDataResponseBaseResponse);
            }
        }, ListDataActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    private void TV(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0,1",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "20",
                ""+pageNo
        );
        baseService.TV(data, new HttpCallback<BaseResponse<BaseDataResponse<WhatList>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    LoadingDialog.showText(ListDataActivity.this,e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {
                setTvList(baseDataResponseBaseResponse);

            }
        }, ListDataActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    private void setTvList(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse){
        if(isLoadMore){
            tvList.setLoadingMore(false);
        }
        BaseDataResponse<WhatList> baseDataResponse = baseDataResponseBaseResponse.getData();
        List<WhatList> whatLists = baseDataResponse.getInfo();

        if(!CheckUtil.isEmpty(whatLists)){

            pageNo = pageNo+1;
            if(isLoadMore){
                gridAdapter.appendDatas(whatLists);
            }else {
                gridAdapter.repaceDatas(whatLists);
            }


        }else {
            if(isLoadMore){
                isMore = false;

            }else {
                gridAdapter.clearDatas();
                gridAdapter.notifyDataSetChanged();

            }

        }
    }

}
