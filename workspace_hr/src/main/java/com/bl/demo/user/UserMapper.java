package com.bl.demo.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {
    //添加用户需要loginName, loginPwd, userName, userCode,userEmail参数
    public int addUser(Map<String,Object> param);
    //根据usercode修改密码
    public int changePwd(Map<String,Object> param);
    //根据usertoken添加token
    public int addTkn(Map<String,Object> param);
    public Map<String,Object> login(Map<String,Object> param);
    //通过loginName获取邮箱以及UserCode
    public String searchEmail(String loginName);
    public String getTkn(String loginName);
}
