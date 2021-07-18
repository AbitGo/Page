package com.DeviceData;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceMapper {
    int addDeviceByUserCode(Map<String,Object> param);
    List<Map<String,Object>> getDeviceByUserCode(Map<String,Object> param);
    String getDeviceByDeviceIMEI(String DeviceIMEI);
    String getDeviceByDeviceCode(String DeviceCode);
    int deleteDeviceByDeviceCode(String DeviceCode);
}
