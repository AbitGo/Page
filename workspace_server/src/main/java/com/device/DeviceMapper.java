package com.device;

import com.pojo.DepartmentCreateInfo;
import com.pojo.DepartmentUpdateInfo;
import com.pojo.PersonAuthonizationInfo;
import com.pojo.PersonDelInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface DeviceMapper {
    //添加设备
    public int addDevice();

}
