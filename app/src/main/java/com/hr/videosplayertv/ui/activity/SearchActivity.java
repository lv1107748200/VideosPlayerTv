package com.hr.videosplayertv.ui.activity;

import android.content.Intent;
import android.graphics.RectF;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.net.entry.ListData;
import com.hr.videosplayertv.ui.adapter.GridAdapter;
import com.hr.videosplayertv.ui.fragment.MultipleFragment;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.utils.NLog;
import com.hr.videosplayertv.widget.keyboard.SkbContainer;
import com.hr.videosplayertv.widget.keyboard.SoftKey;
import com.hr.videosplayertv.widget.keyboard.SoftKeyBoardListener;
import com.owen.tvrecyclerview.widget.SimpleOnItemListener;
import com.owen.tvrecyclerview.widget.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.tv_show_message)
    TextView tv_show_message;
    @BindView(R.id.skbContainer)
    SkbContainer skbContainer;
    @BindView(R.id.tv_list)
    TvRecyclerView tvList;

    private GridAdapter gridAdapter;


    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        super.init();

        skbContainer = findViewById(R.id.skbContainer);
        skbContainer.setSkbLayout(R.xml.skb_all_key);
        skbContainer.setFocusable(true);
        skbContainer.setFocusableInTouchMode(true);
        // 设置属性(默认是不移动的选中边框)
       // setSkbContainerMove();
        setSkbContainerOther();
        //
       // skbContainer.setSelectSofkKeyFront(true); // 设置选中边框最前面.
        // 监听键盘事件.
        skbContainer.setOnSoftKeyBoardListener(new SoftKeyBoardListener() {
            @Override
            public void onCommitText(SoftKey softKey) {
                NLog.e(NLog.KEY,"获取view 坐标   :  "+skbContainer.getX());


                int keyCode = softKey.getKeyCode();
                switch (keyCode){
                    case 67:
                        break;
                    case 68:
                        tv_show_message.setText("");
                        break;
                    case 69:
                        setSkbContainerOther();
                        skbContainer.setSkbLayout(R.xml.skb_all_key);
                        break;
                    case 70:
                        setSkbContainerOther();
                        skbContainer.setSkbLayout(R.xml.skb_t9_keys);
                        break;
                    default:
                        if ((skbContainer.getSkbLayoutId() == R.xml.skb_t9_keys)) {
                            onCommitT9Text(softKey);
                        } else {

                            NLog.e(NLog.KEY,"--->keycode   :  "+keyCode);

                            String keyLabel = softKey.getKeyLabel();
                            tv_show_message.setText(tv_show_message.getText()+keyLabel);

//                    int keyCode = softKey.getKeyCode();
//                    String keyLabel = softKey.getKeyLabel();
//                    if (!TextUtils.isEmpty(keyLabel)) { // 输入文字.
//
//                    } else { // 自定义按键，这些都是你自己在XML中设置的keycode.
//                        keyCode = softKey.getKeyCode();
//                        if (keyCode == KeyEvent.KEYCODE_DEL) {
//
//
//                        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
//                            finish();
//                        } else if (keyCode == 66) {
//                            Toast.makeText(getApplicationContext(), "回车", Toast.LENGTH_LONG).show();
//                        } else if (keyCode == 250) { //切换键盘
//                            // 这里只是测试，你可以写自己其它的数字键盘或者其它键盘
//                            setSkbContainerOther();
//                            skbContainer.setSkbLayout(R.xml.sbd_number);
//                        }
//                    }
                        }
                        break;
                }


            }

            @Override
            public void onBack(SoftKey key) {
               finish();
            }

            @Override
            public void onDelete(SoftKey key) {

            }

        });
        // DEMO（测试键盘失去焦点和获取焦点)
        skbContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mOldSoftKey != null)
                        skbContainer.setKeySelected(mOldSoftKey);
                    else
                        skbContainer.setDefualtSelectKey(0, 0);
                } else {
                    mOldSoftKey = skbContainer.getSelectKey();
                    skbContainer.setKeySelected(null);
                }
            }
        });


        setListener();
        tvList.setSpacingWithMargins(DisplayUtils.getDimen(R.dimen.x22), DisplayUtils.getDimen(R.dimen.x22));
        gridAdapter = new GridAdapter(this);
        tvList.setAdapter(gridAdapter);

        initData();
    }

    private void initData(){
        List<ListData> listData = new ArrayList<>();

        for (int i =0 ;i< 37; i++){
            listData.add(new ListData());
        }

        gridAdapter.repaceDatas(listData);
    }

    private void setListener() {


        tvList.setOnItemListener(new SimpleOnItemListener() {

            @Override
            public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
                onMoveFocusBorder(itemView, 1.1f, DisplayUtils.dip2px(3));
            }

            @Override
            public void onItemClick(TvRecyclerView parent, View itemView, int position) {
                Intent intent = new Intent(SearchActivity.this,ListDataActivity.class);

                startActivity(intent);
            }
        });


        tvList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
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
     * 处理T9键盘的按键.
     * @param softKey
     */
    private void onCommitT9Text(SoftKey softKey) {

    }
    private void setSkbContainerMove() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(true); // 设置是否移动按键边框.

        int sise = DisplayUtils.getDimen(R.dimen.x20);

        RectF rectf = new RectF(
                sise,
                sise,
                sise,
                sise
        );
        skbContainer.setSoftKeySelectPadding(rectf); // 设置移动边框相差的间距.
        skbContainer.setMoveDuration(200); // 设置移动边框的时间(默认:300)
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框在最前面.
    }

    /**
     * 切换布局测试.
     * 因为布局不相同，所以属性不一样，
     * 需要重新设置(不用参考我的,只是DEMO)
     */
    private void setSkbContainerOther() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(false);
        skbContainer.setSoftKeySelectPadding(0);
        skbContainer.setSelectSofkKeyFront(false);
    }

    SoftKey mOldSoftKey;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyDown(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyUp(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }


}
