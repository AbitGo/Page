package com.department;

import com.pojo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface DepartmentMapper {
    //创建部门
    public int departmentCreate(DepartmentCreateInfo param);
    //修改部门信息
    public int departmentUpdate(DepartmentUpdateInfo param);
    //添加部门人员验证
    public int addPersonAuthorization(PersonAuthonizationInfo param);
    //删除部门人员之前的权限认证
    public Map<String,Object> personAuthorization(Map<String,String> param);
    //删除部门人员
    public int delPerson(PersonDelInfo param);
    //查找部门人员
    public List<Map<String,Object>> searchDepartment(DepartmentSearch param);
    public List<Map<String,Object>> getDepartment(String userCode);

}
