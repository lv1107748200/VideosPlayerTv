package com.hr.videosplayertv.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewHolder;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;

public class HomeAdapter extends CommonRecyclerViewAdapter<ListData> implements MetroTitleItemDecoration.Adapter{

    public HomeAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_list_data;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ListData item, int position) {
        helper.getHolder()
                .showImage(R.id.image,"https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4116929131,507038119&fm=26&gp=0.jpg");
    }


    @Override
    public View getTitleView(int index, RecyclerView parent) {
        return null;
    }
}
