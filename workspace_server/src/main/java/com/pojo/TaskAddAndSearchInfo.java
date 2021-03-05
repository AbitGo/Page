package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/3/3 19:47
 */
@ApiModel("添加任务所需要的参数")
public class TaskAddAndSearchInfo {
    @ApiModelProperty("页码")
    private int index;
    @ApiModelProperty("页数")
    private int limit;
    @ApiModelProperty("任务唯一id")
    private String taskCode;
    @ApiModelProperty("申请人唯一id")
    private String proposerCode;
    @ApiModelProperty("设备唯一id")
    private String deviceIMEI;
    @ApiModelProperty("任务申请时间")
    private long taskTime;
    @ApiModelProperty("任务查询状态")
    private int taskStatus;


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

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getProposerCode() {
        return proposerCode;
    }

    public void setProposerCode(String proposerCode) {
        this.proposerCode = proposerCode;
    }

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public long getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(long taskTime) {
        this.taskTime = taskTime;
    }
}
