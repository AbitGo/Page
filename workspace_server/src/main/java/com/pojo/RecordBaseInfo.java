package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 4/7/2021 10:15 PM
 */
@ApiModel(value = "记录映射类")
public class RecordBaseInfo {
    @ApiModelProperty("任务唯一id-必选")
    private String taskCode;
    @ApiModelProperty("任务执行时间-必选")
    private long doTime;
    @ApiModelProperty("执行人员唯一ID-必选")
    private String proposeCode;
    @ApiModelProperty("执行设备IMEI-必选")
    private String deviceIMEI;
    @ApiModelProperty("记录类型-必选")
    private int recordType;

    @ApiModelProperty("分页页数-必选")
    private int index;
    @ApiModelProperty("分页容量-必选")
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

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public long getDoTime() {
        return doTime;
    }

    public void setDoTime(long doTime) {
        this.doTime = doTime;
    }

    public String getProposeCode() {
        return proposeCode;
    }

    public void setProposeCode(String proposeCode) {
        this.proposeCode = proposeCode;
    }

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }
}
