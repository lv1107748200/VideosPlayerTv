package com.hr.videosplayertv.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.CommentAdapter;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.GlideUtil;
import com.hr.videosplayertv.utils.ImgDatasUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.utils.SpanUtils;
import com.hr.videosplayertv.widget.focus.FocusBorder;
import com.hr.videosplayertv.widget.tablayout.TabLayout;
import com.hr.videosplayertv.widget.tablayout.TvTabLayout;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter.FOUR;
import static com.hr.videosplayertv.ui.adapter.ListDataMenuAdapter.THREE;

/**
 * 详情页
 */
public class DetailActivity extends BaseActivity {


    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;

    @BindView(R.id.select_collect)
    TvRecyclerView selectCollect;

    @BindView(R.id.com_layout)
    LinearLayout comLayout;
    @BindView(R.id.layout_select)
    RelativeLayout layoutSelect;

    @BindView(R.id.tab_layout)
    TvTabLayout tabLayout;

    @BindView(R.id.image_poster)
    ImageView imagePoster;

    @BindView(R.id.tv_video_introduction)
    TextView tv_video_introduction;
    @BindView(R.id.tv_data)
    TextView tv_data;

    private ListDataMenuAdapter listDataMenuAdapter;
    private CommentAdapter CommentAdapter;

    private BottomSheetBehavior behavior;

    private boolean isSild = false;//判断是否已滑动


    @OnClick({R.id.btn_player,R.id.btn_collect,R.id.btn_like,R.id.btn_stamp,R.id.image_poster})
    public void Onclick(View view){
        switch (view.getId()){
            case R.id.btn_player:
                if(true){
                    Intent intent = new Intent();
                    intent.setClass(this,PlayerActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.btn_collect:

                break;
            case R.id.btn_like:

                break;
            case R.id.btn_stamp:

                break;
            case R.id.image_poster:
                if(true){
                    Intent intent = new Intent();
                    intent.setClass(this,PlayerActivity.class);
                    startActivity(intent);
                }
                break;

        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void init() {
        super.init();
        tvTitleChild.setText(getString(R.string.svp_details));
        layoutSelect.setSelected(true);

        SpanUtils spanUtils = new SpanUtils();

        tv_video_introduction.setText(
                spanUtils.append("亮剑")
                        .setFontSize(DisplayUtils.sp2px(32))
                        .appendSpace(200)
                        .append("评分：4.8")
                        .setFontSize(DisplayUtils.sp2px(18))
                        .appendLine()
                        .appendLine("更新至45集，周一至周五每天更新两集")
                        .setFontSize(DisplayUtils.sp2px(16))
                        .appendLine()
                        .appendLine("主演：李幼斌、何政军")
                        .appendLine("导演：何政军")
                        .appendLine("简介：")
                        .appendLine("李云龙是八路军独立团的团长，在他的独特指挥下，山崎大队全部消灭。李云龙又会同国军团长楚云飞闯进日军重兵把守的县城，守备部队全军覆灭，李云龙和楚云飞在晋西北因此名声大噪，李楚成为好友")
                        .create()
        );

        spanUtils.setMText();

        tv_data.setText("收藏：209     点赞：443     踩：21");


        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                if(null != newFocus){
                    if(newFocus.getId() == R.id.tab_layout){
                        return FocusBorder.OptionsFactory.get(1.0f, 1.0f, 0);
                    }
                }

                return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0); //返回null表示不使用焦点框框架
            }
        });

        //setBottomSheetBehavior();


        setListener();
        CommentAdapter = new CommentAdapter(this);
        tvList.setSpacingWithMargins(DisplayUtils.dip2px(10), 0);
        tvList.setAdapter(CommentAdapter);

        selectCollect.setSpacingWithMargins(DisplayUtils.dip2px(5), DisplayUtils.dip2px(15));
        listDataMenuAdapter = new ListDataMenuAdapter(this,FOUR);
        selectCollect.setAdapter(listDataMenuAdapter);

        initData();
        setTab();

        GlideUtil.setGlideImage(this, ImgDatasUtils.getUrl(),imagePoster);

    }

    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 37; i++){
            listData.add(new ListData());
        }


        CommentAdapter.repaceDatas(listData);
    }

    private void setTab(){

      final  List<String> stringList = new ArrayList<>();

        for(int i=0; i<50; i++){
            stringList.add(""+(i+1));
        }

      //  NLog.e(NLog.TAGOther,"分割大小---》"+splitList(stringList,20).size());

        tabLayout.setScaleValue(1.1f);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                listDataMenuAdapter.setSelectView(null);
                listDataMenuAdapter.repaceDatas(splitList(stringList,20).get(tab.getPosition()));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.addTab(
                tabLayout.newTab()
                        .setText("1-20")
                , true);
        tabLayout.addTab(
                tabLayout.newTab()
                        .setText("21-40")
        );
        tabLayout.addTab(
                tabLayout.newTab()
                        .setText("40-50")
        );
    }

    private void setListener() {

        selectCollect.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {

                if(null != listDataMenuAdapter.getSelectView()){
                    listDataMenuAdapter.getSelectView().setActivated(true);
                }
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

                listDataMenuAdapter.setSelectData(listDataMenuAdapter.getItem(position).toString());
                listDataMenuAdapter.setSelectView(itemView);

            }

        });

        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.01f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {

            }
        });

        selectCollect.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(tvList.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });

        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                sildAnimina();
                if(selectCollect.hasFocus() && !hasFocus)
                    return;
                mFocusBorder.setVisible(hasFocus);
            }
        });


        //边界监听
