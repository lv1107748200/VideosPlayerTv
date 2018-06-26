package com.hr.videosplayertv.ui.activity;

import android.view.KeyEvent;
import android.widget.Toast;

import com.hr.videosplayertv.R;
import com.hr.videosplayertv.base.BaseActivity;
import com.hr.videosplayertv.widget.layout.ControlPlayer;
import com.hr.videosplayertv.widget.single.IjkPlayerMger;

import butterknife.BindView;

/**
 * 播放页
 */
public class PlayerActivity extends BaseActivity {

    private boolean shortPress = false;
    private String url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private long firstTime=0;



    @BindView(R.id.ControlPlayer)
    ControlPlayer controlPlayer;
    @Override
    public int getLayout() {
        return R.layout.activity_player;
    }
    @Override
    public void init() {
        super.init();
        controlPlayer.setContext(this);
        controlPlayer.setVideoUrl(url);
        controlPlayer.initConPlay();//
    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键


                if(shortPress){
                    controlPlayer.stopSeepOrBackTask(false);
                }else {

                    controlPlayer.stopSeepOrBackTask(true);

                }

                shortPress = false;

                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键




                if(shortPress){
                    controlPlayer.stopSeepOrBackTask(false);
                }else {
                    controlPlayer.stopSeepOrBackTask(true);
                }
                shortPress = false;

                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //    NLog.d(NLog.TAGOther,"keyCode--->" + keyCode);

        switch (keyCode) {

            case KeyEvent.KEYCODE_ENTER:     //确定键enter
            case KeyEvent.KEYCODE_DPAD_CENTER:

                controlPlayer.playOrstopOrRe();//遥控确定键


                break;

            case KeyEvent.KEYCODE_BACK:    //返回键

                if(event.getAction()==KeyEvent.ACTION_DOWN){
                    if (System.currentTimeMillis()-firstTime>2000){
                        Toast.makeText(PlayerActivity.this,"再按一次退出播放器",Toast.LENGTH_SHORT).show();
                        firstTime=System.currentTimeMillis();
                    }else{
                        finish();
                    }
                    return true;
                }

                break;
            case KeyEvent.KEYCODE_MENU:
                //    controlPlayer.onClickKey(KeyEvent.KEYCODE_MENU);

                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键

                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
                 *    exp:KeyEvent.ACTION_UP
                 */
                if (event.getAction() == KeyEvent.ACTION_DOWN){

                    //   controlPlayer.onClickKey(KeyEvent.ACTION_DOWN);

                }

                break;

            case KeyEvent.KEYCODE_DPAD_UP:   //向上键
                //  NLog.d(NLog.TAGOther,"up--->");

                break;

            case     KeyEvent.KEYCODE_0:   //数字键0

                //  finish();

                break;
            case     KeyEvent.KEYCODE_1:   //数字键0
                // controlPlayer.playOrstopOrRe();//遥控确定键

                break;
            case     KeyEvent.KEYCODE_2:   //数字键0
                //  controlPlayer.onClickKey(KeyEvent.KEYCODE_MENU);

                break;

            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键



                if(event.getRepeatCount() == 0){
                    event.startTracking();
                    shortPress = true;
                    controlPlayer.backKey(false,event.getRepeatCount());
                }else {

                    shortPress = false;
                    controlPlayer.backKey(true,event.getRepeatCount());
                    return true;
                }


                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键


                if(event.getRepeatCount() == 0){

                    event.startTracking();
                    shortPress = true;
                    controlPlayer.seepKey(false,event.getRepeatCount());

                }else {

                    shortPress = false;
                    controlPlayer.seepKey(true,event.getRepeatCount());
                    return true;

                }

                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        controlPlayer.destroy();

        IjkPlayerMger.getInstance().setOnDesry();

    }

}
