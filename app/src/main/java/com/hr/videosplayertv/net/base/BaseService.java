package com.hr.videosplayertv.net.base;





import com.hr.videosplayertv.base.BaseApplation;

import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpUtils;
import com.hr.videosplayertv.net.subscriber.HttpSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.ObservableTransformer;

/**
 * Created by 吕 on 2017/10/27.
 */

public class BaseService {

    public BaseService() {
        BaseApplation.getBaseApp().getAppComponent().inject(this);
    }


}
