package com.device;

import com.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeviceMapper {
    //添加设备
    public int addDevice(DeviceAddInfo param);
    //删除设备
    public int deleteDevice(DeviceDeleteOrSearchInfo param);
    //查找设备
    public List<Map<String,Object>> searchDevice(DeviceDeleteOrSearchInfo pram);
    //添加任务
    public int addTask(TaskAddAndSearchInfo taskAddInfo);
    //根据用户申请事件查找
    public List<Map<String,Object>> searchTaskByProposeCode(TaskAddAndSearchInfo param);
}
