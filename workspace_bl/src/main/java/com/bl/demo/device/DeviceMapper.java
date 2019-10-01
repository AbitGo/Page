package com.bl.demo.device;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceMapper {
    int addDeviceByUserCode(Map<String,Object> param);
    List<Map<String,Object>> getDeviceBuUserCode(String userCode);
    int deleteDeviceByUserCode(Map<String,Object> param);
    List<Map<String,Object>> getRecordDataByDeviceCode(String param);
    int addDeviceData(Map<String,Object> param);
}
