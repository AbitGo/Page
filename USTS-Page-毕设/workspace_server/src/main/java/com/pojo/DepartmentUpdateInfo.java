package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/27 15:15
 */

@ApiModel(value = "修改部门所需要的参数")
public class DepartmentUpdateInfo {

    @ApiModelProperty("部门唯一id-必选")
    private String departmentCode;
    @ApiModelProperty("部门信息-必选")
    private String departmentInfo;
    @ApiModelProperty("部门名称-必选")
    private String departmentName;

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentInfo() {
        return departmentInfo;
    }

    public void setDepartmentInfo(String departmentInfo) {
        this.departmentInfo = departmentInfo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
