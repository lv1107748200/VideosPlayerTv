package com.hr.videosplayertv.ui.adapter.viewStub;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.EightLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.ui.activity.ListDataActivity;
import com.hr.videosplayertv.ui.adapter.SubAdapter;
import com.hr.videosplayertv.ui.adapter.viewholder.MainViewHolder;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.NLog;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeLayout {
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;
    private DelegateAdapter delegateAdapter;
    private BaseActivity mContext;

    public HomeLayout(View view , BaseActivity context) {
        ButterKnife.bind(this,view);
        mContext = context;
        setListener();

        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        tvList.setLayoutManager(layoutManager);
//            final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
//            tvList.setRecycledViewPool(viewPool);
        //viewPool.setMaxRecycledViews(0, 20);
        // tvList.setNestedScrollingEnabled(false);
        delegateAdapter = new DelegateAdapter(layoutManager, true);
        tvList.setAdapter(delegateAdapter);



        initData();
    }

    private void initData(){
        List<DelegateAdapter.Adapter> adapters = new LinkedList<>();
        if (true) {
            EightLayoutHelper helper = new EightLayoutHelper(DisplayUtils.getDimen(R.dimen.x20));
            helper.setColWeights(new float[]{50f,20f,20f,20f,20f,20f,30f,30f});
            VirtualLayoutManager.LayoutParams lp = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x550));
            adapters.add(new SubAdapter(mContext, helper, 8, lp) {
                @Override
                public void onBindViewHolder(MainViewHolder holder, int position) {
                    super.onBindViewHolder(holder, position);
                }
            });
        }
        if (true) {
            adapters.add(new SubAdapter(mContext, new LinearLayoutHelper(), 1) {


                @Override
                public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == 1)
                        return new MainViewHolder(
                                LayoutInflater.from(mContext).inflate(R.layout.item_home_title, parent, false));

                    return super.onCreateViewHolder(parent, viewType);
                }

                @Override
                public int getItemViewType(int position) {
                    return 1;
                }

                @Override
                protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {

                }

                @Override
                public void onBindViewHolder(MainViewHolder holder, int position) {

                }
            });
        }

        if(true){
            final StaggeredGridLayoutHelper helper = new StaggeredGridLayoutHelper(7, 20);
            //  helper.setMargin(20, 10, 10, 10);
            //    helper.setPadding(10, 10, 20, 10);
            //  helper.setBgColor(0xFF86345A);
            adapters.add(new SubAdapter(mContext, helper, 7) {
                @Override
                public void onBindViewHolder(MainViewHolder holder, int position) {
                    // super.onBindViewHolder(holder, position);
                    VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x180));
                    holder.itemView.setLayoutParams(layoutParams);
                }
            });
        }

        if (true) {
            adapters.add(new SubAdapter(mContext, new LinearLayoutHelper(), 1) {


                @Override
                public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == 1)
                        return new MainViewHolder(
                                LayoutInflater.from(mContext).inflate(R.layout.item_home_title, parent, false));

                    return super.onCreateViewHolder(parent, viewType);
                }

                @Override
                public int getItemViewType(int position) {
                    return 1;
                }

                @Override
                protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {

                }

                @Override
                public void onBindViewHolder(MainViewHolder holder, int position) {

                }
            });
        }

        if(true){
            final StaggeredGridLayoutHelper helper = new StaggeredGridLayoutHelper(6, 20);
            //  helper.setMargin(20, 10, 10, 10);
            //    helper.setPadding(10, 10, 20, 10);
            //  helper.setBgColor(0xFF86345A);
            adapters.add(new SubAdapter(mContext, helper, 12) {
                @Override
                public void onBindViewHolder(MainViewHolder holder, int position) {
                    //super.onBindViewHolder(holder, position);
                    VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtils.getDimen(R.dimen.x300));
                    holder.itemView.setLayoutParams(layoutParams);
                }
            });
        }
        if (true) {
            adapters.add(new SubAdapter(mContext, new LinearLayoutHelper(), 1) {


                @Override
                public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    if (viewType == 1)
                        return new MainViewHolder(
                                LayoutInflater.from(mContext).inflate(R.layout.item_home_title, parent, false));

                    return super.onCreateViewHolder(parent, viewType);
                }

                @Override
                public int getItemViewType(int position) {
                    return 1;
                }

                @Override
                protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {

                }

                @Override
                public void onBindViewHolder(MainViewHolder holder, int position) {

                }
            });
        }

        if(true){
            final StaggeredGridLayoutHelper helper = new StaggeredGridLayoutHelper(6, 20);
            //  helper.setMargin(20, 10, 10, 10);
            //    helper.setPadding(10, 10, 20, 10);
            //  helper.setBgColor(0xFF86345A);
            adapters.add(new SubAdapter(mContext, helper, 12) {
                @Override
                public void onBindViewHolder(MainViewHolder holder, int position) {
                    //super.onBindViewHolder(holder, position);
                    VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  DisplayUtils.getDimen(R.dimen.x300));
                    holder.itemView.setLayoutParams(layoutParams);
                }
            });
        }

        delegateAdapter.setAdapters(adapters);
    }

    private void setListener() {


        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

                NLog.e(NLog.TAGOther,"首页选中序号--->" + position);

                if(position < 8){
                    mContext. onMoveFocusBorder(itemView, 1.05f, DisplayUtils.dip2px(3));
                }else {
                    mContext. onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
                }


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
                mContext.  mFocusBorder.setVisible(hasFocus);
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

    public void setUpData(List<WhatList> whatLists){



    }

}
