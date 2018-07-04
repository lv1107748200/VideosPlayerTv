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

    private int isMainMenu;

    public final static int ONE = 1111;
    public final static int TWO = 1112;
    public final static int THREE = 1113;

    public ListDataMenuAdapter(Context context,int isMainMenu) {
        super(context);
        this.isMainMenu = isMainMenu;
    }
    @Override
    public int getItemLayoutId(int viewType) {

        switch (isMainMenu){
            case ONE:
                return R.layout.item_main_menu;
            case TWO:
                return R.layout.item_classify_menu;
            case THREE:
                return R.layout.item_list_data_menu;
        }
        return R.layout.item_main_menu;
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
