package com.bl.demo;

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
import java.util.Map;

@RestController
public class TotolController {
    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @RequestMapping(value = "/User/UserLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UserLogin(@RequestBody String LoginJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(LoginJSON);
        //获取登录名与登录密码
        String loginName = LoginJson.getString("loginName");
        String loginPwd = LoginJson.getString("loginPwd");
        String userName = LoginJson.getString("userName");
        String userCode = LoginJson.getString("userCode");
        String userEmail = LoginJson.getString("userEmail");
        Map<String,Object> param = new HashMap<>();
        param.put("loginName",loginName);
        param.put("loginPwd",loginPwd);
        param.put("userName",userName);
        param.put("userCode",userCode);
        param.put("userEmail",userEmail);
        JSONObject jsonObject = new JSONObject();
        try
        {
            int result = userService.addUser(param);
            jsonObject.put("flag",1);
            jsonObject.put("msg","注册成功");
        }catch (Exception e)
        {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "注册失败,用户名已存在");
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

        try{
            StringWriter mail = new StringWriter();
            User user = new User();
            user.setToken(token);
            user.setLoginName(loginName);
            mailService.sendSimpleMail("15695203200@163.com",email,"修改密码邮件","修改密码邮件",token);
        }catch (Exception e){
            e.printStackTrace();
        }
        int result = userService.addTkn(param);

        if(result!=0){
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "验证码已经发送至邮箱，清查收");
        }else{
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "发送失败，请验证邮箱是否正确");
            jsonObject.put("token",token);
        }
        return jsonObject.toString();
    }

}
