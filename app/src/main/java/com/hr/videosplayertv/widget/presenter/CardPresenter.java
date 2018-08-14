package com.hr.videosplayertv.widget.presenter;

import android.support.v17.leanback.widget.Presenter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.utils.DisplayUtils;


/**
 *
 */
public class CardPresenter extends Presenter {


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {

        int wight = DisplayUtils.getWide(6,DisplayUtils.getDimen(R.dimen.x20)
                ,DisplayUtils.getDimen(R.dimen.x40));


        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_grid_stragg,
                null, false);

        root.setLayoutParams(new ViewGroup.LayoutParams(wight,wight/3*4));

        return new ViewHolder(root);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        if(item instanceof WhatList){
         ImageView imageView = viewHolder.view.findViewById(R.id.image_grid_stragg);
         TextView textView = viewHolder.view.findViewById(R.id.title_grid_stragg);

         textView.setText(((WhatList) item).getTitle());
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }


}
