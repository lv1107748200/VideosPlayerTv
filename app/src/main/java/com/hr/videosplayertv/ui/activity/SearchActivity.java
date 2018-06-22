package com.hr.videosplayertv.ui.activity;

import android.graphics.RectF;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.utils.DisplayUtils;
import com.hr.videosplayertv.widget.keyboard.SkbContainer;
import com.hr.videosplayertv.widget.keyboard.SoftKey;
import com.hr.videosplayertv.widget.keyboard.SoftKeyBoardListener;

import butterknife.BindView;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.skbContainer)
    SkbContainer skbContainer;

    @Override
    public int getLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        super.init();

        skbContainer = (SkbContainer) findViewById(R.id.skbContainer);
        skbContainer.setSkbLayout(R.xml.sbd_qwerty);
        skbContainer.setFocusable(true);
        skbContainer.setFocusableInTouchMode(true);
        // 设置属性(默认是不移动的选中边框)
        setSkbContainerMove();
        //
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框最前面.
        // 监听键盘事件.
        skbContainer.setOnSoftKeyBoardListener(new SoftKeyBoardListener() {
            @Override
            public void onCommitText(SoftKey softKey) {
                if ((skbContainer.getSkbLayoutId() == R.xml.skb_t9_keys)) {
                    onCommitT9Text(softKey);
                } else {
                    int keyCode = softKey.getKeyCode();
                    String keyLabel = softKey.getKeyLabel();
                    if (!TextUtils.isEmpty(keyLabel)) { // 输入文字.

                    } else { // 自定义按键，这些都是你自己在XML中设置的keycode.
                        keyCode = softKey.getKeyCode();
                        if (keyCode == KeyEvent.KEYCODE_DEL) {


                        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                            finish();
                        } else if (keyCode == 66) {
                            Toast.makeText(getApplicationContext(), "回车", Toast.LENGTH_LONG).show();
                        } else if (keyCode == 250) { //切换键盘
                            // 这里只是测试，你可以写自己其它的数字键盘或者其它键盘
                            setSkbContainerOther();
                            skbContainer.setSkbLayout(R.xml.sbd_number);
                        }
                    }
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
        // 英文键盘切换测试.
        findViewById(R.id.en_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkbContainerMove();
                skbContainer.setSkbLayout(R.xml.sbd_qwerty);
            }
        });
        // 数字键盘切换测试.
        findViewById(R.id.num_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkbContainerOther();
                skbContainer.setSkbLayout(R.xml.sbd_number);
            }
        });
        // 全键盘切换测试.
        findViewById(R.id.all_key_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkbContainerOther();
                skbContainer.setSkbLayout(R.xml.skb_all_key);
            }
        });
        findViewById(R.id.t9_key_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkbContainerMove();
                skbContainer.setSkbLayout(R.xml.skb_t9_keys);
            }
        });

    }


    private void setSkbContainerMove() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(true); // 设置是否移动按键边框.
        RectF rectf = new RectF(
                DisplayUtils.dip2px(15),
                DisplayUtils.dip2px(15),
                DisplayUtils.dip2px(15),
                DisplayUtils.dip2px(15)
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


    /**
     * 处理T9键盘的按键.
     * @param softKey
     */
    private void onCommitT9Text(SoftKey softKey) {
    }

}
