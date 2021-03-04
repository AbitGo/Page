package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/28 6:00
 */
@ApiModel("删除用户需要的参数")
public class PersonDelInfo {

    @ApiModelProperty("自己的用户唯一id-必选")
    private String userCodeSelf;
    @ApiModelProperty("需要删除的用户唯一id-必选")
    private String userCodeDel;
    @ApiModelProperty("部门的唯一id-必选")
    private String departmentCode;

    public String getUserCodeSelf() {
        return userCodeSelf;
    }

    public void setUserCodeSelf(String userCodeSelf) {
        this.userCodeSelf = userCodeSelf;
    }

    public String getUserCodeDel() {
        return userCodeDel;
    }

    public void setUserCodeDel(String userCodeDel) {
        this.userCodeDel = userCodeDel;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
}
