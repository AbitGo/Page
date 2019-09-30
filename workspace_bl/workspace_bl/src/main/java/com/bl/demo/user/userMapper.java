package com.bl.demo.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface userMapper {
    //添加用户需要loginName, loginPwd, userName, userCode,userEmail参数
    public int addUser(Map<String,Object> param);
    //添加
    public int changePwd(Map<String,Object> param);
    public int addTkn(Map<String,Object> param);
    public String getTkn(String name);
}
