package com.hr.videosplayertv.widget.presenter;

import android.support.v17.leanback.widget.FocusHighlightHelper;
import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;
import android.view.ViewGroup;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.utils.DisplayUtils;


/**
 * 测试 ListRowPresenter 重写.  用于水平滚动
 */
public class NewListRowPresenter extends ListRowPresenter {

    public NewListRowPresenter(int focusZoomFactor) {
        super(focusZoomFactor);
    }

    @Override
    protected void initializeRowViewHolder(RowPresenter.ViewHolder holder) {
        super.initializeRowViewHolder(holder);
        final ViewHolder rowViewHolder = (ViewHolder) holder;
        ItemBridgeAdapter itemBridgeAdapter = rowViewHolder.getBridgeAdapter();
        // 焦点事件处理.

        // 设置横向item的间隔.
       rowViewHolder.getGridView().setHorizontalSpacing(DisplayUtils.getDimen(R.dimen.x20));
       rowViewHolder.getGridView().setVerticalSpacing(DisplayUtils.getDimen(R.dimen.x20));
    }

    @Override
    protected RowPresenter.ViewHolder createRowViewHolder(ViewGroup parent) {
        ListRowPresenter.ViewHolder holder = (ViewHolder) super.createRowViewHolder(parent);

        return holder;
    }
}
