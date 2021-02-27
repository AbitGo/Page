package com.department;

import com.pojo.DepartmentCreateInfo;
import com.pojo.DepartmentUpdateInfo;
import com.pojo.UserRegisterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
