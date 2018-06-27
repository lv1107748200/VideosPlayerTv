package com.hr.videosplayertv.ui.adapter;

import android.content.Context;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewAdapter;
import com.hr.videosplayertv.ui.adapter.base.CommonRecyclerViewHolder;

import java.util.List;

/**
 * Created by 吕 on 2018/3/13.
 */

public class ListDataMenuAdapter extends CommonRecyclerViewAdapter {

    private boolean isMainMenu;

    public ListDataMenuAdapter(Context context) {
        super(context);
    }
    public ListDataMenuAdapter(Context context,boolean isMainMenu) {
        super(context);
        this.isMainMenu = isMainMenu;
    }
    @Override
    public int getItemLayoutId(int viewType) {
        return  isMainMenu ? R.layout.item_main_menu:R.layout.item_list_data_menu;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {

        if(item instanceof ListData){
            helper.getHolder().setText(R.id.title, ((ListData) item).getTitle()+"title");
        }else {
            helper.getHolder().setText(R.id.title, item.toString());
        }

    }
}
