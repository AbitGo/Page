package com.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl {
    @Autowired
    UserMapper userMapper;
    public int addUser(Map<String,Object> param){
        return userMapper.addUser(param);
    }
    public Map<String, Object> UserLogin(String LoginName)
    {
        return userMapper.UserLogin(LoginName);
    }
    public int UpdateUserInfo(Map<String,Object> param){
        return  userMapper.UpdateUserInfo(param);
    }
    public int DeleteUserInfo(Map<String,Object> param)
    {
        return userMapper.DeleteUserInfo(param);
    }


    public List<Map<String,Object>>GetUserInfoForAdmin(Map<String,Object> param,int page,int limit){
        return  userMapper.GetUserInfoForAdmin(param);
    }
    public List<Map<String,Object>>GetUserInfoForRoot(Map<String,Object> param,int page,int limit){
        return  userMapper.GetUserInfoForRoot(param);
    }

}
