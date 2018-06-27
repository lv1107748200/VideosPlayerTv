package com.hr.videosplayertv.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseFragment;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.activity.DetailActivity;
import com.hr.videosplayertv.ui.activity.ListDataActivity;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.adapter.HomeAdapter;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.ui.adapter.MetroAdapter;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.widget.focus.FocusBorder;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;
import com.owen.tvrecyclerview.widget.V7GridLayoutManager;

import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.VirtualLayoutManager.LayoutParams;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

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
                viewStub.setLayoutResource(R.layout.item_multiple_home);
                new  HomeLayout(viewStub.inflate());
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
    //首页布局
    class HomeLayout{
        @BindView(R.id.tv_list)
        TvRecyclerView tvList;
        private MetroAdapter metroAdapter;

        public HomeLayout(View view) {
            ButterKnife.bind(this,view);

            setListener();
            metroAdapter = new MetroAdapter(mContext);
            tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));
            tvList.setAdapter(metroAdapter);


            initData();
        }

        private void initData(){
            List<ListData> listData = new ArrayList<>();

            for (int i =0 ;i< 60; i++){
                listData.add(new ListData());
            }

            metroAdapter.repaceDatas(listData);
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


        private ListDataMenuAdapter listDataMenuAdapter;
        @BindView(R.id.common_menu)
        TvRecyclerView mainMenu;

        @BindView(R.id.image_one)
        ImageView image_one;
        @BindView(R.id.image_two)
        ImageView image_two;
        @BindView(R.id.image_three)
        ImageView image_three;
        @BindView(R.id.image_four)
        ImageView image_four;
        @BindView(R.id.image_five)
        ImageView image_five;

        @OnClick({R.id.image_one})
        public void Onclick(View view){
            switch (view.getId()){
                case R.id.image_one:
                    Intent intent = new Intent(MultipleFragment.this.getContext(),DetailActivity.class);
                    startActivity(intent);
                    break;

            }
        }

        public CommonLayout(View view) {
            ButterKnife.bind(this,view);

            GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_one);
            GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_two);
            GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_three);
            GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_four);
            GlideUtil.setGlideImage(mContext,ImgDatasUtils.getUrl(),image_five);

            mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
                @Override
                public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                    return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0); //返回null表示不使用焦点框框架
                }
            });

            setListener();
            listDataMenuAdapter = new ListDataMenuAdapter(MultipleFragment.this.getContext(),true);
            mainMenu.setSpacingWithMargins(10, 30);
            mainMenu.setAdapter(listDataMenuAdapter);

            initData();
        }

        private void initData(){
            List<ListData> listData = new ArrayList<>();

            for (int i =0 ;i< 8; i++){
                listData.add(new ListData());
            }

            listDataMenuAdapter.repaceDatas(listData);
        }

        private void setListener() {

            mainMenu.setOnItemListener(new SimpleOnItemListener() {

                @Override
                public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                    //   onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(MainActivity.this,3));

                }

                @Override
                public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                }
            });

            mainMenu.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {

                    //  mFocusBorder.setVisible(hasFocus);

                }
            });

        }


    }


}
