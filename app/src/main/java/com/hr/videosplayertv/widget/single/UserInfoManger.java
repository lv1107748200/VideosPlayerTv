package com.hr.videosplayertv.widget.single;


/**
 * Created by 吕 on 2018/2/6.
 */

public class UserInfoManger {

    volatile private static UserInfoManger instance = null;

    private String token;


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
