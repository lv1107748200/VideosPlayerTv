package com.hr.videosplayertv.ui.adapter.viewStub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
    TvRecyclerView tvList;
    private DelegateAdapter delegateAdapter;
    private BaseActivity mContext;
    private BaseFragment baseFragment;
    private Map<String,List> homePagesListMap;
    private VirtualLayoutManager layoutManager;

   // List<DelegateAdapter.Adapter> adapters;

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

        layoutManager = new VirtualLayoutManager(mContext);
        tvList.setLayoutManager(layoutManager);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        tvList.setAdapter(delegateAdapter);
        initData();
    }

    private void initData(){
        homePagesListMap = new HashMap<>();
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


        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

               // NLog.e(NLog.TAGOther,"首页选中序号--->" + position);

                if(position == 6){
                    isUp = true;
                }else {
                    isUp = false;
                }

                if( 0 <= position && position<6){
                    List list = homePagesListMap.get(ImmobilizationData.HomePages.HEAD.getKey());
                    if(!CheckUtil.isEmpty(list)){
                        WhatList whatList = (WhatList) list.get(position);

                        if(null != imageView){
                            GlideUtil.setGlideImage(mContext
                                    , UrlUtils.getUrl(whatList.getImgPath())
                                    ,imageView,R.drawable.hehe);
                            textView.setText(whatList.getTitle());
                        }
                    }
                }


                mContext.setShowOrDiss(true);
//                if(position < 8){
//                    mContext.onMoveFocusBorder(itemView, 1.05f, DisplayUtils.dip2px(3));
//                }else {
                    if(itemView.getId() == R.id.title_layout){
                        mContext.onMoveFocusBorder(itemView, 1.0f, 0);

                       // NLog.e(NLog.TAGOther," HomeLayout title 上下滚动 ---> " + isUpOrDown);
                        int offset = 0;
                        if(-1 != listHight){
                            offset = listHight/2;
                        }
                        if(isUpOrDown){
                            tvList.setSelection(position + 1);
                            //layoutManager.scrollToPositionWithOffset(position + 1,0);
                        }else {
                            tvList.setSelection(position - 1);
                            //layoutManager.scrollToPositionWithOffset(position - 1,0);
                        }

                    }else {
                       // NLog.e(NLog.TAGOther," itemView 的宽度 ---> " + itemView.getWidth());

                        mContext.onMoveFocusBorder(itemView,
                                Formatter.getScale(itemView.getWidth(),itemView.getHeight(),DisplayUtils.getDimen(R.dimen.x15)),
                                Formatter.getRoundRadius());

                    }
               // }


            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Intent intent = new Intent(mContext,ListDataActivity.class);

                mContext.startActivity(intent);
            }
        });


        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mContext.mFocusBorder.setVisible(hasFocus);
            }
        });

        tvList.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {

            //    NLog.e(NLog.TAGOther," HomeLayout 加载更多 ---> ");

                tvList.setLoadingMore(true); //正在加载数据
                isLoadMore = true;
                ComList();
                return isMore; //是否还有更多数据
            }
        });
        tvList.addOnScrollListener(mOnScrollListener);
    }
    public void load(){
        isMore = true;
        isLoadMore = false;
         pageNo = 3;
        tvList.setLoadingMore(false);
        tvList.setHasMoreData(true);

        setSimulationData();//模拟数据


        index();
        ComList();
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

            List<DelegateAdapter.Adapter> adapters = new ArrayList<>();

            for (ImmobilizationData.HomePages c : ImmobilizationData.HomePages.values()) {
                List list = homePagesListMap.get(c.getKey());

                if(!CheckUtil.isEmpty(list)){
                    switch (c){
                        case HEAD:
                            if (true) {
                                EightLayoutHelper helper = new EightLayoutHelper(DisplayUtils.getDimen(R.dimen.x20));
                                helper.setColWeights(new float[]{50f,20f,20f,20f,20f,20f,30f,30f});
                                VirtualLayoutManager.LayoutParams lp = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x550));
                                adapters.add(new SubAdapter(mContext, helper, 8, c.getKey(),list,lp){
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADONE;
                                    }
                                });
                            }
                            break;
                        case REC:
                            if(true){
                                VirtualLayoutManager.LayoutParams lp = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x60));
                                adapters.add(new SubAdapter(mContext, new LinearLayoutHelper(), 1,c.getKey(),null,lp) {
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADTHREE;
                                    }
                                });

                            }
                            if(true){

//                                int wight = DisplayUtils.getWide(7,DisplayUtils.getDimen(R.dimen.x20)
//                                        ,DisplayUtils.getDimen(R.dimen.x40))/3;

                                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x180));
                                 GridLayoutHelper helper = new GridLayoutHelper(7, list.size(),DisplayUtils.getDimen(R.dimen.x20));
                                adapters.add(new SubAdapter(mContext, helper, 7,c.getKey(),list,layoutParams) {
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADTWO;
                                    }

                                });
                            }
                            break;
                        default:
                            if(true){
                                VirtualLayoutManager.LayoutParams lp = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x60));
                                adapters.add(new SubAdapter(mContext, new LinearLayoutHelper(), 1,c.getKey(),null,lp) {
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADTHREE;
                                    }
                                });

                            }
                            if(true){

                                int wight = DisplayUtils.getWide(6,DisplayUtils.getDimen(R.dimen.x20)
                                        ,DisplayUtils.getDimen(R.dimen.x40))/3;

                                VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, wight*4);
                                GridLayoutHelper helper = new GridLayoutHelper(6, list.size(),DisplayUtils.getDimen(R.dimen.x20));
                                adapters.add(new SubAdapter(mContext, helper, 7,c.getKey(),list,layoutParams) {
                                    @Override
                                    public int getItemViewType(int position) {
                                        return GRADTWO;
                                    }

                                });
                            }
                            break;
                    }
                }
            }
            delegateAdapter.setAdapters(adapters);

            if( sizee == 2 || sizee == 3 )
            delegateAdapter.notifyDataSetChanged();
        }


    }

  class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

        public final static int GRADONE = 987;
        public final static int GRADTWO = 789;
        public final static int GRADTHREE = 787;


        private Context contextontext;

        private LayoutHelper mLayoutHelper;


        private VirtualLayoutManager.LayoutParams mLayoutParams;
        private int mCount = 0;
        private String type;

        private List list;

        public String getType() {
            return type;
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, "",null,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count,String type, List list,@NonNull VirtualLayoutManager.LayoutParams layoutParams) {
            this.contextontext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
            this.type = type;
            this.list = list;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == GRADONE){
                return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid, parent, false));

            }else if(viewType == GRADTWO){
                return new MainViewHolder(
                        LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid_stragg, parent, false));

            }else if(viewType == GRADTHREE){
                return new MainViewHolder(
                        LayoutInflater.from(contextontext).inflate(R.layout.item_home_title, parent, false));
            }
            return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid, parent, false));
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new VirtualLayoutManager.LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {

        if( GRADONE ==  getItemViewType(position)){
          final   Object o = list.get(position);
            if(o instanceof WhatList){

                ImageView imageView = holder.itemView.findViewById(R.id.image_grid);
                TextView textView = holder.itemView.findViewById(R.id.title_grid);

                if(position == 0){
                    HomeLayout.this.imageView = imageView;
                    HomeLayout.this.textView = textView;
                }

             final    Intent intent = new Intent();

                if(position == 6){

                    textView.setText("收藏夹");
                    textView.setVisibility(View.VISIBLE);

                    intent.setClass(contextontext,DiversityActivity.class);
                    intent.putExtra("DiversityType",DiversityActivity.FAVORITE);

                    GlideUtil.setGlideImage(contextontext
                            , ""
                            ,imageView,R.drawable.foc_back_image);

                }else if(position == 7){

                    textView.setText("播放记录");
                    textView.setVisibility(View.VISIBLE);

                    intent.setClass(contextontext,DiversityActivity.class);
                    intent.putExtra("DiversityType",DiversityActivity.PLAYERRECORD);

                    GlideUtil.setGlideImage(contextontext
                            , ""
                            ,imageView,R.drawable.foc_image_three);

                }else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(((WhatList) o).getTitle());

                    GlideUtil.setGlideImage(contextontext
                            , UrlUtils.getUrl(((WhatList) o).getImgPath())
                            ,imageView,R.drawable.hehe);

                    intent.setClass(contextontext,DetailActivity.class);
                    intent.putExtra("Iddddd",new Iddddd(((WhatList) o).getID(),((WhatList) o).getContxt()));

                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        contextontext.startActivity(intent);
                    }
                });

            }
        }else if( GRADTWO == getItemViewType(position)){
          final   Object o = list.get(position);
            if(o instanceof WhatList){
                ImageView imageView = holder.itemView.findViewById(R.id.image_grid_stragg);
                TextView textView = holder.itemView.findViewById(R.id.title_grid_stragg);

                textView.setText(((WhatList) o).getTitle());
                GlideUtil.setGlideImage(contextontext
                        , UrlUtils.getUrl(((WhatList) o).getImgPath())
                        ,imageView,R.drawable.hehe);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(contextontext,DetailActivity.class);
                        intent.putExtra("Iddddd",new Iddddd(((WhatList) o).getID(),((WhatList) o).getContxt()));
                        contextontext.startActivity(intent);
                    }
                });

            }
        }else if(GRADTHREE == getItemViewType(position)){

            TextView textView = holder.itemView.findViewById(R.id.title);
            textView.setText(getType());
        }else {

        }

        }

        @Override
        public int getItemCount() {
            if(!CheckUtil.isEmpty(list)){
                return list.size();
            }
            return mCount;
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
                    tvList.setLoadingMore(false);
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<WhatList>> baseDataResponseBaseResponse) {

                if(isLoadMore){
                    tvList.setLoadingMore(false);
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
        for(int i =0; i<8; i++){
            WhatList whatList = new WhatList();
            whatList.setTitle("... ...");
            whatList.setImgPath("");
            whatListList.add(whatList);
        }
        if(!CheckUtil.isEmpty(whatListList)){
            if(whatListList.size() >= 8){
                setUpData(ImmobilizationData.HomePages.HEAD.getKey(),whatListList.subList(0, 8));

                setUpData(ImmobilizationData.HomePages.REC.getKey(),whatListList.subList(0, 7));

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

        tvList.setSelection(0);

    }

}
