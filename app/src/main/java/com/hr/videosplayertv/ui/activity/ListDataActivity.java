package com.hr.videosplayertv.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.common.Iddddd;
import com.hr.videosplayertv.common.ImmobilizationData;
import com.hr.videosplayertv.db.DBResultCallback;
import com.hr.videosplayertv.db.RealmDBManger;
import com.hr.videosplayertv.db.TabsData;
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
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.CheckUtil;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.utils.NToast;
import com.hr.videosplayertv.widget.dialog.LoadingDialog;
import com.hr.videosplayertv.widget.layout.AddLineLayout;
import com.hr.videosplayertv.widget.single.UserInfoManger;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmList;
import io.realm.RealmResults;

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
    @BindView(R.id.addLayout)
    AddLineLayout addLayout;

    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 1;

    private GridAdapter gridAdapter;
    private ListDataMenuAdapter listDataMenuAdapter;

    private List<WhatType> whatTypeList;
    private String type;

    private String CID;

    private String pid = null;

    private int select = -1;

    private boolean isCanBack = false;

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

        initData();
    }

    private void initData(){

        Intent intent = getIntent();
        type = intent.getStringExtra("TYPE");
        select = intent.getIntExtra("SELECTddfdf",-1);

     //   NLog.e(NLog.TAGOther,"SELECT 选中的条幅 ---> " + select);

        if(!CheckUtil.isEmpty(type)){
            huoqv();
        }
        tvTitleChild.setText(type+getString(R.string.svp_list));

      //  load("0,1");
    }

    private void huoqv(){
        RealmDBManger.getTabsData(TabsData.class,"tab",type, new DBResultCallback<RealmResults<TabsData>>() {
            @Override
            public void onSuccess(RealmResults<TabsData> realmResults) {
               // NLog.e(NLog.DB,"数据库 查询成功------------");
                if(!CheckUtil.isEmpty(realmResults)){
//                    NLog.e(NLog.DB,"数据库"+commonLayout.getType()+" --->"+realmResults.size());
//                    NLog.e(NLog.DB,"数据库"+commonLayout.getType()+" --->"+realmResults.get(0).getRealmList().size());
                    if(!CheckUtil.isEmpty(realmResults.get(0).getRealmList())){
                        whatTypeList = new ArrayList<>();
                        whatTypeList.add(new WhatType());
                        whatTypeList.addAll(realmResults.get(0).getRealmList());
                        if(!CheckUtil.isEmpty(whatTypeList)){

                            pid = whatTypeList.get(1).getPID();

                            listDataMenuAdapter.repaceDatas(whatTypeList);

                            if(-1 != select){
                                listMenu.setSelection(select + 1);
                            }else {
                                listMenu.setSelection(1);
                            }

                        }else {
                            ComType();
                        }
                    }else {
                        ComType();
                    }
                }else {
                    ComType();
                }
            }
            @Override
            public void onError(String errString) {
               // NLog.e(NLog.DB,"数据库 查询失败------------");
               // NLog.e(NLog.DB,"数据库"+type+" --->"+errString);
            }
        });
    }
    private void baocun(List<WhatType> whatTypeList){
        TabsData tabsData = new TabsData();
        tabsData.setTab(type);

        if(!CheckUtil.isEmpty(whatTypeList)){
            RealmList<WhatType> whatTypeRealmList = new RealmList<>();
            whatTypeRealmList.addAll(whatTypeList);
            tabsData.setRealmList(whatTypeRealmList);
        }
        //NLog.e(NLog.DB,"数据库"+type+" 保存--->");
        RealmDBManger.copyToRealmOrUpdate(tabsData, new DBResultCallback() {
            @Override
            public void onSuccess(Object o) {
               // NLog.e(NLog.DB,"数据库 保存成功--->");
            }

            @Override
            public void onError(String errString) {
             //   NLog.e(NLog.DB,"数据库 保存失败--->"+errString);
            }
        });
    }
    private void setListener() {

        listMenu.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                setShowOrDiss(false);
                onMoveFocusBorder(itemView, 1.0f, DisplayUtils.dip2px(3));

                if(position > 0){

                    if(listDataMenuAdapter.getItem(position) instanceof WhatType){

                        isMore = true;
                        isLoadMore = false;
                        pageNo = 1;
                        tvList.setHasMoreData(true);

                        if(((WhatType) listDataMenuAdapter.getItem(position)).getPath().equals(CID)){
                            return;
                        }
                        lifecycleSubject.onNext(ActivityEvent.STOP);

                        lifecycleSubject.onNext(ActivityEvent.START);

                        load(((WhatType) listDataMenuAdapter.getItem(position)).getPath());
                    }

                }
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                if(position == 0){
                 Intent intent = new Intent();
                 intent.setClass(ListDataActivity.this, SearchActivity.class);
                 intent.putExtra("TYPE",type);
                 intent.putExtra("PID",pid);
                 startActivity(intent);
                }

            }
        });

        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                setShowOrDiss(true);
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                Object o =  gridAdapter.getItem(position);

                if(o instanceof WhatList){
                    Intent intent = new Intent(ListDataActivity.this,DetailActivity.class);
                    intent.putExtra("Iddddd",new Iddddd(((WhatList) o).getID(),((WhatList) o).getContxt()));
                    startActivity(intent);
                }

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
                load(CID);
                return isMore; //是否还有更多数据
            }
        });

        tvList.addOnScrollListener(mOnScrollListener);

    }

    private void load(String s){
          CID = s;
          addLayout.showClick();
          ComList();
    }
    /**
     * 获取类型
     */
    private void ComType(){
        if(null == type)
            return;
        baseService.ComType(ImmobilizationData.Tags.getTypeUrlByName(type),new HttpCallback<BaseResponse<BaseDataResponse<WhatType>>>() {
            @Override
            public void onError(HttpException e) {

                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                }


            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatType>> baseDataResponseBaseResponse) {

                BaseDataResponse<WhatType> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<WhatType> whatTypeList = baseDataResponse.getInfo();
                baocun(whatTypeList);
                whatTypeList.add(new WhatType());
                listDataMenuAdapter.repaceDatas(whatTypeList);
                if(!CheckUtil.isEmpty(whatTypeList)){
                    pid = whatTypeList.get(1).getPID();
                    if(-1 != select){
                        listMenu.setSelection(select + 1);
                    }else {
                        listMenu.setSelection(1);
                    }
                }

            }
        },ListDataActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    private void ComList(){
         UserToken userToken = UserInfoManger.getInstance().getUserToken();
         if(null == userToken){
             return;
         }
        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                CID,
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "20",
                ""+pageNo,
                ""
                );

        baseService.ComList( ImmobilizationData.Tags.getUrlByName(type),data, new HttpCallback<BaseResponse<BaseDataResponse<WhatList>>>() {
            @Override
            public void onError(HttpException e) {

                addLayout.hideClick();

                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                }
                if(isLoadMore){
                    tvList.setLoadingMore(false);
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {
                addLayout.hideClick();
                setTvList(baseDataResponseBaseResponse);

            }
        }, ListDataActivity.this.bindUntilEvent(ActivityEvent.STOP));
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
                addLayout.hideAndShowMessage(getString(R.string.svp_null_data));
            }

        }
    }


    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
          //是否滑到顶部
            if(!recyclerView.canScrollVertically(-1)){
                isCanBack = false;
            }
        }

        @Override
        public void onScrolled(RecyclerView rv, int i, int i2) {

            if(i2 > 0){
                isCanBack = true;
            }

        }

    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:

                if(isCanBack){
                    NToast.shortToastBaseApp("返回顶部");
                    tvList.setSelection(0);
                    isCanBack = false;
                    return true;
                }

                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
