package com.hr.videosplayertv.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseFragment;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.activity.DetailActivity;
import com.hr.videosplayertv.ui.activity.ListDataActivity;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 通用版 根据数据不同来区分页面
 */
public class MultipleFragment extends BaseFragment {

    private int type ;

    public MultipleFragment setType(int type) {
        this.type = type;
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
                break;
            case 1:
                viewStub.setLayoutResource(R.layout.item_multiple_classify);
                new  ClassifyLayout(viewStub.inflate());
                break;
            default:
                viewStub.setLayoutResource(R.layout.item_multiple_common);
                new  CommonLayout(viewStub.inflate());
                break;
        }

    }


    //分类试图
    class ClassifyLayout{
        @BindView(R.id.tv_list)
        TvRecyclerView tvList;

        private GridAdapter gridAdapter;

        public ClassifyLayout(View view) {
            ButterKnife.bind(this,view);

            setListener();
            tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));
            gridAdapter = new GridAdapter(MultipleFragment.this.getContext());
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
                    Intent intent = new Intent(MultipleFragment.this.getContext(),ListDataActivity.class);

                    startActivity(intent);
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

    //common

    class CommonLayout {

        @BindView(R.id.image_one)
        ImageView imageOne;

        public CommonLayout(View view) {
            ButterKnife.bind(this,view);
            imageOne.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b){
                        mFocusBorder.setVisible(b);
                        onMoveFocusBorder(view, 1.01f, 0);
                    }else {
                        mFocusBorder.setVisible(b);
                    }

                }
            });

            imageOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MultipleFragment.this.getContext(),DetailActivity.class);

                    startActivity(intent);
                }
            });
        }
    }


}