//        mRecyclerView.setOnInBorderKeyEventListener(new TvRecyclerView.OnInBorderKeyEventListener() {
//            @Override
//            public boolean onInBorderKeyEvent(int direction, int keyCode, KeyEvent event) {
//                Log.i("zzzz", "onInBorderKeyEvent: ");
//                return false;//需要拦截返回true,否则返回false
//            }
//        });

        /*mRecyclerView.setOnLoadMoreListener(new TvRecyclerView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                Log.i("@@@@", "onLoadMore: ");
                mRecyclerView.setLoadingMore(true); //正在加载数据
                mLayoutAdapter.appendDatas(); //加载数据
                mRecyclerView.setLoadingMore(false); //加载数据完毕
                return false; //是否还有更多数据
            }
        });*/
    }

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @return
     */
    private   List<List<?>> splitList(List<?> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }

        List<List<?>> result = new ArrayList<List<?>>();


        int size = list.size();
        int count = (size + len - 1) / len;


        for (int i = 0; i < count; i++) {
            List<?> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }


    //初始化滚动
    private void setBottomSheetBehavior(){
        View bottomSheet = findViewById(R.id.com_layout);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(DisplayUtils.getScreenHeight(this)-DisplayUtils.dip2px(300)-DisplayUtils.getStatusBarHeight(this));

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(View bottomSheet, int newState) {
                String state = "null";
                switch (newState) {
                    case 1:
                        state = "STATE_DRAGGING";
                        break;
                    case 2:
                        state = "STATE_SETTLING";
                        break;
                    case 3:
                        state = "STATE_EXPANDED";
                        break;
                    case 4:
                        state = "STATE_COLLAPSED";
                        break;
                    case 5:
                        state = "STATE_HIDDEN";
                        break;
                }
                Log.d("MainActivity", "newState:" + state);
            }
            @Override
            public void onSlide(View bottomSheet, float slideOffset) {
//                Log.d("MainActivity", "slideOffset:" + slideOffset);
            }
        });
    }

    private void sildAnimina(){

      final  float distance = DisplayUtils.getDimen(R.dimen.x400);


       // NLog.e(NLog.TAGOther,"获取评论的测量高度---》"+layoutSelect.getMeasuredHeight());

     final   int hight = layoutSelect.getMeasuredHeight();
     final   LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layoutSelect.getLayoutParams();

        if(isSild){
            isSild = !isSild;
            //ObjectAnimator translationX = new ObjectAnimator().ofFloat(layoutSelect,"translationX",0,0);
            ObjectAnimator translationY = new ObjectAnimator().ofFloat(layoutSelect,"translationY",-distance,0);

            AnimatorSet animatorSet = new AnimatorSet();  //组合动画
            animatorSet.playTogether(translationY); //设置动画
            animatorSet.setDuration(200);  //设置动画时间
            translationY.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                   // NLog.e(NLog.TAGOther,"平移动画结束---》");
                    layoutParams.height = MATCH_PARENT;
                    layoutSelect.setLayoutParams(layoutParams);
                    layoutSelect.setSelected(true);
                }
            });
            animatorSet.start(); //启动
        }else {
            isSild = !isSild;
           // ObjectAnimator translationX = new ObjectAnimator().ofFloat(layoutSelect,"translationX",0,0);
            ObjectAnimator translationY = new ObjectAnimator().ofFloat(layoutSelect,"translationY",0,-distance);

            AnimatorSet animatorSet = new AnimatorSet();  //组合动画
            animatorSet.playTogether(translationY); //设置动画
            animatorSet.setDuration(200);  //设置动画时间
            animatorSet.start(); //启动
            layoutSelect.setSelected(false);
            layoutParams.height = (int) (hight + distance);
            layoutSelect.setLayoutParams(layoutParams);
        }

    }
}
