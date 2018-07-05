package com.hr.videosplayertv.net.Service;

import com.hr.videosplayertv.net.base.BaseDataResponse;
import com.hr.videosplayertv.net.base.BaseResponse;
import com.hr.videosplayertv.net.entry.response.WhatList;
import com.hr.videosplayertv.net.entry.response.WhatType;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ComService {

    //所有分类
    @GET("List/AllInOneType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> AllInOneType();
    //获取电影分类
    @GET("List/FilmType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> FilmType();
    //获取电视剧分类
    @GET("List/TVType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> TVType();
    //获取综艺分类
    @GET("List/VarietyType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> VarietyType();
    //获取动漫分类
    @GET("List/AnimeType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> AnimeType();
    //获取体育分类
    @GET("List/SportType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> SportType();
    //获取记录片分类
    @GET("List/DocumentaryType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> DocumentaryType();
    //获取华人圈分类
    @GET("List/CircleType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> CircleType();
    //获取游戏分类
    @GET("List/GameType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> GameType();

    //电影
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Film")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Film(@Body RequestBody route);

}
