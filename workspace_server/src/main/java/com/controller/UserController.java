package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pojo.ReturnMessage;
import com.pojo.UserRegisterInfo;
import com.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user/userRegister", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userRegister(@RequestBody String paramJson) throws Exception {
        ReturnMessage returnMessage = new ReturnMessage();

        //前端传输过来的数据必须经过验证
        UserRegisterInfo userRegisterInfo = JSON.parseObject(paramJson, UserRegisterInfo.class);
        userRegisterInfo.setUserCode("USER"+System.currentTimeMillis());
        try {
            userService.userRegister(userRegisterInfo);
        }catch (Exception e){
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("注册失败.该登录名字已被使用");
            return JSONObject.toJSONString(returnMessage);
        }
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("注册成功.请立刻登录");
        return JSONObject.toJSONString(returnMessage);
    }
}
