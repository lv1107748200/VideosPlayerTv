package com.hr.videosplayertv.ui.adapter.viewStub;

import android.content.Intent;
import android.view.View;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.common.ImmobilizationData;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.activity.ListDataActivity;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hr.videosplayertv.ui.adapter.GridAdapter.CLASSIFYLAYOUT;

public class ClassifyLayout {
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;

    private GridAdapter gridAdapter;
    private BaseActivity mContext;

    public ClassifyLayout(View view, BaseActivity context) {
        ButterKnife.bind(this,view);
        mContext = context;
        setListener();
        tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x40), DisplayUtils.getDimen(R.dimen.x40));
        gridAdapter = new GridAdapter(mContext,CLASSIFYLAYOUT);
        tvList.setAdapter(gridAdapter);

        initData();
    }
    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i = 0, j = ImmobilizationData.Tags.values().length; i< j; i++){

            if(i==0 || i==1){

            }else {
                listData.add(new ListData(ImmobilizationData.Tags.getNameByIndex(i)));
            }

        }

        gridAdapter.repaceDatas(listData);
    }

    private void setListener() {


        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                mContext.onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Intent intent = new Intent(mContext,ListDataActivity.class);

                if(gridAdapter.getItem(position) instanceof ListData)
                intent.putExtra("TYPE",((ListData) gridAdapter.getItem(position)).getTitle());
                mContext. startActivity(intent);
            }
        });


        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // mFocusBorder.setVisible(hasFocus);
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
