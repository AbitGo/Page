package com.pojo;

import java.util.List;
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
    private List<Map<String,Object>> infos;

    public ReturnMessage() {
    }

    public ReturnMessage(String executeStatus, String executeMsg, List<Map<String, Object>> infos) {
        this.executeStatus = executeStatus;
        this.executeMsg = executeMsg;
        this.infos = infos;
    }

    public ReturnMessage(String executeStatus, String executeMsg, Map<String, Object> info) {
        this.executeStatus = executeStatus;
        this.executeMsg = executeMsg;
        this.info = info;
    }

    public ReturnMessage(String executeStatus, String executeMsg) {
        this.executeStatus = executeStatus;
        this.executeMsg = executeMsg;
    }

    public ReturnMessage(String executeStatus, String executeMsg, Map<String, Object> info, List<Map<String, Object>> infos) {
        this.executeStatus = executeStatus;
        this.executeMsg = executeMsg;
        this.info = info;
        this.infos = infos;
    }


    public List<Map<String, Object>> getInfos() {
        return infos;
    }

    public void setInfos(List<Map<String, Object>> infos) {
        this.infos = infos;
    }

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
