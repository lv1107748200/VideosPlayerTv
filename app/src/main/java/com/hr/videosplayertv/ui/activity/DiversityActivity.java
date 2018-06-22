package com.hr.videosplayertv.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 播放记录，收藏。。。
 */
public class DiversityActivity extends BaseActivity {

    public final static int HISTORIESRECORD = 1001;//历史记录
    public final static int FAVORITE = 1002;//收藏夹
    public final static int PLAYERRECORD = 1003;//播放记录

    private int type = -1;

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;



    private GridAdapter gridAdapter;

    @Override
    public int getLayout() {
        return R.layout.activity_diversity;
    }

    @Override
    public void init() {
        super.init();
        int type = getIntent().getIntExtra("DiversityType",-1);

        switch (type){
            case HISTORIESRECORD:
                tvTitleChild.setText(getString(R.string.svp_histories));
                break;
            case FAVORITE:
                tvTitleChild.setText(getString(R.string.svp_favorite));
                break;
            case PLAYERRECORD:
                tvTitleChild.setText(getString(R.string.svp_recently_played_videos));
                break;

        }



        setListener();
        tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));
        gridAdapter = new GridAdapter(this);
        tvList.setAdapter(gridAdapter);

        initData();
    }


    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 37; i++){
            listData.add(new ListData());
        }

        gridAdapter.repaceDatas(listData);

    }

    private void setListener() {


        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

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

}
