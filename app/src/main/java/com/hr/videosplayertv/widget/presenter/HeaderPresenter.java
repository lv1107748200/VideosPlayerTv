package com.hr.videosplayertv.widget.presenter;

import android.graphics.Color;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowHeaderPresenter;
import android.support.v17.leanback.widget.RowHeaderView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hr.videosplayertv.R;


/**
 *  关于横向item的头样式.
 */
public class HeaderPresenter extends RowHeaderPresenter {

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {
        super.onBindViewHolder(viewHolder, item);

        RowHeaderView headerView = viewHolder.view.findViewById(R.id.row_header);
        headerView.setTextSize(25);
        headerView.setTextColor(Color.WHITE);
        headerView.setPadding(0, 0, 0, 0);

      //  viewHolder.view.setLayoutParams(new LinearLayout.LayoutParams(0,0));
    }

}
