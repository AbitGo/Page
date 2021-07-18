package com.device;

import com.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceService {
    @Autowired
    DeviceMapper deviceMapper;
    public int addDevice(DeviceAddInfo param){
        return deviceMapper.addDevice(param);
    }
    public int deleteDevice(DeviceDeleteOrSearchInfo param){
        return deviceMapper.deleteDevice(param);
    }

    public List<Map<String,Object>> searchDevice(String param,int page,int limit){
        return deviceMapper.searchDevice(param);
    }
    public int addTask(TaskSearchByManager taskAddInfo){
        return deviceMapper.addTask(taskAddInfo);
    }
    public List<Map<String,Object>> searchTaskByProposeCode(TaskSearchByManager param,int page,int limit){
        return deviceMapper.searchTaskByProposeCode(param);
    }
    public List<Map<String,Object>> searchTaskByManager(TaskSearchByManager taskSearchByManager,int page,int limit){
        return deviceMapper.searchTaskByManager(taskSearchByManager);
    }
    public int auditTaskByTaskCode(TaskAuditInfo param){
        return deviceMapper.auditTaskByTaskCode(param);
    }

    public TaskUnlockInfo getTaskbyTaskCode(String taskCode){
        return deviceMapper.getTaskbyTaskCode(taskCode);
    }
    public int UpdataTaskStatus(Map<String,Object> param){
        return deviceMapper.UpdataTaskStatus(param);
    }

    public int addRecord(RecordBaseInfo recordBaseInfo){
        return deviceMapper.addRecord(recordBaseInfo);
    }
    public List<Map<String,Object>> searchRecord(String proposerCode,int page,int limit){
        return deviceMapper.searchRecord(proposerCode);
    }
    public List<Map<String,Object>> getDevice(String userCode,int page,int limit ){
        return deviceMapper.getDevice(userCode);
    }
}
