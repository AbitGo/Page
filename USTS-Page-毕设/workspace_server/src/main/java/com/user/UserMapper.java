package com.user;

import com.pojo.UserLoginInfo;
import com.pojo.UserRegisterInfo;
import com.pojo.UserSearchInfo;
import com.pojo.UserUpdatePwdInfo;
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
    //搜索用户邮箱
    public Map<String,String> searchUserEmail(String userInfo);
    //添加最新数据
    public int updateUserVerCode(Map<String,String> param);
    //获取用户验证码
    public String getUserVerCode(String mail);
    //修改用户密码
    public int updateUserPwd(UserUpdatePwdInfo param);
}
