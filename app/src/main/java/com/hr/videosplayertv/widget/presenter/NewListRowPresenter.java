package com.hr.videosplayertv.widget.presenter;

import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;
import android.view.ViewGroup;



/**
 * 测试 ListRowPresenter 重写.
 */
public class NewListRowPresenter extends ListRowPresenter {

    @Override
    protected void initializeRowViewHolder(RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);
        final ViewHolder rowViewHolder = (ViewHolder) holder;
        ItemBridgeAdapter itemBridgeAdapter = rowViewHolder.getBridgeAdapter();
        // 焦点事件处理.

        // 设置横向item的间隔.
        rowViewHolder.getGridView().setHorizontalSpacing(10);
    }

    @Override
    protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        ListRowPresenter.ViewHolder holder = (ViewHolder) super.createRowViewHolder(parent);

        return holder;
    }
}
