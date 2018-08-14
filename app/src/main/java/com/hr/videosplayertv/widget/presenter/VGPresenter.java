package com.hr.videosplayertv.widget.presenter;


import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.ViewGroup;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.utils.DisplayUtils;

/*
 * lv   2018/8/13
 */
public class VGPresenter extends NewVGBasePresenter {

    @Override
    protected void initializeRowViewHolder(RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);
        final NewVGBasePresenter.ViewHolder rowViewHolder = (NewVGBasePresenter.ViewHolder) holder;
        ItemBridgeAdapter itemBridgeAdapter = rowViewHolder.getBridgeAdapter();
        // 焦点事件处理.

        // 设置横向item的间隔.
        rowViewHolder.getGridView().setHorizontalSpacing(DisplayUtils.getDimen(R.dimen.x20));
        rowViewHolder.getGridView().setVerticalSpacing(DisplayUtils.getDimen(R.dimen.x20));
    }
    @Override
    protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        NewVGBasePresenter.ViewHolder holder = (ViewHolder) super.createRowViewHolder(parent);

        return holder;
    }
}
