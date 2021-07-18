package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 3/27/2021 12:13 AM
 */
@ApiModel(value = "审批任务")
public class TaskAuditInfo {
    @ApiModelProperty("任务唯一id-必选")
    private String taskCode;
    @ApiModelProperty("任务审核标识符-必选")
    private String taskStatus;

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
