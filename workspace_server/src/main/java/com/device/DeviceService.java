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
}
