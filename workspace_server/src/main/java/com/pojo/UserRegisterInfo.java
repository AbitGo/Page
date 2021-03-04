package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/27 14:13
 */
@ApiModel("用户注册需要使用的参数")
public class UserRegisterInfo {
    @ApiModelProperty("用户名-必须")
    private String userName;
    @ApiModelProperty("用户密码-必须")
    private String userPassword;
    @ApiModelProperty("用户登陆迷名-必须")
    private String userLoginName;
    @ApiModelProperty("不需要")
    private String userCode;
    @ApiModelProperty("用户邮箱-必须")
    private String userEmail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
