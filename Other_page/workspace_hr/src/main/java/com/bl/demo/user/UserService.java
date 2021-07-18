package com.bl.demo.user;

import com.bl.demo.pojo.SalaryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    //添加用户需要loginName, loginPwd, userName, userCode,userEmail参数
    public int addUser(Map<String, Object> param) {
        return userMapper.addUser(param);
    }

    //根据usercode修改密码
    public int changePwd(Map<String, Object> param) {
        return userMapper.changePwd(param);
    }

    //根据usertoken添加token
    public int addTkn(Map<String, Object> param) {
        return userMapper.addTkn(param);
    }

    public String getTkn(String name) {
        return userMapper.getTkn(name);
    }

    public String searchEmail(String loginName) {
        return userMapper.searchEmail(loginName);
    }

    public Map<String, Object> login(Map<String, Object> param) {
        return userMapper.login(param);
    }

    public Map<String, Object> getCheckInAndIsCheck(Map<String, Object> param) {
        return userMapper.getCheckInAndIsCheck(param);
    }

    public int addCheckInAndIsCheck(Map<String, Object> param) {
        return userMapper.addCheckInAndIsCheck(param);
    }

    public int updataCheckInAndIsCheck(Map<String, Object> param) {
        return userMapper.updataCheckInAndIsCheck(param);
    }

    public Map<String,Object>getSalary(Map<String,Object> param){
        return userMapper.getSalary(param);
    }

    public int addSalary(List<SalaryPojo> param){
        return userMapper.addSalary(param);
    }
    public List<Map<String,Object>> getCheckUser(Map<String,Object> param){
        return userMapper.getCheckUser(param);
    }

    public List<Map<String,Object>> getDeptAndRole(){
        return userMapper.getDeptAndRole();
    }
}
