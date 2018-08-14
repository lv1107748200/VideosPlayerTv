package com.hr.videosplayertv.widget.presenter;


import android.content.Context;
import android.support.v17.leanback.widget.VerticalGridView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.hr.videosplayertv.R;

/*
 * lv   2018/8/13
 */
public class VGRowView  extends LinearLayout {


    private VerticalGridView mGridView;

    public VGRowView(Context context) {
        this(context, null);
    }

    public VGRowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VGRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.item_row_vg_one, this);

        mGridView =  findViewById(R.id.row_vg);
        // since we use WRAP_CONTENT for height in lb_list_row, we need set fixed size to false
        mGridView.setHasFixedSize(false);

        // Uncomment this to experiment with page-based scrolling.
        // mGridView.setFocusScrollStrategy(HorizontalGridView.FOCUS_SCROLL_PAGE);

        setOrientation(LinearLayout.VERTICAL);
        setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
    }

    /**
     * Returns the HorizontalGridView.
     */
    public VerticalGridView getGridView() {
        return mGridView;
    }
}
