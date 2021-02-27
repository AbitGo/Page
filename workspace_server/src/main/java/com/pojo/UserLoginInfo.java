package com.pojo;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/28 1:45
 */
public class UserLoginInfo {
    //可以是登录名也可以是邮箱
    private String userLoginInfo;
    private String userPassword;

    public String getUserLoginInfo() {
        return userLoginInfo;
    }

    public void setUserLoginInfo(String userLoginInfo) {
        this.userLoginInfo = userLoginInfo;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
