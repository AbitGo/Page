package com.example.reapp.pojo;

public class DeviceInfo {

    private String deviceIMEI;
    private String deviceDefaultPWD;
    private String departmentCode;
    private String deviceName;
    private String deviceInfo;

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

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "deviceIMEI='" + deviceIMEI + '\'' +
                ", deviceDefaultPWD='" + deviceDefaultPWD + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                '}';
    }
}
