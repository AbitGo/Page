package com.example.reapp.pojo;

public class LogInfo {
    private Long doTime;
    private String taskCode;
    private String deviceIMEI;
    private Integer recordType;
    private String proposeCode;

    public Long getDoTime() {
        return doTime;
    }

    public void setDoTime(Long doTime) {
        this.doTime = doTime;
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

    public Integer getRecordType() {
        return recordType;
    }

    public void setRecordType(Integer recordType) {
        this.recordType = recordType;
    }

    public String getProposeCode() {
        return proposeCode;
    }

    public void setProposeCode(String proposeCode) {
        this.proposeCode = proposeCode;
    }

    @Override
    public String toString() {
        return "LogInfo{" +
                "doTime=" + doTime +
                ", taskCode='" + taskCode + '\'' +
                ", deviceIMEI='" + deviceIMEI + '\'' +
                ", recordType=" + recordType +
                ", proposeCode='" + proposeCode + '\'' +
                '}';
    }
}
