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

    //1 所有分类
    @GET("List/AllInOneType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> AllInOneType();
    //2 获取电影分类
    @GET("List/FilmType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> FilmType();
    //3 获取电视剧分类
    @GET("List/TVType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> TVType();
    //4 获取综艺分类
    @GET("List/VarietyType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> VarietyType();
    //5 获取动漫分类
    @GET("List/AnimeType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> AnimeType();
    //6 获取体育分类
    @GET("List/SportType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> SportType();
    //7 获取记录片分类
    @GET("List/DocumentaryType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> DocumentaryType();
    //8 获取华人圈分类
    @GET("List/CircleType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> CircleType();
    //9 获取游戏分类
    @GET("List/GameType")
    Observable<Response<BaseResponse<BaseDataResponse<WhatType>>>> GameType();

    //10 电影
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Film")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Film(@Body RequestBody route);
    //11 电视剧
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/TV")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    TV(@Body RequestBody route);
    //12 综艺
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Variety")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Variety(@Body RequestBody route);
    //13 动漫
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Anime")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Anime(@Body RequestBody route);
    //14 体育
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Sport")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Sport(@Body RequestBody route);
    //15 记录片
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Documentary")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Documentary(@Body RequestBody route);
    //16 华人
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Circle")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Circle(@Body RequestBody route);
    //17 游戏
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("List/Game")
    Observable<Response<BaseResponse<BaseDataResponse<WhatList>>>>
    Game(@Body RequestBody route);

}
