package com.user;

import com.pojo.UserLoginInfo;
import com.pojo.UserRegisterInfo;
import com.pojo.UserSearchInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //用户注册
    public int userRegister(UserRegisterInfo param);
    //用户登录
    public Map<String,Object> userLogin(UserLoginInfo param);
    //用户搜索
    public List<Map<String,Object>> userSearch(UserSearchInfo userInfo);
}
