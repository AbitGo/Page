package com.user;

import com.pojo.UserRegisterInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface UserMapper {
    //用户注册
    public int userRegister(UserRegisterInfo param);
}
