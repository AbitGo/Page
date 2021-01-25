package com.bl.demo.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int addUser(Map<String,Object> param);
    Map<String,Object> login(Map<String,Object> param);
    int updateUserInfo(Map<String,Object> param);
    int updateUserPwd(Map<String,Object> param);
    List<Map<String,Object>> getUserList();
}
