package com.bl.demo;

import com.alibaba.druid.sql.visitor.functions.Char;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bl.DemoApplication;
import com.bl.demo.pojo.SalaryPojo;
import com.bl.demo.pojo.User;
import com.bl.demo.user.MailService;
import com.bl.demo.user.UserService;
import com.bl.utli.PubicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.io.StringWriter;
import java.lang.reflect.Parameter;
import java.util.*;

@RestController
public class TotolController {
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @RequestMapping(value = "/User/UserAdd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UserAdd(@RequestBody String LoginJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(LoginJSON);
        //获取登录名与登录密码
        String loginName = LoginJson.getString("loginName");
        String loginPwd = LoginJson.getString("loginPwd");
        String userName = LoginJson.getString("userName");
        String userCode = "Usr" + PubicMethod.getAcademeCode();
        String userEmail = LoginJson.getString("userEmail");
        int userDept = LoginJson.getInteger("userDept");
        int userRole = LoginJson.getInteger("userRole");
        Map<String, Object> param = new HashMap<>();
        param.put("loginName", loginName);
        param.put("loginPwd", loginPwd);
        param.put("userName", userName);
        param.put("userCode", userCode);
        param.put("userEmail", userEmail);

        param.put("userDept", userDept);
        param.put("userRole", userRole);
        JSONObject jsonObject = new JSONObject();
        try {
            int result = userService.addUser(param);
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "注册成功");
        } catch (Exception e) {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "注册失败,用户名已存在");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/User/UserLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UserLogin(@RequestBody String LoginJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(LoginJSON);
        //获取登录名与登录密码
        String loginName = LoginJson.getString("loginName");
        String loginPwd = LoginJson.getString("loginPwd");
        Map<String, Object> param = new HashMap<>();
        param.put("loginName", loginName);
        param.put("loginPwd", loginPwd);
        Map<String, Object> result = userService.login(param);

        JSONObject jsonObject = new JSONObject();
        if (result == null) {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "用户名不存在");
            return jsonObject.toString();
        } else {
            String pwd = (String) result.get("loginPwd");
            if (!pwd.equals(loginPwd)) {
                jsonObject.put("flag", "0");
                jsonObject.put("msg", "密码不正确");
                return jsonObject.toString();
            } else {
                jsonObject.put("flag", "1");
                jsonObject.put("msg", "登陆成功");
                jsonObject.put("loginName", result.get("loginName"));
                jsonObject.put("loginPwd", result.get("loginPwd"));
                jsonObject.put("userName", result.get("userName"));
                jsonObject.put("userCode", result.get("userCode"));
                jsonObject.put("userEmail", result.get("userEmail"));
                jsonObject.put("roleName", result.get("roleName"));
                jsonObject.put("departmentName", result.get("departmentName"));
                System.out.println(result.get("departmentName"));
            }
        }
        return jsonObject.toString();
    }


    @RequestMapping(value = "/User/UserToken", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UserToken(@RequestBody String TokenJSON) throws Exception {
        JSONObject tokenJSON = JSONObject.parseObject(TokenJSON);
        //获取登录名与登录密码
        String loginName = tokenJSON.getString("loginName");

        String email = userService.searchEmail(loginName);

        //System.out.println("emil:"+email);
        JSONObject jsonObject = new JSONObject();
        if (email == null) {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "登陆名不存在");
            return jsonObject.toString();
        }
        Map<String, Object> param = new HashMap<>();

        String token = "Tkn" + PubicMethod.getAcademeCode();
        param.put("loginName", loginName);
        param.put("token", token);

        String send_email = "15695203200@163.com";
        try {
            mailService.sendSimpleMail(send_email, email, send_email, "修改密码邮件", "验证码为：" + token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int result = userService.addTkn(param);

        if (result != 0) {
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "验证码已经发送至邮箱，清查收");
            jsonObject.put("token", token);
        } else {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "发送失败，请验证邮箱是否正确");
            jsonObject.put("token", token);
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/User/ChangePWD", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String ChangePWD(@RequestBody String tokenJSON) throws Exception {
        JSONObject json = JSONObject.parseObject(tokenJSON);
        //获取登录名与登录密码
        String loginName = json.getString("loginName");
        String newLoginPWD = json.getString("newLoginPWD");
        String token = json.getString("token");


        Map<String, Object> param = new HashMap<>();
        param.put("loginPwd", newLoginPWD);
        param.put("loginName", loginName);

        String SQLToken = userService.getTkn(loginName);
        int result = 0;
        JSONObject jsonObject = new JSONObject();
        if (SQLToken == null) {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "用户不存在");
            return jsonObject.toString();
        }
        if (SQLToken.equals(token)) {
            result = userService.changePwd(param);
        }


        if (result != 0) {
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "修改密码成功");
        } else {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "修改失败,请联系管理员");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/User/checkIn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String CheckIn(@RequestBody String checkJSON) throws Exception {
        JSONObject json = JSONObject.parseObject(checkJSON);
        //获取登录名与登录密码
        String userCode = json.getString("userCode");

        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;//月份
        int day = calendar.get(Calendar.DATE);
        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        param.put("year", year);
        param.put("month", month);

        param.put("checkYear", year);
        param.put("checkMonth", month);

        Map<String, Object> result = userService.getCheckInAndIsCheck(param);

        JSONObject jsonObject = new JSONObject();
        if (result==null) {
            //当月并没签到，就可以进行插入操作，并实现当前日期的操作
            String checkingIn = "0000000000000000000000000000000";
            char[] checkCountByte = checkingIn.toCharArray();
            checkCountByte[day] = '1';
            checkingIn = new String(checkCountByte);
            param.put("checkingIn", checkingIn);
            //第一次签到为1
            param.put("checkCount", 1);
            int result_add = userService.addCheckInAndIsCheck(param);
            if (result_add != 0) {
                jsonObject.put("flag", "1");
                jsonObject.put("msg", "签到成功");
                return jsonObject.toString();
            } else {
                jsonObject.put("flag", "0");
                jsonObject.put("msg", "签到失败");
                return jsonObject.toString();
            }
        } else {
            //已经签到一回之后
            String checkingIn = (String) result.get("checkingIn");
            int checkCount = (int) result.get("checkCount");
            char[] checkCountByte = checkingIn.toCharArray();
            if (checkCountByte[day] == '1') {
                jsonObject.put("flag", "0");
                jsonObject.put("msg", "当天已经签到，请勿重复签到");
                return jsonObject.toString();
            }
            checkCountByte[day] = '1';
            checkingIn = new String(checkCountByte);
            checkCount++;
            param.put("checkingIn", checkingIn);
            param.put("checkCount", checkCount);
            int result_add = userService.updataCheckInAndIsCheck(param);
            if (result_add != 0) {
                jsonObject.put("flag", "1");
                jsonObject.put("msg", "签到成功");
                return jsonObject.toString();
            } else {
                jsonObject.put("flag", "0");
                jsonObject.put("msg", "签到失败,请练习管理员");
                return jsonObject.toString();
            }
        }
    }

    @RequestMapping(value = "/User/getCheckIn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String GetCheckIn(@RequestBody String tokenJSON) throws Exception {
        JSONObject json = JSONObject.parseObject(tokenJSON);
        //获取登录名与登录密码
        String userCode = json.getString("userCode");
        int checkYear = json.getInteger("checkYear");
        int checkMonth = json.getInteger("checkMonth");

        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        param.put("checkYear", checkYear);
        param.put("checkMonth", checkMonth);

        Map<String, Object> result = userService.getCheckInAndIsCheck(param);
        JSONObject jsonObject = new JSONObject();
        if (result == null) {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "该月并未打卡记录");
            return jsonObject.toString();
        }else{
            jsonObject.put("flag", "1");
            jsonObject.put("msg","查询成功");
            String checkingIn = (String)result.get("checkingIn");
            int checkCount = (int)result.get("checkCount");
            jsonObject.put("checkingIn",checkingIn);
            jsonObject.put("checkCount",checkCount);
            jsonObject.put("userCode",userCode);
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/User/getSalary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String GetSalary(@RequestBody String tokenJSON) throws Exception {
        JSONObject json = JSONObject.parseObject(tokenJSON);
        //获取登录名与登录密码
        String userCode = json.getString("userCode");
        int checkYear = json.getInteger("checkYear");
        int checkMonth = json.getInteger("checkMonth");

        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        param.put("year", checkYear);
        param.put("month", checkMonth);

        Map<String, Object> result = userService.getSalary(param);
        JSONObject jsonObject = new JSONObject();
        if (result == null) {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "该月并未记录工资");
            return jsonObject.toString();
        }else{
            jsonObject.put("flag", "1");
            jsonObject.put("msg","查询成功");
            int year_s = (int)result.get("year_s");
            int month_s = (int)result.get("month_s");
            float salary = (float) result.get("salary");
            int workDay = (int) result.get("workDay");

            jsonObject.put("checkYear",year_s);
            jsonObject.put("checkMonth",month_s);
            jsonObject.put("userCode",userCode);
            jsonObject.put("workDay",workDay);
            jsonObject.put("salary",salary);
            return jsonObject.toString();
        }
    }


    //生成测试工资
    @RequestMapping(value = "/User/createSalary", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String CreateSalary(@RequestBody String tokenJSON) throws Exception {
        JSONObject json = JSONObject.parseObject(tokenJSON);

        int checkYear = json.getInteger("checkYear");
        int checkMonth = json.getInteger("checkMonth");

        Map<String, Object> param = new HashMap<>();
        param.put("checkYear", checkYear);
        param.put("checkMonth", checkMonth);

        List<Map<String,Object>> result = userService.getCheckUser(param);
        List<SalaryPojo> param_add = new ArrayList<>();

        for(Map<String,Object> a_param: result){
            SalaryPojo salaryPojo= new SalaryPojo();

            float roleSalary = (float)a_param.get("roleSalary");
            int checkCount = (int)a_param.get("checkCount");

            salaryPojo.setCheckCount((int)a_param.get("checkCount"));
            salaryPojo.setCheckMonth((int)a_param.get("checkMonth"));
            salaryPojo.setUserCode((String)a_param.get("userCode"));
            salaryPojo.setCheckYear((int)a_param.get("checkYear"));
            salaryPojo.setSalary(roleSalary*checkCount);

            param_add.add(salaryPojo);
        }

        int result_add = userService.addSalary(param_add);

        JSONObject jsonObject = new JSONObject();
        if (result == null) {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "ok");
            return jsonObject.toString();
        }else{
            jsonObject.put("flag", "1");
            jsonObject.put("msg","查询成功");
            return jsonObject.toString();
        }
    }

    @RequestMapping(value = "/User/getDeptAndRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String GetDept() throws Exception {

        List<Map<String,Object>> result = userService.getDeptAndRole();
        List<SalaryPojo> param_add = new ArrayList<>();

        JSONArray jsonArray = new JSONArray();

        for(Map<String,Object> a_param: result){
            JSONObject i = new JSONObject();
            i.put("roleId",a_param.get("roleId"));
            i.put("roleName",a_param.get("roleName"));
            i.put("departmentName",a_param.get("departmentName"));
            i.put("departmentId",a_param.get("departmentId"));
            jsonArray.add(i);
        }
        JSONObject jsonObject = new JSONObject();
        if (result == null) {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "获取失败");
            return jsonObject.toString();
        }else{
            jsonObject.put("flag", "1");
            jsonObject.put("msg","获取成功");
            jsonObject.put("data",jsonArray);
            return jsonObject.toString();
        }
    }


}
