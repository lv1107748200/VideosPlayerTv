package com.hr.videosplayertv.net.http;




import com.hr.videosplayertv.net.base.BaseService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by 吕 on 2017/10/26.
 */
@Module
public class HttpModule {
    protected static final String APP_CODE = "AppCode";
    protected static final String APP_CODE_VALUE = "ANDROID";

    //
    @Provides
    @Singleton
    public BaseService baseService(){
        return new BaseService();
    }
}
