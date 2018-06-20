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


import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewHolder;



public class GridAdapter extends CommonRecyclerViewAdapter<ListData> {
    public GridAdapter(Context context) {
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
}
