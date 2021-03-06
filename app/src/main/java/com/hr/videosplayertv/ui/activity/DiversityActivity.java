package com.hr.videosplayertv.ui.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.common.Iddddd;
import com.hr.videosplayertv.db.DBResultCallback;
import com.hr.videosplayertv.db.PHData;
import com.hr.videosplayertv.db.RealmDBManger;
import com.hr.videosplayertv.db.TabsData;
import com.hr.videosplayertv.net.base.BaseDataResponse;
import com.hr.videosplayertv.net.base.BaseResponse;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.net.entry.request.WhatCom;
import com.hr.videosplayertv.net.entry.response.FavoriteList;
import com.hr.videosplayertv.net.entry.response.Result;
import com.hr.videosplayertv.net.entry.response.SearchList;
import com.hr.videosplayertv.net.entry.response.UserToken;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpException;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.videosplayertv.utils.CheckUtil;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.Formatter;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.utils.NToast;
import com.hr.videosplayertv.widget.dialog.LoadingDialog;
import com.hr.videosplayertv.widget.dialog.OperationDialog;
import com.hr.videosplayertv.widget.layout.AddLineLayout;
import com.hr.videosplayertv.widget.single.UserInfoManger;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.hr.videosplayertv.ui.adapter.GridAdapter.FAVORITELAYOUT;
import static com.hr.videosplayertv.ui.adapter.GridAdapter.PLAYERRECORDLAYOUT;

/**
 * 播放记录，收藏。。。
 */
public class DiversityActivity extends BaseActivity implements OperationDialog.DiaLogCallBack {

    public final static int HISTORIESRECORD = 1001;//历史记录
    public final static int FAVORITE = 1002;//收藏夹
    public final static int PLAYERRECORD = 1003;//播放记录

    private int type = -1;

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;
    @BindView(R.id.addLayout)
    AddLineLayout addLayout;


    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 1;
    private boolean isCanBack = false;
    private int itemHight = -1;

    private GridAdapter gridAdapter;

    private OperationDialog operationDialog;

    private Result result;
    private int deleteNumber = -1;

    @Override
    public int getLayout() {
        return R.layout.activity_diversity;
    }

