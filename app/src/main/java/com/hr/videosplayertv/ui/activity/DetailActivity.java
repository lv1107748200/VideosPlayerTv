package com.hr.videosplayertv.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.common.Iddddd;
import com.hr.videosplayertv.net.base.BaseDataResponse;
import com.hr.videosplayertv.net.base.BaseResponse;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.net.entry.request.WhatCom;
import com.hr.videosplayertv.net.entry.response.CommHot;
import com.hr.videosplayertv.net.entry.response.Comment;
import com.hr.videosplayertv.net.entry.response.Detail;
import com.hr.videosplayertv.net.entry.response.GuestSeries;
import com.hr.videosplayertv.net.entry.response.UserToken;
import com.hr.videosplayertv.net.entry.response.VL;
import com.hr.videosplayertv.net.entry.response.VideoDisLike;
import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpException;
import com.hr.videosplayertv.ui.adapter.CommentAdapter;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.utils.CheckUtil;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.utils.NToast;
import com.hr.videosplayertv.utils.SpanUtils;
import com.hr.videosplayertv.utils.UrlUtils;
import com.hr.videosplayertv.widget.dialog.LoadingDialog;
import com.hr.videosplayertv.widget.focus.FocusBorder;
import com.hr.videosplayertv.widget.single.CollectManger;
import com.hr.videosplayertv.widget.single.UserInfoManger;
import com.hr.videosplayertv.widget.tablayout.TabLayout;
import com.hr.videosplayertv.widget.tablayout.TvTabLayout;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter.FOUR;
import static com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter.THREE;

/**
 * 详情页
 */
public class DetailActivity extends BaseActivity {


    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;

    @BindView(R.id.select_collect)
    TvRecyclerView selectCollect;

    @BindView(R.id.com_layout)
    LinearLayout comLayout;
    @BindView(R.id.layout_select)
    RelativeLayout layoutSelect;

    @BindView(R.id.tab_layout)
    TvTabLayout tabLayout;

    @BindView(R.id.image_poster)
    ImageView imagePoster;

    @BindView(R.id.tv_video_introduction)
    TextView tv_video_introduction;
    @BindView(R.id.tv_data)
    TextView tv_data;

    private ListDataMenuAdapter listDataMenuAdapter;
    private CommentAdapter CommentAdapter;

    private BottomSheetBehavior behavior;

    private Iddddd iddddd;//id 数据

    private boolean isSild = false;//判断是否已滑动


