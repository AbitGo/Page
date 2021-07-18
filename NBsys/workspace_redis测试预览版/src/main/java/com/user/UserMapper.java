package com.user;

import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //注册用户
    int addUser(Map<String,Object> param);
    //用户登陆，返回用户名与用户加密后的密码
    Map<String, Object> UserLogin(@Param(value="LoginName")String LoginName);

    //更新用户的信息
    int UpdateUserInfo(Map<String,Object> param);

    //删除用户信息，使用EmployeeCode进行删除
    int DeleteUserInfo(Map<String,Object> param);

    //获取自己旗下的用户详细信息
    List<Map<String,Object>> GetUserInfoForAdmin(Map<String,Object> param);

    //专门为管理员做的
    List<Map<String,Object>> GetUserInfoForRoot(Map<String,Object> param);
}