    @Override
    public void init() {
        super.init();

        type = getIntent().getIntExtra("DiversityType",-1);
        setListener();
        tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));

        switch (type){
            case HISTORIESRECORD:
                tvTitleChild.setText(getString(R.string.svp_histories));
                break;
            case FAVORITE:
                operationDialog = new OperationDialog(this).setCallBack(this).creatDialog();
                tvTitleChild.setText(getString(R.string.svp_favorite));
                gridAdapter = new GridAdapter(this,FAVORITELAYOUT){
                    @Override
                    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {
                        if(itemHight != -1){
                            helper.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)itemHight));
                        }
                        super.onBindItemHolder(helper, item, position);
                    }
                };
                break;
            case PLAYERRECORD:
                tvTitleChild.setText(getString(R.string.svp_recently_played_videos));
                gridAdapter = new GridAdapter(this,PLAYERRECORDLAYOUT){
                    @Override
                    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {
                        if(itemHight != -1){
                            helper.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)itemHight));
                        }
                        super.onBindItemHolder(helper, item, position);
                    }
                };
                break;

        }

        tvList.setAdapter(gridAdapter);
        load();
    }

    @Override
    public void BFCallBack() {
                     if(result != null){
                         Intent intent = new Intent();
                         intent.setClass(DiversityActivity.this,DetailActivity.class);
                         intent.putExtra("Iddddd",new Iddddd(result.getVideoID(),result.getSecretVID()));
                         startActivity(intent);
                     }
    }

    @Override
    public void SCCallBack() {

        if(null != result){
            operationDialog.ShanChu("... 正在删除 ...");
            RemoveFavorite(result.getFavoriteID()+"",deleteNumber);
        }else {
            if(null != operationDialog){
                operationDialog.ShanChuOr(false,"删除失败");
            }
        }


    }

    private void setListener() {
        tvList.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {

                tvList.getViewTreeObserver().removeOnPreDrawListener(this);

                //NLog.e(NLog.TAGOther,"tvlist 高度 ---》"+ tvList.getHeight());

                itemHight = DisplayUtils.getWide(5,DisplayUtils.getDimen(R.dimen.x22),
                        tvList.getPaddingBottom(),tvList.getWidth())/3;

                itemHight = itemHight * 4;

                // NLog.e(NLog.TAGOther,"tvlist 高度 ---》" + itemHight);

                if(!CheckUtil.isEmpty(gridAdapter.getmDatas())){
                    gridAdapter.notifyDataSetChanged();
                }

                return true;
            }
        });

        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView,
                        Formatter.getScale(
                                itemView.getWidth()
                                ,itemView.getHeight()
                                ,DisplayUtils.getDimen(R.dimen.x15)
                        ),
                        Formatter.getRoundRadius());
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Object o =   gridAdapter.getItem(position);
                Intent intent = new Intent();
                switch (type){
                    case HISTORIESRECORD:

                        break;
                    case FAVORITE:
                        deleteNumber = position;
                         if(o instanceof Result){
                           result = (Result) o;
                         }

                        if(null != operationDialog){

                            operationDialog.show(result.getTitle());
                        }

                        break;
                    case PLAYERRECORD:

                        if(o instanceof PHData){
                            intent.setClass(DiversityActivity.this,PlayerActivity.class);
                            intent.putExtra("PLAYID",((PHData) o).getKey());
                            intent.putExtra("NAME",((PHData) o).getName());
                            startActivity(intent);
                        }

                        break;
                }

            }
        });


        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
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


        tvList.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                tvList.setLoadingMore(true); //正在加载数据
                isLoadMore = true;
                load();
                return isMore; //是否还有更多数据
            }
        });
        tvList.addOnScrollListener(mOnScrollListener);
    }

    private void load(){
        switch (type){
            case HISTORIESRECORD:

                break;
            case FAVORITE:
                addLayout.showClick();
             FavoriteList();
                break;
            case PLAYERRECORD:
                addLayout.showClick();
                getPHData();
                break;
        }
    }

    private void getPHData(){
        RealmDBManger.getMyRealm()
                .where(PHData.class)
                .findAllAsync().addChangeListener(new RealmChangeListener<RealmResults<PHData>>() {
            @Override
            public void onChange(RealmResults<PHData> phData) {
                new DBResultCallback<RealmResults<PHData>>(){
                    @Override
                    public void onSuccess(RealmResults<PHData> phData) {

                        addLayout.hideClick();

                        if(!CheckUtil.isEmpty(phData)){
                            NLog.e(NLog.DB," 播放记录 --->" + phData.size());
                            phData = phData.sort("time", Sort.DESCENDING);
                            if(phData.size() >  100){
                                gridAdapter.repaceDatas(phData.subList(0,100));
                            }else {
                                gridAdapter.repaceDatas(phData);
                            }

                        }else {
                            addLayout.hideAndShowMessage(getString(R.string.svp_null_data));
                        }
                    }

                    @Override
                    public void onError(String errString) {
                        addLayout.hideClick();
                    }
                }.onCallback(phData);
            }
        });
    }

    private void FavoriteList(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom whatCom = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire()
        );

        whatCom.setPageSize("20");
        whatCom.setPageIndex(pageNo+"");

        baseService.FavoriteList(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<FavoriteList>>>() {
            @Override
            public void onError(HttpException e) {

                addLayout.hideClick();

                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {

                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<FavoriteList>> baseDataResponseBaseResponse) {

                addLayout.hideClick();
                if(isLoadMore){
                    tvList.setLoadingMore(false);
                }
                BaseDataResponse<FavoriteList> baseDataResponse = baseDataResponseBaseResponse.getData();
                List<FavoriteList> whatLists = baseDataResponse.getInfo();
                List<Result> results = new ArrayList<>();
                if(!CheckUtil.isEmpty(whatLists)){
                    results = whatLists.get(0).getResult();
                }

                if(!CheckUtil.isEmpty(results)){

                    pageNo = pageNo+1;
                    if(isLoadMore){
                        gridAdapter.appendDatas(results);
                    }else {
                        gridAdapter.repaceDatas(results);
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
        },DiversityActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }
//删除收藏
    private void RemoveFavorite(String Favoriteids, final int number){

        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken && null != Favoriteids){
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
        whatCom.setFavoriteids(Favoriteids);

        baseService.RemoveFavorite(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<String>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){

                    if(null != operationDialog){
                        operationDialog.ShanChuOr(false,e.getMsg());
                    }

                }else {
                    if(null != operationDialog){
                        operationDialog.ShanChuOr(false,"删除失败");
                    }
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<String>> baseDataResponseBaseResponse) {
                operationDialog.ShanChuOr(true,"删除成功");
                gridAdapter.Swiped(number);

            }
        },DiversityActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
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
