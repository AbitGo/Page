package com.example.reapp.pojo;

public class TaskInfo {

    private String taskCode;
    private String deviceIMEI;
    private String userName;
    private String deviceName;
    private Integer taskStatus;
    private Integer taskTime;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Integer taskTime) {
        this.taskTime = taskTime;
    }

    @Override
    public String toString() {
        return "TaskInfo{" +
                "taskCode='" + taskCode + '\'' +
                ", deviceIMEI='" + deviceIMEI + '\'' +
                ", userName='" + userName + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", taskStatus=" + taskStatus +
                ", taskTime=" + taskTime +
                '}';
    }
}