    @OnClick({R.id.btn_player,R.id.btn_collect,R.id.btn_like,R.id.btn_stamp,R.id.image_poster})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.btn_player:
                if(true){
                    Intent intent = new Intent();
                    intent.setClass(this,PlayerActivity.class);
                    if(listDataMenuAdapter.getSelectData() != null){
                        intent.putExtra("GUESTSERIES",listDataMenuAdapter.getSelectData());
                    } else if(!CheckUtil.isEmpty(CollectManger.getInstance().getGuestSeriesList())){
                        intent.putExtra("GUESTSERIES",CollectManger.getInstance().getGuestSeriesList().get(0));
                    }
                    startActivity(intent);
                }
                break;
            case R.id.btn_collect:
                AddToScj();
                break;
            case R.id.btn_like:
                VideoLike();
                break;
            case R.id.btn_stamp:
                VideoDisLike();
                break;
            case R.id.image_poster:
                if(true){
                    Intent intent = new Intent();
                    intent.setClass(this,PlayerActivity.class);
                    if(listDataMenuAdapter.getSelectData() != null){
                        intent.putExtra("GUESTSERIES",listDataMenuAdapter.getSelectData());
                    } else  if(!CheckUtil.isEmpty(CollectManger.getInstance().getGuestSeriesList())){
                        intent.putExtra("GUESTSERIES",CollectManger.getInstance().getGuestSeriesList().get(0));
                    }
                    startActivity(intent);
                }
                break;

        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void init() {
        super.init();
        tvTitleChild.setText(getString(R.string.svp_details));
        layoutSelect.setSelected(true);

        iddddd = getIntent().getParcelableExtra("Iddddd");
        CollectManger.getInstance().setIddddd(iddddd);

        //tv_data.setText("收藏：209     点赞：443     踩：21");
        setTv_data("0","0","0");

        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if(null != newFocus){
                    if(newFocus.getId() == R.id.tab_layout){
                        return FocusBorder.OptionsFactory.get(1.0f, 1.0f, 0);
                    }
                }

                return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0); //返回null表示不使用焦点框框架
            }
        });

        //setBottomSheetBehavior();


        setListener();
        CommentAdapter = new CommentAdapter(this);
        tvList.setSpacingWithMargins(DisplayUtils.dip2px(10), 0);
        tvList.setAdapter(CommentAdapter);

        selectCollect.setSpacingWithMargins(DisplayUtils.dip2px(5), DisplayUtils.dip2px(15));
        listDataMenuAdapter = new ListDataMenuAdapter(this,FOUR);
        selectCollect.setAdapter(listDataMenuAdapter);

        initData();
        setTab();

    }

    private void initData(){
        CommentList();
        Play();
    }

    private void setTab(){
      //  NLog.e(NLog.TAGOther,"分割大小---》"+splitList(stringList,20).size());

        tabLayout.setScaleValue(1.1f);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                listDataMenuAdapter.setSelectView(null);
                listDataMenuAdapter.repaceDatas(splitList(CollectManger.getInstance().getGuestSeriesList(),20).get(tab.getPosition()));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setListener() {

        selectCollect.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

                if(null != listDataMenuAdapter.getSelectView()){
                    listDataMenuAdapter.getSelectView().setActivated(true);
                }
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));

            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                listDataMenuAdapter.setSelectData((GuestSeries) listDataMenuAdapter.getItem(position));
                listDataMenuAdapter.setSelectView(itemView);

                Intent intent = new Intent();
                intent.setClass(DetailActivity.this,PlayerActivity.class);
                intent.putExtra("GUESTSERIES",(GuestSeries) listDataMenuAdapter.getItem(position));
                startActivity(intent);

                }

        });

        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.01f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });

        selectCollect.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
                sildAnimina();
                if(selectCollect.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });


        //边界监听
