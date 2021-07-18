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
    private String proposerCode;
    @ApiModelProperty("需要搜寻的任务状态")
    private String taskStatus;
    @ApiModelProperty("isUser")
    private int isUser;
    private String deviceIMEI;
    private String taskCode;
    private long taskTime;

    public long getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public int getIsUser() {
        return isUser;
    }

    public void setIsUser(int isUser) {
        this.isUser = isUser;
    }

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

    public String getProposerCode() {
        return proposerCode;
    }

    public void setProposerCode(String proposerCode) {
        this.proposerCode = proposerCode;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
