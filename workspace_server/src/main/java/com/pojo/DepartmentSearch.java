package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/3/3 16:14
 */
@ApiModel(value = "搜寻部门所需要的参数")
public class DepartmentSearch {
    @ApiModelProperty("部门唯一ID-可选")
    private String departmentCode;
    @ApiModelProperty("部门名字-可选")
    private String departmentName;
    @ApiModelProperty("部门分页页数-必选")
    private int index;
    @ApiModelProperty("部门分页容量-必选")
    private int limit;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
