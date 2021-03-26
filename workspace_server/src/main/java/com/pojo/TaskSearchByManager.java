package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 3/26/2021 11:42 PM
 */
@ApiModel("管理员查找任务所需要的参数")
public class TaskSearchByManager {
    @ApiModelProperty("页码")
    private int index;
    @ApiModelProperty("页数")
    private int limit;
    @ApiModelProperty("管理员的唯一ID")
    private String userCode;
    @ApiModelProperty("需要搜寻的任务状态")
    private String taskStatus;

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

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
