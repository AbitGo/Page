package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mail.SendMailService;
import com.pojo.*;
import com.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    SendMailService mailService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "用户获取验证码", notes = "使用登录名或邮箱获取验证码")
    @Transactional
    @RequestMapping(value = "/user/getUserVerificationCode", method = RequestMethod.GET)
    @CrossOrigin
    public String getUserVerificationCode(@RequestParam @ApiParam(name = "获取验证码", value = "用户信息", required = true)String userInfo) {

        String currentTime_s = System.currentTimeMillis()+"";
        ReturnMessage returnMessage = new ReturnMessage();

        Map<String,String> email = userService.searchUserEmail(userInfo);
        if(null!=email)
        {
            HashMap<String,String> temp = new HashMap<>();
            temp.put("verification",currentTime_s);
            temp.put("userEmail",email.get("userEmail"));
            userService.updateUserVerCode(temp);
            mailService.sendHtmlMail(email.get("userEmail"),"找回密码",currentTime_s.substring(currentTime_s.length()-6,currentTime_s.length()));
            returnMessage.setExecuteStatus("1");
            returnMessage.setExecuteMsg("发送成功");
        }else{
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("该用户未注册");
        }
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "用户修改密码", notes = "用户使用必要参数进行密码修改")
    @Transactional
    @RequestMapping(value = "/user/userUpdatePwd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userUpdatePwd(@RequestBody @ApiParam(name = "修改密码", value = "传入参数", required = true) UserUpdatePwdInfo userUpdatePwdInfo) throws Exception {

        ReturnMessage returnMessage = new ReturnMessage();

        String userVerificationCode = userService.getUserVerCode(userUpdatePwdInfo.getUserEmail());
        if(!userVerificationCode.equals("0")){
            if(System.currentTimeMillis()-Long.parseLong(userVerificationCode)>300000){
                returnMessage.setExecuteMsg("验证码已过期");
                returnMessage.setExecuteStatus("0");
            }else{
                if(Long.parseLong(userVerificationCode)%1000000==Long.parseLong(userUpdatePwdInfo.getvCode())){
                    userService.updateUserPwd(userUpdatePwdInfo);
                    returnMessage.setExecuteMsg("修改成功");
                    returnMessage.setExecuteStatus("01");
                }else{
                    returnMessage.setExecuteMsg("验证码不正确");
                    returnMessage.setExecuteStatus("0");
                }
            }
        }
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "用户注册", notes = "用户使用必要参数进行注册")
    @Transactional
    @RequestMapping(value = "/user/userRegister", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userRegister(@RequestBody @ApiParam(name = "搜寻部门", value = "传入参数", required = true) UserRegisterInfo userRegisterInfo) throws Exception {
        ReturnMessage returnMessage = new ReturnMessage();

        //前端传输过来的数据必须经过验证
        userRegisterInfo.setUserCode("USER" + System.currentTimeMillis());
        try {
            userService.userRegister(userRegisterInfo);
        } catch (Exception e) {
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("注册失败.该登录名字已被使用");
            return JSONObject.toJSONString(returnMessage);
        }
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("注册成功.请立刻登录");
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "用户登录", notes = "用户登录必要参数进行注册")
    @Transactional
    @RequestMapping(value = "/user/userLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userLogin(@RequestBody @ApiParam(name = "用户登录", value = "传入参数", required = true) UserLoginInfo userLoginInfo) throws Exception {
        ReturnMessage returnMessage = new ReturnMessage();
        Map<String, Object> result = userService.userLogin(userLoginInfo);
        if (null == result) {
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("用户未注册");
        } else {
            if (!userLoginInfo.getUserPassword().equals(result.get("userPassword"))) {
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("密码错误");
            } else {
                returnMessage.setExecuteStatus("1");
                returnMessage.setExecuteMsg("登录成功");
                returnMessage.setInfo(result);
            }
        }
        return JSONObject.toJSONString(returnMessage);
    }


    @ApiOperation(value = "用户查找", notes = "使用可选参数搜索用户")
    @Transactional
    @RequestMapping(value = "/user/userSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userSearch(@RequestBody @ApiParam(name = "查找用户", value = "传入参数", required = true) UserSearchInfo userSearchInfo) throws Exception {
        List<Map<String, Object>> result = userService.userSearch(userSearchInfo);
        ReturnMessage returnMessage = new ReturnMessage("1", "搜索成功", result);
        return JSONObject.toJSONString(returnMessage);
    }
}
