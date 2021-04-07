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

    public List<Map<String,Object>> searchDevice(DeviceDeleteOrSearchInfo param,int page,int limit){
        return deviceMapper.searchDevice(param);
    }
    public int addTask(TaskAddAndSearchInfo taskAddInfo){
        return deviceMapper.addTask(taskAddInfo);
    }
    public List<Map<String,Object>> searchTaskByProposeCode(TaskAddAndSearchInfo param,int page,int limit){
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
}
