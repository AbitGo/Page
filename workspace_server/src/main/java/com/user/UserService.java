package com.user;

import com.pojo.UserLoginInfo;
import com.pojo.UserRegisterInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public int userRegister(UserRegisterInfo param){
        return userMapper.userRegister(param);
    }
    public Map<String,Object> userLogin(UserLoginInfo param){
        return userMapper.userLogin(param);
    }
}

