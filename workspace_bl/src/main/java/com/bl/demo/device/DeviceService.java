package com.bl.demo.device;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceService {
    @Autowired
    DeviceMapper deviceMapper;
    public int addDeviceByUserCode(Map<String,Object> param){
        return deviceMapper.addDeviceByUserCode(param);
    }
    public List<Map<String,Object>> getDeviceBuUserCode(String userCode){
        return deviceMapper.getDeviceBuUserCode(userCode);
    }
    public int deleteDeviceByUserCode(Map<String,Object> param){
        return deviceMapper.deleteDeviceByUserCode(param);
    }

    public List<Map<String,Object>> getRecordDataByDeviceCode(String param){
        return deviceMapper.getRecordDataByDeviceCode(param);
    }

    public List<String> getDevice(){
        return deviceMapper.getDevice();
    }

    public int addDeviceData(Map<String,Object> param){
        return deviceMapper.addDeviceData(param);
    }
}
