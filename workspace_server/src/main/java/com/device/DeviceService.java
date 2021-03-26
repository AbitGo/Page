package com.device;

import com.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceService {
    @Autowired
    DeviceMapper departmentMapper;
    public int addDevice(DeviceAddInfo param){
        return departmentMapper.addDevice(param);
    }
    public int deleteDevice(DeviceDeleteOrSearchInfo param){
        return departmentMapper.deleteDevice(param);
    }

    public List<Map<String,Object>> searchDevice(DeviceDeleteOrSearchInfo param,int page,int limit){
        return departmentMapper.searchDevice(param);
    }
    public int addTask(TaskAddAndSearchInfo taskAddInfo){
        return departmentMapper.addTask(taskAddInfo);
    }
    public List<Map<String,Object>> searchTaskByProposeCode(TaskAddAndSearchInfo param,int page,int limit){
        return departmentMapper.searchTaskByProposeCode(param);
    }
    public List<Map<String,Object>> searchTaskByManager(TaskSearchByManager taskSearchByManager,int page,int limit){
        return departmentMapper.searchTaskByManager(taskSearchByManager);
    }
    public int auditTaskByTaskCode(TaskAuditInfo param){
        return departmentMapper.auditTaskByTaskCode(param);
    }
}
