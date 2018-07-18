package com.hr.videosplayertv.ui.adapter.viewStub;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
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
import com.hr.videosplayertv.utils.ColorUtils;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;
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
        if (false) {
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
             StaggeredGridLayoutHelper helper = new StaggeredGridLayoutHelper(7, 20);
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

        if (false) {
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

        if(false){
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
        if (false) {
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
        if (false) {
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

        if(false){
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

               // NLog.e(NLog.TAGOther,"首页选中序号--->" + position);

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

        tvList.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                return true; //是否还有更多数据
            }
        });
    }

    public void setUpData(List<WhatList> whatLists){

    }

    static class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

        private Context mContext;

        private LayoutHelper mLayoutHelper;


        private VirtualLayoutManager.LayoutParams mLayoutParams;
        private int mCount = 0;


        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_grid, parent, false));
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new VirtualLayoutManager.LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {

            ImageView imageView = holder.itemView.findViewById(R.id.image);
            TextView textView = holder.itemView.findViewById(R.id.title_sdfd);

            textView.setText(offsetTotal+"");

            if(position == 6){
                imageView.setVisibility(View.GONE);
            }if(position == 7){
                imageView.setVisibility(View.GONE);

            }else {

                imageView.setVisibility(View.VISIBLE);

                GlideUtil.setGlideImage(mContext
                        , ImgDatasUtils.getUrl()
                        ,(ImageView) holder.itemView.findViewById(R.id.image),R.drawable.hehe);
            }

        }

        @Override
        public int getItemCount() {
            return mCount;
        }
    }
    static class MainViewHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public MainViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }
}
