package com.hr.videosplayertv.widget.single;


import com.hr.videosplayertv.net.entry.response.UserToken;

/**
 * Created by 吕 on 2018/2/6.
 */

public class UserInfoManger {

    volatile private static UserInfoManger instance = null;

    private String token;
    private UserToken UserToken;

    public static UserInfoManger getInstance(){
        if(instance == null){
            synchronized (UserInfoManger.class) {
                if(instance == null){
                    instance = new UserInfoManger();

                }
            }
        }

        return instance;
    }

    public UserToken getUserToken() {
        return UserToken;
    }

    public void setUserToken(UserToken userToken) {
        UserToken = userToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void clear(){
        token = null;
    }

}
