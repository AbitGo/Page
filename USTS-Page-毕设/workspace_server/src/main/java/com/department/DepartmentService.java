package com.department;

import com.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    public int departmentCreate(DepartmentCreateInfo param){
        return departmentMapper.departmentCreate(param);
    }

    public int departmentUpdate(DepartmentUpdateInfo param){
        return departmentMapper.departmentUpdate(param);
    }
    public int addPersonAuthorization(PersonAuthonizationInfo param){
        return departmentMapper.addPersonAuthorization(param);
    }
    public Map<String,Object> personAuthorization(Map<String,String> param){
        return departmentMapper.personAuthorization(param);
    }
    public int delPerson(PersonDelInfo param){
        return departmentMapper.delPerson(param);
    }

    public List<Map<String,Object>> searchDepartment(DepartmentSearch param,int index,int limit){
        return departmentMapper.searchDepartment(param);
    }

    public List<Map<String,Object>> getDepartment(String userCode){
        return departmentMapper.getDepartment(userCode);
    }
}
