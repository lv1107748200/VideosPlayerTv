package com.hr.videosplayertv.net.base;





import com.hr.videosplayertv.base.BaseApplation;

import com.hr.videosplayertv.net.Service.ComService;
import com.hr.videosplayertv.net.Service.UserService;
import com.hr.videosplayertv.net.entry.request.AutoLogin;
import com.hr.videosplayertv.net.entry.response.InfoToken;
import com.hr.videosplayertv.net.entry.response.UserInfo;
import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpUtils;
import com.hr.videosplayertv.net.subscriber.HttpSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.RequestBody;

/**
 * Created by 吕 on 2017/10/27.
 */

public class BaseService {

    @Inject
    public UserService userService;
    @Inject
    public ComService comService;

    public BaseService() {
        BaseApplation.getBaseApp().getAppComponent().inject(this);
    }

    //根据栏目id查询所有的文章或视频
    @SuppressWarnings("unchecked")
    public void userAutoLogin(
            AutoLogin data,
            HttpCallback<BaseResponse<InfoToken>> httpCallback){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  userService.userAutoLogin(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<InfoToken>(httpCallback)
        );
    }



    ////获取用户信息，传入登录时获取的token，及影院id（固定为1）
    @SuppressWarnings("unchecked")
    public void validate(
            BaseDataRequest data,
            HttpCallback<BaseResponse<BaseDataResponse<UserInfo>>> httpCallback){

        RequestBody body = HttpUtils.buildRequestBody(data);
        Observable observable =  userService.validate(body);

        HttpUtils.toSubscribe(
                observable,
                new HttpSubscriber<BaseDataResponse<UserInfo>>(httpCallback)
        );
    }

}
