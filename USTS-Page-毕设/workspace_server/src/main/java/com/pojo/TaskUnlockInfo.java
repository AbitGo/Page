package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 4/5/2021 10:02 PM
 */
@ApiModel(value = "执行任务")
public class TaskUnlockInfo {
    @ApiModelProperty("执行人唯一ID-必选")
    private String proposerCode;
    @ApiModelProperty("任务唯一ID-必选")
    private String taskCode;
    @ApiModelProperty("设备唯一ID-必选")
    private String deviceIMEI;
    @ApiModelProperty("任务申请时间-必选")
    private String taskTime;
    @ApiModelProperty("任务状态-必选")
    private int taskStatus;

    public String getProposerCode() {
        return proposerCode;
    }

    public void setProposerCode(String proposerCode) {
        this.proposerCode = proposerCode;
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

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public int getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(int taskStatus) {
        this.taskStatus = taskStatus;
    }

//    @Override
//    public String toString() {
//        return "proposerCode"+this.getProposerCode();
//    }
}
