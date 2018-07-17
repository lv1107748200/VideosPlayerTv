package com.hr.videosplayertv.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.hr.videosplayertv.R;
import com.hr.videosplayertv.ui.adapter.viewholder.MainViewHolder;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.ColorUtils;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;

public class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

    private Context contextontext;

    private LayoutHelper mLayoutHelper;


    private VirtualLayoutManager.LayoutParams mLayoutParams;
    private int mCount = 0;


    public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
        this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
    }

    public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
        this.contextontext = context;
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mLayoutParams = layoutParams;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(contextontext).inflate(R.layout.item_home_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        // only vertical
        holder.itemView.setLayoutParams(
                new VirtualLayoutManager.LayoutParams(mLayoutParams));
    }


    @Override
    protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {

        ImageView imageView = holder.itemView.findViewById(R.id.image);
        TextView textView = holder.itemView.findViewById(R.id.title_sdfd);

        textView.setText(offsetTotal);

        if(position == 6){
            imageView.setVisibility(View.GONE);

        }if(position == 7){
            imageView.setVisibility(View.GONE);

        }else {

            imageView.setVisibility(View.VISIBLE);
            GlideUtil.setGlideImage(contextontext
                    , ImgDatasUtils.getUrl()
                    ,(ImageView) holder.itemView.findViewById(R.id.image),R.drawable.hehe);
        }


    }

    @Override
    public int getItemCount() {
        return mCount;
    }
}
