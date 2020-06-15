/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.hr.videosplayertv.widget.presenter;

import android.support.v17.leanback.transition.TransitionHelper;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ItemBridgeAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.RowPresenter;
import android.support.v17.leanback.widget.VerticalGridPresenter;
import android.support.v17.leanback.widget.VerticalGridView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.ui.adapter.SubAdapter;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.NLog;

import java.util.List;


/**
 */
public class InvisibleRowPresenter extends RowPresenter {

    public InvisibleRowPresenter() {
//        setHeaderPresenter(null); // 屏蔽掉头.
    }

    /**
     * 不得不说，android 的 Leanback 确实很灵活.
     * 这里可以换成其它布局的，不一定是 LinerLayout.
     */
    @Override
    protected ViewHolder createRowViewHolder(ViewGroup parent) {
        LinearLayout root = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_head,
                null, false);
        int wight = DisplayUtils.getWide(1,DisplayUtils.getDimen(R.dimen.x20)
                ,DisplayUtils.getDimen(R.dimen.x40));

        root.setLayoutParams(new ViewGroup.LayoutParams(wight
                , DisplayUtils.getDimen(R.dimen.x550)));
        return new ViewHolder(root);
    }
    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);



    }

    @Override
    protected void onUnbindRowViewHolder(ViewHolder holder) {
        super.onUnbindRowViewHolder(holder);
    }

}
