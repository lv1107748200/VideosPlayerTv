/*
 * Copyright (C) 2014 Lucas Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hr.videosplayertv.ui.adapter;

import android.content.Context;

import com.hr.videosplayertv.R;


import com.hr.videosplayertv.db.PHData;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.net.entry.response.Result;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.videosplayertv.utils.ColorUtils;
import com.hr.videosplayertv.utils.Formatter;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.hr.videosplayertv.utils.UrlUtils;


public class GridAdapter extends CommonRecyclerViewAdapter {

    public static final int CLASSIFYLAYOUT =110;
    public static final int FAVORITELAYOUT =111;//收藏夹
    public static final int PLAYERRECORDLAYOUT =112;//播放记录


    private int  type;


    public GridAdapter(Context context) {
        super(context);
    }

    public GridAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    @Override
    public int getItemLayoutId(int viewType) {

        if(CLASSIFYLAYOUT == type){
            return R.layout.item_list_classify_data;
        }else if(FAVORITELAYOUT == type){
            return R.layout.item_list_favorite_data;
        }else if(PLAYERRECORDLAYOUT == type){
            return R.layout.item_list_favorite_data;
        }
        return R.layout.item_list_data;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {

        if(CLASSIFYLAYOUT == type){
            if(item instanceof ListData){
                helper.getHolder().setText(R.id.title,((ListData) item).getTitle());
                helper.getHolder().getView(R.id.image).setBackgroundResource(((ListData) item).getBack());
            }

        }else if(FAVORITELAYOUT == type){
            if(item instanceof Result)
                helper.getHolder()
                        .showImage(R.id.image, UrlUtils.getUrl(((Result) item).getImgPath()))
                        .setText(R.id.title,((Result) item).getTitle());

        }else if(PLAYERRECORDLAYOUT == type) {
            if(item instanceof PHData)
                helper.getHolder()
                        .showImage(R.id.image, UrlUtils.getUrl(((PHData) item).getImgPath()))
                        .setText(R.id.title,((PHData) item).getTitle()
                                + "\n" + ((PHData) item).getName()
                                + "\n" + Formatter.formatTime((int) ((PHData) item).getPress()));

        } else{
            if(item instanceof WhatList){
                helper.getHolder()
                        .showImage(R.id.image, UrlUtils.getUrl(((WhatList) item).getImgPath()))
                        .setText(R.id.title,((WhatList) item).getTitle());
            }else {
                helper.getHolder()
                        .showImage(R.id.image, ImgDatasUtils.getUrl());
            }
        }
    }

    //左右移动删除的方法
    public void Swiped(int adapterPosition) {
        getmDatas().remove(adapterPosition);
        this.notifyItemRemoved(adapterPosition);
    }
}