//        mRecyclerView.setOnInBorderKeyEventListener(new TvRecyclerView.OnInBorderKeyEventListener() {
//            @Override
//            public boolean onInBorderKeyEvent(int direction, int keyCode, KeyEvent event) {
//                Log.i("zzzz", "onInBorderKeyEvent: ");
//                return false;//需要拦截返回true,否则返回false
//            }
//        });

        /*mRecyclerView.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                Log.i("@@@@", "onLoadMore: ");
                mRecyclerView.setLoadingMore(true); //正在加载数据
                mLayoutAdapter.appendDatas(); //加载数据
                mRecyclerView.setLoadingMore(false); //加载数据完毕
                return false; //是否还有更多数据
            }
        });*/
    }
    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @return
     */
    private   List<List<?>> splitList(List<?> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<?>> result = new ArrayList<List<?>>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<?> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }

    private void setListDataMenuAndTab(){

        if(CheckUtil.isEmpty(CollectManger.getInstance().getGuestSeriesList()) || CollectManger.getInstance().getGuestSeriesList().size() == 1){

            tabLayout.setVisibility(View.GONE);

        }else {
          //  tabLayout.setVisibility(View.VISIBLE);

          int i = splitList(CollectManger.getInstance().getGuestSeriesList(),20).size();
          switch (i){
              case 1:

                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("1-"+CollectManger.getInstance().getGuestSeriesList().size())
                          , true);
                  break;
              case 2:
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("1-20")
                          , true);
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("21-"+ CollectManger.getInstance().getGuestSeriesList().size())
                  );
                  break;
              case 3:
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("1-20")
                          , true);
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("21-40")
                  );
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("21-"+ CollectManger.getInstance().getGuestSeriesList().size())
                  );
                      break;
              case 4:
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("1-20")
                          , true);
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("21-40")
                  );
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("41-60")
                  );
                  tabLayout.addTab(
                          tabLayout.newTab()
                                  .setText("61-"+ CollectManger.getInstance().getGuestSeriesList().size())
                  );
                  break;
          }
        }

    }

    private String c;
    private String d;
    private String cc;
    private void setTv_data(String c,String d,String cc){

        if(!CheckUtil.isEmpty(c)){
            this.c = c;
        }
        if(!CheckUtil.isEmpty(d)){
            this.d = d;
        }
        if(!CheckUtil.isEmpty(cc)){
            this.cc = cc;
        }

        tv_data.setText("收藏："+this.c+"     点赞："+this.d+"     踩："+this.cc);
    }

    //踩影片
    private void  VideoDisLike(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom whatCom = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                null,
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire()
        );

        if(!CheckUtil.isEmpty(iddddd)){
            whatCom.setVideoID(iddddd.getId());
        }

        baseService.VideoDisLike(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<VideoDisLike>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<VideoDisLike>> baseDataResponseBaseResponse) {
                VideoDisLike videoDisLike = baseDataResponseBaseResponse.getData().getInfo().get(0);
                NToast.shortToastBaseApp(videoDisLike.getShowMsg());
                setTv_data(null,null,""+videoDisLike.getNumber());//踩


            }
        },DetailActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }
    //19点赞影片
    private void  VideoLike(){

        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom whatCom = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                null,
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire()
        );

        if(!CheckUtil.isEmpty(iddddd)){
            whatCom.setVideoID(iddddd.getId());
        }

        baseService.VideoLike(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<VideoDisLike>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<VideoDisLike>> baseDataResponseBaseResponse) {

                VideoDisLike videoDisLike = baseDataResponseBaseResponse.getData().getInfo().get(0);
                NToast.shortToastBaseApp(videoDisLike.getShowMsg());
                setTv_data(null,""+videoDisLike.getNumber(),null);//点赞

            }
        },DetailActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }
    //20收藏影片
    private void  AddToScj(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom whatCom = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                null,
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire()
        );

        if(!CheckUtil.isEmpty(iddddd)){
            whatCom.setVideoID(iddddd.getId());
        }
        baseService.AddToScj(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<VideoDisLike>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<VideoDisLike>> baseDataResponseBaseResponse) {

                VideoDisLike videoDisLike = baseDataResponseBaseResponse.getData().getInfo().get(0);
                NToast.shortToastBaseApp(videoDisLike.getShowMsg());

                setTv_data(""+videoDisLike.getNumber(),null,null);//收藏
            }
        },DetailActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    //获取评论列表
    private void CommentList(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom whatCom = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                null,
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire()
        );
        if(!CheckUtil.isEmpty(iddddd)){
            whatCom.setID(iddddd.getId());
        }

        baseService.CommentList(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<Comment>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                  //  NToast.shortToastBaseApp(e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<Comment>> baseDataResponseBaseResponse) {

                Comment comment = baseDataResponseBaseResponse.getData().getInfo().get(0);
                List<CommHot> CommHot = comment.getCommHot();

                if(!CheckUtil.isEmpty(CommHot)){
                    CommentAdapter.repaceDatas(CommHot);
                }


            }
        },DetailActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }
    //获取影片播放地址
    private void Play(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom whatCom = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                null,
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire()
        );
        if(!CheckUtil.isEmpty(iddddd)){
            whatCom.setID(iddddd.geteId());
        }

        baseService.Play(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<Detail>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<Detail>> baseDataResponseBaseResponse) {


                Detail detail = baseDataResponseBaseResponse.getData().getInfo().get(0);

                if(null != detail){
                    VL VL = detail.getVL();
                    SpanUtils spanUtils = new SpanUtils();
                    tv_video_introduction.setText(
                            spanUtils.append(VL.getTitle())
                                    .setFontSize(DisplayUtils.sp2px(32))
                                    .appendSpace(200)
                                    .append("评分：4.8")
                                    .setFontSize(DisplayUtils.sp2px(18))
                                    .appendLine()
                                    .appendLine(detail.getPost_Year()+"/"+detail.getChannel()+"/"+detail.getVideoType())
                                    .setFontSize(DisplayUtils.sp2px(16))
                                    .appendLine()
                                    .appendLine("主演："+VL.getStarring())
                                    .appendLine("导演："+VL.getDirector())
                                    .appendLine("简介：")
                                    .appendLine(detail.getContxt())
                                    .create()
                    );

                    setTv_data(""+detail.getFavrateNumber(),"0","0");//播放详情
                    GlideUtil.setGlideImage(DetailActivity.this, UrlUtils.getUrl(detail.getImgPath()),imagePoster);

                    CollectManger.getInstance().setGuestSeriesList(detail.getGuestSeriesList());
                    CollectManger.getInstance().setVipSeriesList(detail.getVipSeriesList());
                    CollectManger.getInstance().setPlayRecordURL(detail.getPlayRecordURL());
                    CollectManger.getInstance().setDetail(detail);

                    setListDataMenuAndTab();
                }
            }
        },DetailActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }


    //初始化滚动
    private void setBottomSheetBehavior(){
        View bottomSheet = findViewById(R.id.com_layout);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(DisplayUtils.getScreenHeight(this)-DisplayUtils.dip2px(300)-DisplayUtils.getStatusBarHeight(this));

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                String state = "null";
                switch (newState) {
                    case 1:
                        state = "STATE_DRAGGING";
                        break;
                    case 2:
                        state = "STATE_SETTLING";
                        break;
                    case 3:
                        state = "STATE_EXPANDED";
                        break;
                    case 4:
                        state = "STATE_COLLAPSED";
                        break;
                    case 5:
                        state = "STATE_HIDDEN";
                        break;
                }
                Log.d("MainActivity", "newState:" + state);
            }
            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
