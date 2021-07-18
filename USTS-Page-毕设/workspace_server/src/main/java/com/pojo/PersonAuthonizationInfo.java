package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/28 6:00
 */
@ApiModel("添加部门人员")
public class PersonAuthonizationInfo {
    @ApiModelProperty("人员唯一ID-必选")
    private String userCode;
    @ApiModelProperty("人员唯一ID-必选")
    private String departmentCode;
    @ApiModelProperty("人员角色D-必选")
    private int userRole;
    @ApiModelProperty("系统生成-可选")
    private String personnelCode;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }
}
