package com.hr.videosplayertv.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.hr.videosplayertv.R;
import com.hr.videosplayertv.common.Iddddd;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.ui.activity.DetailActivity;
import com.hr.videosplayertv.ui.adapter.base.CommonBaseAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewHolder;
import com.hr.videosplayertv.ui.adapter.base.CommonViewHolder;
import com.hr.videosplayertv.ui.adapter.viewholder.MainViewHolder;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.ColorUtils;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.utils.UrlUtils;

import java.util.List;

public class SubAdapter extends CommonRecyclerViewAdapter {

    private Context contextontext;

    public SubAdapter(Context context) {
        super(context);
    }

    public SubAdapter(Context context, List mDatas) {
        super(context, mDatas);
        this.contextontext = context;
    }
    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_home_grid_stragg;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {
        if(item instanceof WhatList){
            ImageView imageView = helper.itemView.findViewById(R.id.image_grid_stragg);
            TextView textView = helper.itemView.findViewById(R.id.title_grid_stragg);

            textView.setText(((WhatList) item).getTitle());
            GlideUtil.setGlideImage(contextontext
                    , UrlUtils.getUrl(((WhatList) item).getImgPath())
                    ,imageView,R.drawable.hehe);
        }else {
            NLog.e(NLog.TAGOther," 数据类型错误 --->");
        }
    }
}
