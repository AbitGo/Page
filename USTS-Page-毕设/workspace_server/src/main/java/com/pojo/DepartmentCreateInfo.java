package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/27 14:48
 */
@ApiModel("使用必选参数进行部门创建")
public class DepartmentCreateInfo {
    @ApiModelProperty("部门名字-必选")
    private String departmentName;
    private String departmentCode;
    @ApiModelProperty("部门创建者-必选")
    private String departmentRoot;
    private String personnelCode;

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentRoot() {
        return departmentRoot;
    }

    public void setDepartmentRoot(String departmentRoot) {
        this.departmentRoot = departmentRoot;
    }
}
