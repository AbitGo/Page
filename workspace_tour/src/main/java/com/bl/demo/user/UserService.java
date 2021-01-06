package com.bl.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public int addUser(Map<String, Object> param) {
        return userMapper.addUser(param);
    }

    public Map<String, Object> login(Map<String, Object> param) {
        return userMapper.login(param);
    }

    public int updateUserInfo(Map<String,Object> info){
        return userMapper.updateUserInfo(info);
    }
    public int updateUserPwd(Map<String,Object> param){
        return userMapper.updateUserPwd(param);
    }
    public List<Map<String,Object>> getUserList(){
        return userMapper.getUserList();
    }
}
