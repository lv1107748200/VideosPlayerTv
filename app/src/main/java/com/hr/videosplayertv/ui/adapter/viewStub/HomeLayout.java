package com.hr.videosplayertv.ui.adapter.viewStub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.OnChildViewHolderSelectedListener;
import android.support.v17.leanback.widget.VerticalGridView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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
import com.hr.videosplayertv.common.Iddddd;
import com.hr.videosplayertv.common.ImmobilizationData;
import com.hr.videosplayertv.event.Event;
import com.hr.videosplayertv.net.base.BaseDataResponse;
import com.hr.videosplayertv.net.base.BaseResponse;
import com.hr.videosplayertv.net.entry.request.WhatCom;
import com.hr.videosplayertv.net.entry.response.Result;
import com.hr.videosplayertv.net.entry.response.UserToken;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpException;
import com.hr.videosplayertv.ui.activity.DetailActivity;
import com.hr.videosplayertv.ui.activity.DiversityActivity;
import com.hr.videosplayertv.ui.activity.ListDataActivity;
import com.hr.videosplayertv.ui.activity.MainActivity;
import com.hr.videosplayertv.ui.adapter.SubAdapter;
import com.hr.videosplayertv.ui.adapter.viewholder.MainViewHolder;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.CheckUtil;
import com.hr.videosplayertv.utils.ColorUtils;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.FocusUtil;
import com.hr.videosplayertv.utils.Formatter;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.utils.NToast;
import com.hr.videosplayertv.utils.UrlUtils;
import com.hr.videosplayertv.widget.dialog.LoadingDialog;
import com.hr.videosplayertv.widget.presenter.CardPresenter;
import com.hr.videosplayertv.widget.presenter.HeadListRow;
import com.hr.videosplayertv.widget.presenter.NewPresenterSelector;
import com.hr.videosplayertv.widget.single.UserInfoManger;
import com.hr.videosplayertv.widget.single.WhatView;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeLayout {
    @BindView(R.id.tv_list)
    VerticalGridView tvList;
    private BaseActivity mContext;
    private BaseFragment baseFragment;
    private Map<String,List> homePagesListMap;

    ArrayObjectAdapter mRowsAdapter;
    ItemBridgeAdapter mItemBridgeAdapter;

    private SubAdapter subAdapter;

    private ImageView imageView;
    private TextView textView;

    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 3;

    public static boolean isUpOrDown = true;
    public static boolean isUp = false; //鸡肋判断
    public static boolean isCanBack = false;

    private int listHight = -1;

    public  HomeLayout(View view , BaseFragment baseFragment) {
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);

        mContext = (BaseActivity) baseFragment.getActivity();
        this.baseFragment = baseFragment;
        setListener();

        initData();
    }

    private void initData(){
        homePagesListMap = new HashMap<>();
        NewPresenterSelector newPresenterSelector = new NewPresenterSelector();
        mRowsAdapter = new ArrayObjectAdapter(); // 填入 Presenter 选择器.

        mItemBridgeAdapter = new ItemBridgeAdapter(mRowsAdapter,newPresenterSelector);

        tvList.setClipChildren(false);
        tvList.setClipToPadding(false);


      //  tvList.setVerticalSpacing(DisplayUtils.getDimen(R.dimen.x20));
       // tvList.setHorizontalSpacing(DisplayUtils.getDimen(R.dimen.x20));


        tvList.setAdapter(mItemBridgeAdapter);

    }

    private void setListener() {
        tvList.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tvList.getViewTreeObserver().removeOnPreDrawListener(this);

                //NLog.e(NLog.TAGOther,"tvlist 高度 ---》"+ tvList.getHeight());
                listHight = tvList.getHeight();

                return true;
            }
        });

        tvList.setOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() {
            @Override
            public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelected(parent, child, position, subposition);



            }
        });



        tvList.addOnScrollListener(mOnScrollListener);
    }
    public void load(){
        isMore = true;
        isLoadMore = false;
         pageNo = 3;

        setSimulationData();//模拟数据


      //  index();
      //  ComList();
    }

    private void setUpData(String key,List  whatLists){

//        if(null != homePagesListMap.get(key)){
//            homePagesListMap.put(key,null);
//        }
       // NLog.e(NLog.TAGOther,"名字 --->" + key);
        homePagesListMap.put(key,whatLists);

    }
    private void setDelegateAdapter(){

        if(!CheckUtil.isEmpty(homePagesListMap)){
            int  sizee = homePagesListMap.size();
         //   NLog.e(NLog.TAGOther," sise : "+ sizee);

            for (ImmobilizationData.HomePages c : ImmobilizationData.HomePages.values()) {
                List list = homePagesListMap.get(c.getKey());

                if(!CheckUtil.isEmpty(list)){
                    switch (c){
                        case HEAD:
                            if (true) {
                                CardPresenter cardPresenter = new CardPresenter();
                                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                                for(int i=0; i<list.size(); i++){
                                    listRowAdapter.add(list.get(i));
                                }

                                HeadListRow listRow = new HeadListRow(new HeaderItem(ImmobilizationData.HomePages.HEAD.getKey()),listRowAdapter);
                                mRowsAdapter.add(listRow);
                            }
                            break;
                        case REC:

                            if(true){
                                CardPresenter cardPresenter = new CardPresenter();
                                ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
                                for(int i=0; i<list.size(); i++){
                                    listRowAdapter.add(list.get(i));
                                }

                                ListRow listRow = new ListRow(new HeaderItem(ImmobilizationData.HomePages.REC.getKey()),listRowAdapter);
                                mRowsAdapter.add(listRow);
                            }
                            break;
                        default:
                            if(true){

                            }
                            if(true){

                            }
                            break;
                    }


                    mItemBridgeAdapter.notifyDataSetChanged();
                }

            }


        }


    }



    private void index(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken)
            return;

        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "13",
                ""+1
        );
        data.setIsindex(1);
        baseFragment.baseService.index(data, new HttpCallback<BaseResponse<BaseDataResponse<WhatList>>>() {
            @Override
            public void onError(HttpException e) {

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {

                List<WhatList> whatLists = baseDataResponseBaseResponse.getData().getInfo();

                if(!CheckUtil.isEmpty(whatLists)){

                    if(whatLists.size() >= 13){

                            setUpData(ImmobilizationData.HomePages.HEAD.getKey(),whatLists.subList(0, 8));

                            setUpData(ImmobilizationData.HomePages.REC.getKey(),whatLists.subList(6, 13));

                            setDelegateAdapter();

                    }else {
                        if(whatLists.size() >= 8){
                            setUpData(ImmobilizationData.HomePages.HEAD.getKey(),whatLists.subList(0, 8));

                            setUpData(ImmobilizationData.HomePages.REC.getKey(),whatLists.subList(0, 7));

                            setDelegateAdapter();
                        }
                    }


                }
            }
        },baseFragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }
    /**
     * 获取列表数据
     */
    private void ComList(){

     final String type = ImmobilizationData.HomePages.getKeyByIndex(pageNo - 1);

        if(null == baseFragment || type == null)
            return;

        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken)
            return;

        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0,1",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "12",
                ""+1
        );
        baseFragment.baseService.ComList(ImmobilizationData.Tags.getUrlByName(type),data, new HttpCallback<BaseResponse<BaseDataResponse<WhatList>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
                if(isLoadMore){

                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {

                if(isLoadMore){

                }
                if(pageNo < ImmobilizationData.HomePages.values().length){
                    pageNo = pageNo +1;
                }else {
                    isMore = false;
                }

                if(baseDataResponseBaseResponse.getData() != null){
                    BaseDataResponse<WhatList> whatListBaseDataResponse = baseDataResponseBaseResponse.getData();

                    List<WhatList> whatListList = whatListBaseDataResponse.getInfo();

                    if(!CheckUtil.isEmpty(whatListList)){
                        setUpData(type,whatListList);
                        setDelegateAdapter();
                    }

                }

            }
        }, baseFragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW));
    }

    private void setSimulationData(){
        List<WhatList> whatListList = new ArrayList<>();
        for(int i =0; i<12; i++){
            WhatList whatList = new WhatList();
            whatList.setTitle("... ...");
            whatList.setImgPath("");
            whatListList.add(whatList);
        }

        subAdapter = new SubAdapter(mContext,whatListList);

        if(!CheckUtil.isEmpty(whatListList)){
            if(whatListList.size() >= 8){
                setUpData(ImmobilizationData.HomePages.HEAD.getKey(),whatListList.subList(0, 12));

                setUpData(ImmobilizationData.HomePages.REC.getKey(),whatListList.subList(0, 11));

                setDelegateAdapter();
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
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(Event.HomeLayoutEvent message) {



    }

}
