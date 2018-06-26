package com.hr.videosplayertv.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.owen.tvrecyclerview.widget.MetroGridLayoutManager;
import com.owen.tvrecyclerview.widget.MetroTitleItemDecoration;

/**
 * Created by owen on 2017/7/14.
 */

public class MetroAdapter extends CommonRecyclerViewAdapter<ListData>
        implements MetroTitleItemDecoration.Adapter{
    public MetroAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_home_grid;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ListData item, int position) {
        helper.getHolder()
                .showImage(R.id.image, ImgDatasUtils.getUrl());


        final View itemView = helper.itemView;
        MetroGridLayoutManager.LayoutParams lp = (MetroGridLayoutManager.LayoutParams) itemView.getLayoutParams();

        if(position >=7){
            lp.rowSpan = 6;
            lp.colSpan = 7;
        }else if(position < 7) {
            lp.sectionIndex = 0;
            lp.rowSpan = 5;
            lp.colSpan = 6;
        }
        itemView.setLayoutParams(lp);
    }

    @Override
    public View getTitleView(int index, RecyclerView parent) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_title, parent, false);
    }
}
