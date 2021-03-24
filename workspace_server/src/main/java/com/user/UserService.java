package com.user;

import com.pojo.UserLoginInfo;
import com.pojo.UserRegisterInfo;
import com.pojo.UserSearchInfo;
import com.pojo.UserUpdatePwdInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public List<Map<String,Object>> userSearch(UserSearchInfo userInfo){
        return userMapper.userSearch(userInfo);
    }
    public Map<String,String> searchUserEmail(String userInfo){
        return userMapper.searchUserEmail(userInfo);
    }
    public int updateUserVerCode(Map<String,String> param){
        return userMapper.updateUserVerCode(param);
    }
    public String getUserVerCode(String mail){
        return userMapper.getUserVerCode(mail);
    }
    public int updateUserPwd(UserUpdatePwdInfo param){
        return userMapper.updateUserPwd(param);
    }
}

