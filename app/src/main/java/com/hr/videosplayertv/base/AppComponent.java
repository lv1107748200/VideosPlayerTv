package com.hr.videosplayertv.base;





import com.hr.videosplayertv.net.base.BaseService;
import com.hr.videosplayertv.net.http.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 吕 on 2017/10/26.
 */
@Singleton
@Component(   modules = {
        AppModule.class,
        HttpModule.class
})
public interface AppComponent {
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);
    void inject(BaseService baseService);
}
