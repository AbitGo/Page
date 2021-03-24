package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/3/23 21:36
 */
@ApiModel("用户修改密码需要使用的参数")
public class UserUpdatePwdInfo {
    @ApiModelProperty("验证码-必须")
    private String vCode;
    @ApiModelProperty("新密码-必须")
    private String userPassword;
    @ApiModelProperty("邮箱-必须")
    private String userEmail;

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
