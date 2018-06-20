package com.hr.videosplayertv.net.http;



public interface HttpCallback<T>{

    void onError(HttpException e);

    void onSuccess(T t);
}
