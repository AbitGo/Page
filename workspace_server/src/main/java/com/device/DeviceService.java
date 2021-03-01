package com.device;

import com.pojo.DepartmentCreateInfo;
import com.pojo.DepartmentUpdateInfo;
import com.pojo.PersonAuthonizationInfo;
import com.pojo.PersonDelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DeviceService {
    @Autowired
    DeviceMapper departmentMapper;
    public int departmentCreate(DepartmentCreateInfo param){
        return departmentMapper.departmentCreate(param);
    }

    public int departmentUpdate(DepartmentUpdateInfo param){
        return departmentMapper.departmentUpdate(param);
    }
    public int addPersonAuthorization(PersonAuthonizationInfo param){
        return departmentMapper.addPersonAuthorization(param);
    }
    public Map<String,Object> delPersonAuthorization(Map<String,String> param){
        return departmentMapper.delPersonAuthorization(param);
    }
    public int delPerson(PersonDelInfo param){
        return departmentMapper.delPerson(param);
    }
}
