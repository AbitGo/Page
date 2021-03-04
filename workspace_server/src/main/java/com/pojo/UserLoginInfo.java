package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/28 1:45
 */
@ApiModel(value = "用户登录所需要的参数-必选")
public class UserLoginInfo {
    //可以是登录名也可以是邮箱
    @ApiModelProperty(("用户登录信息登录名/邮箱-必选"))
    private String userLoginInfo;
    @ApiModelProperty(("用户登录密码-必选"))
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
