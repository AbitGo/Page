package com.department;

import com.pojo.DepartmentCreateInfo;
import com.pojo.DepartmentUpdateInfo;
import com.pojo.UserRegisterInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper {
    //创建部门
    public int departmentCreate(DepartmentCreateInfo param);
    //修改部门信息
    public int departmentUpdate(DepartmentUpdateInfo param);

}
