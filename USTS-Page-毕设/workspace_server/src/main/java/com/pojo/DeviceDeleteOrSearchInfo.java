package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/3/1 20:20
 */
@ApiModel("设备删除或查找参数-可选")
public class DeviceDeleteOrSearchInfo {
    @ApiModelProperty("部门id-查找可选-删除必选")
    private String departmentCode;
    @ApiModelProperty("设备IMEI-查找可选-删除必选")
    private String deviceIMEI;
    @ApiModelProperty("设备IMEI-查找不选-删除必选")
    private String userCode;
    @ApiModelProperty("设备IMEI-查找可选-删除可选")
    private String deviceName;
    @ApiModelProperty("页码-必选")
    private int index;
    @ApiModelProperty("页数-必选")
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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

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

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }
}