//                Log.d("MainActivity", "slideOffset:" + slideOffset);
            }
        });
    }

    private void sildAnimina(){

      final  float distance = DisplayUtils.getDimen(R.dimen.x400);


       // NLog.e(NLog.TAGOther,"获取评论的测量高度---》"+layoutSelect.getMeasuredHeight());

     final   int hight = layoutSelect.getMeasuredHeight();
     final   LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layoutSelect.getLayoutParams();

        if(isSild){
            isSild = !isSild;
            //ObjectAnimator translationX = new ObjectAnimator().ofFloat(layoutSelect,"translationX",0,0);
            ObjectAnimator translationY = new ObjectAnimator().ofFloat(layoutSelect,"translationY",-distance,0);

            AnimatorSet animatorSet = new AnimatorSet();  //组合动画
            animatorSet.playTogether(translationY); //设置动画
            animatorSet.setDuration(200);  //设置动画时间
            translationY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                   // NLog.e(NLog.TAGOther,"平移动画结束---》");
                    layoutParams.height = MATCH_PARENT;
                    layoutSelect.setLayoutParams(layoutParams);
                    layoutSelect.setSelected(true);
                }
            });
            animatorSet.start(); //启动
        }else {
            isSild = !isSild;
           // ObjectAnimator translationX = new ObjectAnimator().ofFloat(layoutSelect,"translationX",0,0);
            ObjectAnimator translationY = new ObjectAnimator().ofFloat(layoutSelect,"translationY",0,-distance);

            AnimatorSet animatorSet = new AnimatorSet();  //组合动画
            animatorSet.playTogether(translationY); //设置动画
            animatorSet.setDuration(200);  //设置动画时间
            animatorSet.start(); //启动
            layoutSelect.setSelected(false);
            layoutParams.height = (int) (hight + distance);
            layoutSelect.setLayoutParams(layoutParams);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CollectManger.Clear();//清空
    }
}
