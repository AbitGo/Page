package com.example.reapp.pojo;

import java.util.List;
import java.util.Map;

public class ResultInfo<T> {

    private String executeMsg;
    private String executeStatus;
    private T info;
    private List<T> infos;
    public void setExecuteMsg(String executeMsg) {
        this.executeMsg = executeMsg;
    }
    public String getExecuteMsg() {
        return executeMsg;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }
    public String getExecuteStatus() {
        return executeStatus;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public List<T> getInfos() {
        return infos;
    }

    public void setInfos(List<T> infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "executeMsg='" + executeMsg + '\'' +
                ", executeStatus='" + executeStatus + '\'' +
                ", info=" + info +
                ", infos=" + infos +
                '}';
    }
}
