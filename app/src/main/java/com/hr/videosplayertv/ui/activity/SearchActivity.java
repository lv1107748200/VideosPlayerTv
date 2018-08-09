package com.hr.videosplayertv.ui.activity;

import android.content.Intent;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.common.Iddddd;
import com.hr.videosplayertv.net.base.BaseDataResponse;
import com.hr.videosplayertv.net.base.BaseResponse;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.net.entry.request.WhatCom;
import com.hr.videosplayertv.net.entry.response.Result;
import com.hr.videosplayertv.net.entry.response.SearchList;
import com.hr.videosplayertv.net.entry.response.UserToken;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpException;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.CheckUtil;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.Formatter;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.utils.NToast;
import com.hr.videosplayertv.widget.AffPasWindow;
import com.hr.videosplayertv.widget.dialog.LoadingDialog;
import com.hr.videosplayertv.widget.keyboard.SkbContainer;
import com.hr.videosplayertv.widget.keyboard.SoftKey;
import com.hr.videosplayertv.widget.keyboard.SoftKeyBoardListener;
import com.hr.videosplayertv.widget.layout.AddLineLayout;
import com.hr.videosplayertv.widget.single.UserInfoManger;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActivity implements AffPasWindow.AffPasWindowCallBack {

    public static int pp = 0;

    @BindView(R.id.tv_show_message)
    EditText tv_show_message;
    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.skbContainer)
    SkbContainer skbContainer;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;
    @BindView(R.id.addLayout)
    AddLineLayout addLayout;

    private String type;
    private GridAdapter gridAdapter;
    private AffPasWindow affPasWindow;
    private StringBuffer stringBuffer;//搜索字符

    private boolean isMore = true;
    private boolean isLoadMore = false;
    private int pageNo = 1;

    private String Tags = "A";

    private String PID = null;

    private boolean isCanBack = false;
    private int itemHight = -1;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        super.init();

        type = getIntent().getStringExtra("TYPE");
        PID = getIntent().getStringExtra("PID");

      //  NLog.e(NLog.TAGOther,"PID 搜索项目 ---> " + PID);

        if(!CheckUtil.isEmpty(type)){
            tvTitleChild.setText(type+getString(R.string.svp_search));
        }else {
            tvTitleChild.setText(getString(R.string.svp_search));
        }


        affPasWindow = new AffPasWindow(this,this);
        skbContainer.setSkbLayout(R.xml.skb_all_key);
        skbContainer.setFocusable(true);
        skbContainer.setFocusableInTouchMode(true);
        // 设置属性(默认是不移动的选中边框)
       // setSkbContainerMove();
        setSkbContainerOther();
        //
       // skbContainer.setSelectSofkKeyFront(true); // 设置选中边框最前面.
        // 监听键盘事件.
        skbContainer.setOnSoftKeyBoardListener(new SoftKeyBoardListener() {
            @Override
            public void onCommitText(SoftKey softKey) {
               // NLog.e(NLog.KEY,"获取view 坐标   :  "+skbContainer.getX());


                int keyCode = softKey.getKeyCode();
                switch (keyCode){
                    case 67:
                        addOrRemove(null,1);
                        break;
                    case 68:
                        addOrRemove(null,2);
                        break;
                    case 69:
                        setSkbContainerOther();
                        skbContainer.setSkbLayout(R.xml.skb_all_key);
                        break;
                    case 70:
                        setSkbContainerOther();
                        skbContainer.setSkbLayout(R.xml.skb_t9_keys);
                        break;
                    default:
                        if ((skbContainer.getSkbLayoutId() == R.xml.skb_t9_keys)) {
                            onCommitT9Text(softKey);
                        } else {

                            NLog.e(NLog.KEY,"--->keycode   :  "+keyCode);

                            String keyLabel = softKey.getKeyLabel();
                            addOrRemove(keyLabel,0);

//                    int keyCode = softKey.getKeyCode();
//                    String keyLabel = softKey.getKeyLabel();
//                    if (!TextUtils.isEmpty(keyLabel)) { // 输入文字.
//
//                    } else { // 自定义按键，这些都是你自己在XML中设置的keycode.
//                        keyCode = softKey.getKeyCode();
//                        if (keyCode == KeyEvent.KEYCODE_DEL) {
//
//
//                        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//                            finish();
//                        } else if (keyCode == 66) {
//                            Toast.makeText(getApplicationContext(), "回车", Toast.LENGTH_LONG).show();
//                        } else if (keyCode == 250) { //切换键盘
//                            // 这里只是测试，你可以写自己其它的数字键盘或者其它键盘
//                            setSkbContainerOther();
//                            skbContainer.setSkbLayout(R.xml.sbd_number);
//                        }
//                    }
                        }
                        break;
                }


            }

            @Override
            public void onBack(SoftKey key) {
                    finish();
            }

            @Override
            public void onDelete(SoftKey key) {

            }

        });
        // DEMO（测试键盘失去焦点和获取焦点)
        skbContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mOldSoftKey != null)
                        skbContainer.setKeySelected(mOldSoftKey);
                    else
                        skbContainer.setDefualtSelectKey(0, 0);
                } else {
                    mOldSoftKey = skbContainer.getSelectKey();
                    skbContainer.setKeySelected(null);
                }
            }
        });


        setListener();
        tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));
        gridAdapter = new GridAdapter(this){
            @Override
            public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {
                if(itemHight != -1){
                    helper.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)itemHight));
                }
                super.onBindItemHolder(helper, item, position);
            }
        };
        tvList.setAdapter(gridAdapter);

       // initData();

        load(Tags);
    }

    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 37; i++){
            listData.add(new ListData());
        }

        gridAdapter.repaceDatas(listData);
    }

    private void setListener() {

        tvList.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tvList.getViewTreeObserver().removeOnPreDrawListener(this);

                //NLog.e(NLog.TAGOther,"tvlist 高度 ---》"+ tvList.getHeight());

                itemHight = DisplayUtils.getWide(3,DisplayUtils.getDimen(R.dimen.x22),
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
                setShowOrDiss(true);
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
                Object o =  gridAdapter.getItem(position);

                if(o instanceof WhatList){
                    Intent intent = new Intent(SearchActivity.this,DetailActivity.class);
                    intent.putExtra("Iddddd",new Iddddd(((WhatList) o).getID(),((WhatList) o).getContxt()));
                    startActivity(intent);
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
                load(Tags);
                return isMore; //是否还有更多数据
            }
        });

        tvList.addOnScrollListener(mOnScrollListener);

    }

    private void load(String T){

        if(CheckUtil.isEmpty(T))
            return;

        Tags = T;

        addLayout.showClick();
        Search();
    }

    private void Search(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom data = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                "0",
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire(),
                "20",
                ""+pageNo,
                Tags
        );

        if(!CheckUtil.isEmpty(PID)){
            data.setCID(PID);
        }

        baseService.Search(data, new HttpCallback<BaseResponse<BaseDataResponse<SearchList>>>() {
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
            public void onSuccess(BaseResponse<BaseDataResponse<SearchList>> baseDataResponseBaseResponse) {

                addLayout.hideClick();
                List<WhatList>  whatLists = baseDataResponseBaseResponse.getData().getInfo().get(0).getResult();
                setTvList(whatLists);

            }
        },SearchActivity.this.bindUntilEvent(ActivityEvent.STOP));
    }

    private void setTvList(List<WhatList>  whatLists){
        if(isLoadMore){
            tvList.setLoadingMore(false);
        }

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
    /**
     * 处理T9键盘的按键.
     * @param softKey
     */
    private void onCommitT9Text(SoftKey softKey) {

        switch (softKey.getKeyCode()){
            case 1251:
                addOrRemove("1",0);
                break;
            case 1250:
                addOrRemove("0",0);
                break;
                default:
                   // NLog.e(NLog.KEY,"获取view 坐标  x,y :  "+skbContainer.getX()+","+skbContainer.getY());
                   // NLog.e(NLog.KEY,"获取view 坐标  l,t :  "+softKey.getLeft()+","+softKey.getTop());

                    int x  = (int) (softKey.getLeft() + skbContainer.getX());
                    int y = (int) (softKey.getTop() + skbContainer.getY());

                   // NLog.e(NLog.KEY,"移动view 坐标  x,y :  "+x+","+y);

                    affPasWindow.moveLyout(x - pp,y - pp,softKey.getKeyCode());
                    break;
        }


    }
    private void setSkbContainerMove() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(true); // 设置是否移动按键边框.

        int sise = DisplayUtils.getDimen(R.dimen.x20);

        RectF rectf = new RectF(
                sise,
                sise,
                sise,
                sise
        );
        skbContainer.setSoftKeySelectPadding(rectf); // 设置移动边框相差的间距.
        skbContainer.setMoveDuration(200); // 设置移动边框的时间(默认:300)
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框在最前面.
    }

    /**
     * 切换布局测试.
     * 因为布局不相同，所以属性不一样，
     * 需要重新设置(不用参考我的,只是DEMO)
     */
    private void setSkbContainerOther() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(false);
        skbContainer.setSoftKeySelectPadding(0);
        skbContainer.setSelectSofkKeyFront(false);
    }

    SoftKey mOldSoftKey;


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

        if (skbContainer.onSoftKeyDown(keyCode, event))
            return true;

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyUp(keyCode, event))
            return true;
        return super.onKeyUp(keyCode, event);
    }
    /**
    *T9子选项
    */
    @Override
    public void whatText(String s) {
        addOrRemove(s,0);
    }

    /**
     * @param s 字符串处理
     */
    private void addOrRemove(String s,int type){
        if(null == stringBuffer){
                stringBuffer = new StringBuffer();
        }
        if(type == 0){//添加
            if(null != s)
                stringBuffer.append(s);
        }else if(type == 1){//删除
            if(stringBuffer.length()>0)
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }else if(type == 2){//清空
            if(stringBuffer.length()>0)
            stringBuffer.delete(0,stringBuffer.length());
        }
        tv_show_message.setText(stringBuffer);



        lifecycleSubject.onNext(ActivityEvent.STOP);
        lifecycleSubject.onNext(ActivityEvent.START);

        isMore = true;
        isLoadMore = false;
        pageNo = 1;
        tvList.setHasMoreData(true);

        load(stringBuffer.toString());
    }


}
