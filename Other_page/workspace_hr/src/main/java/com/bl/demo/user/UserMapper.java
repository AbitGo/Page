package com.bl.demo.user;

import com.bl.demo.pojo.SalaryPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    //添加用户需要loginName, loginPwd, userName, userCode,userEmail参数
    public int addUser(Map<String,Object> param);
    //根据usercode修改密码
    public int changePwd(Map<String,Object> param);
    //根据usertoken添加token
    public int addTkn(Map<String,Object> param);
    public Map<String,Object> login(Map<String,Object> param);
    //通过loginName获取邮箱以及UserCode
    public String searchEmail(String loginName);
    public String getTkn(String loginName);

    //获取当前月份签到次数以及今日是否签到
    public Map<String,Object> getCheckInAndIsCheck(Map<String,Object> param);
    //当查询该月没有签int到则直接添加
    public int addCheckInAndIsCheck(Map<String,Object> param);
    //修改当月签到次数以及签到时间
    public int updataCheckInAndIsCheck(Map<String,Object> param);

    //获取当前月份工资
    public Map<String,Object> getSalary(Map<String,Object> param);

    //生成当前月份工资add
    public int addSalary(List<SalaryPojo> param);

    //获取当前月打卡的人
    public List<Map<String,Object>> getCheckUser(Map<String,Object> param);
    public List<Map<String,Object>> getDeptAndRole();


}
