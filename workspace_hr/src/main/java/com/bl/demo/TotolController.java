package com.bl.demo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bl.DemoApplication;
import com.bl.demo.pojo.User;
import com.bl.demo.user.MailService;
import com.bl.demo.user.UserService;
import com.bl.utli.PubicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String userCode = "Usr"+PubicMethod.getAcademeCode();
        String userEmail = LoginJson.getString("userEmail");
        int userDept = LoginJson.getInteger("userDept");
        int userRole = LoginJson.getInteger("userRole");
        Map<String,Object> param = new HashMap<>();
        param.put("loginName",loginName);
        param.put("loginPwd",loginPwd);
        param.put("userName",userName);
        param.put("userCode",userCode);
        param.put("userEmail",userEmail);

        param.put("userDept",userDept);
        param.put("userRole",userRole);
        JSONObject jsonObject = new JSONObject();
        try
        {
            int result = userService.addUser(param);
            jsonObject.put("flag","1");
            jsonObject.put("msg","注册成功");
        }catch (Exception e)
        {
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
        Map<String,Object> param = new HashMap<>();
        param.put("loginName",loginName);
        param.put("loginPwd",loginPwd);
        Map<String,Object> result = userService.login(param);

        JSONObject jsonObject = new JSONObject();
        if(result==null){
            jsonObject.put("flag","0");
            jsonObject.put("msg","用户名不存在");
            return jsonObject.toString();
        }
        else
        {
            String pwd = (String) result.get("loginPwd");
            if(!pwd.equals(loginPwd)){
                jsonObject.put("flag","0");
                jsonObject.put("msg","密码不正确");
                return jsonObject.toString();
            }else
            {
                jsonObject.put("flag","1");
                jsonObject.put("msg","登陆成功");
                jsonObject.put("loginName",result.get("loginName"));
                jsonObject.put("loginPwd",result.get("loginPwd"));
                jsonObject.put("userName",result.get("userName"));
                jsonObject.put("userCode",result.get("userCode"));
                jsonObject.put("userEmail",result.get("userEmail"));
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
        if(email==null){
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "登陆名不存在");
            return jsonObject.toString();
        }
        Map<String,Object> param = new HashMap<>();

        String token = "Tkn"+PubicMethod.getAcademeCode();
        param.put("loginName",loginName);
        param.put("token",token);

        String send_email = "15695203200@163.com";
        try{
            mailService.sendSimpleMail(send_email,email,send_email,"修改密码邮件","验证码为："+token);
        }catch (Exception e){
            e.printStackTrace();
        }
        int result = userService.addTkn(param);

        if(result!=0){
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "验证码已经发送至邮箱，清查收");
            jsonObject.put("token",token);
        }else{
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "发送失败，请验证邮箱是否正确");
            jsonObject.put("token",token);
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


        Map<String,Object> param = new HashMap<>();
        param.put("loginPwd",newLoginPWD);
        param.put("loginName",loginName);

        String SQLToken = userService.getTkn(loginName);
        int result =0;
        JSONObject jsonObject = new JSONObject();
        if(SQLToken==null)
        {
            jsonObject.put("flag","0");
            jsonObject.put("msg","用户不存在");
            return jsonObject.toString();
        }
        if(SQLToken.equals(token)){
            result = userService.changePwd(param);
        }


        if(result!=0){
            jsonObject.put("flag","1");
            jsonObject.put("msg","修改密码成功");
        }else{
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "修改失败,请联系管理员");
        }
        return jsonObject.toString();
    }
}
