package com.pojo;

import java.util.Map;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/27 14:32
 */
public class ReturnMessage {
    private String executeStatus;
    private String executeMsg;
    private Map<String,Object> info;

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getExecuteMsg() {
        return executeMsg;
    }

    public void setExecuteMsg(String executeMsg) {
        this.executeMsg = executeMsg;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }
}
