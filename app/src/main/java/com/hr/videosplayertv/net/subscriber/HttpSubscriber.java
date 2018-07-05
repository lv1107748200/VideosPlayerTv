package com.hr.videosplayertv.net.subscriber;


import com.hr.videosplayertv.net.base.BaseResponse;
import com.hr.videosplayertv.net.http.HttpCallback;
import com.hr.videosplayertv.net.http.HttpException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;


/*
 */
public class HttpSubscriber<T> implements Observer<Response<BaseResponse<T>>> {
    HttpCallback callback;

    public HttpSubscriber(HttpCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
      //  NToast.shortToastBaseApp("访问失败!");
        if (callback != null) {
            callback.onError(new HttpException(-100,e.getMessage()));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onNext(Response<BaseResponse<T>> httpResultResponse) {
        if(httpResultResponse.code()==200){
           BaseResponse<T> result = httpResultResponse.body();
            if(result.getRet() == 200 ){

                if (callback != null) {
                    callback.onSuccess(result);
                }

            } else {
                    if (callback != null) {
                         callback.onError(new HttpException(result.getRet(),result.getMsg()));
                    }
            }
        } else {
            if (callback != null) {
                callback.onError(new HttpException(httpResultResponse.code(),httpResultResponse.message()));
            }
        }
    }



}
