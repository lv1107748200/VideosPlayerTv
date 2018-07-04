package com.hr.videosplayertv.ui.adapter;

import android.content.Context;
import android.view.View;

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
    public final static int FOUR = 1114;
    public boolean isHead = false;
    private String selectData;
    private View selectView;

    private int layoutId;

    public ListDataMenuAdapter(Context context,int isMainMenu) {
        super(context);
        this.isMainMenu = isMainMenu;
        switch (isMainMenu){
            case ONE:
                layoutId = R.layout.item_main_menu;
                break;
            case TWO:
                layoutId = R.layout.item_classify_menu;
                break;
            case THREE:
                layoutId = R.layout.item_list_data_menu;
                break;
            case FOUR:
                layoutId = R.layout.item_select_menu;
                break;
        }
    }

    public ListDataMenuAdapter(Context context,int isMainMenu,boolean isHead) {
        super(context);
        this.isMainMenu = isMainMenu;
        this.isHead = isHead;
        switch (isMainMenu){
            case ONE:
                layoutId = R.layout.item_main_menu;
                break;
            case TWO:
                layoutId = R.layout.item_classify_menu;
                break;
            case THREE:
                layoutId = R.layout.item_list_data_menu;
                break;
            case FOUR:
                layoutId = R.layout.item_select_menu;
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            if(isHead)
            return 123;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        if(viewType == 123){
            return R.layout.item_head_list_data;
        }
        return layoutId;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, Object item, int position) {

        if(isHead){//列表搜索页

            if(position == 0){

            }else {
                helper.getHolder().setText(R.id.title, ((ListData) item).getTitle()+"title");
            }

        }else if(isMainMenu == FOUR){//选集
            helper.getHolder().setText(R.id.title, item.toString());
            if(item.toString().equals(selectData)){
                helper.itemView.setActivated(true);
                selectView = helper.itemView;
            }else {
                helper.itemView.setActivated(false);
            }

        } else {
            helper.getHolder().setText(R.id.title, ((ListData) item).getTitle()+"title");
        }



    }

    public void setSelectData(String data){
        selectData = data;
    }

    public View getSelectView() {
        return selectView;
    }

    public void setSelectView(View selectView) {
        if(this.selectView != null){
            this.selectView.setActivated(false);
        }
        this.selectView = selectView;
    }

    public String getSelectData() {
        return selectData;
    }
}
