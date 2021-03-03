package com.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/3/1 20:20
 */
@ApiModel(value = "添加设备所需要的参数")
public class DeviceAddInfo {
    @ApiModelProperty("部门唯一ID-必选")
    private String departmentCode;
    @ApiModelProperty("设备名称-必选")
    private String deviceName;
    @ApiModelProperty("设备唯一IMEI-必选")
    private String deviceIMEI;
    @ApiModelProperty("设备默认密码-可选")
    private String deviceDefaultPWD;
    @ApiModelProperty("用户唯一ID-必选")
    private String userCode;

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

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceIMEI() {
        return deviceIMEI;
    }

    public void setDeviceIMEI(String deviceIMEI) {
        this.deviceIMEI = deviceIMEI;
    }

    public String getDeviceDefaultPWD() {
        return deviceDefaultPWD;
    }

    public void setDeviceDefaultPWD(String deviceDefaultPWD) {
        this.deviceDefaultPWD = deviceDefaultPWD;
    }
}
