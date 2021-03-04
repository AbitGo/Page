package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pojo.ReturnMessage;
import com.pojo.UserLoginInfo;
import com.pojo.UserRegisterInfo;
import com.pojo.UserSearchInfo;
import com.user.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation(value = "用户注册",notes = "用户使用必要参数进行注册")
    @Transactional
    @RequestMapping(value = "/user/userRegister", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userRegister(@RequestBody @ApiParam(name = "搜寻部门",value = "传入参数",required = true)UserRegisterInfo userRegisterInfo) throws Exception {
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

    @ApiOperation(value = "用户登录",notes = "用户登录必要参数进行注册")
    @Transactional
    @RequestMapping(value = "/user/userLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userLogin(@RequestBody @ApiParam(name = "用户登录",value = "传入参数",required = true)UserLoginInfo userLoginInfo) throws Exception {
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


    @ApiOperation(value = "用户查找",notes = "使用可选参数搜索用户")
    @Transactional
    @RequestMapping(value = "/user/userSearch", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userSearch(@RequestBody @ApiParam(name = "查询设备",value = "传入参数",required = true)  UserSearchInfo userSearchInfo) throws Exception {
        ReturnMessage returnMessage = new ReturnMessage();
        List<Map<String, Object>> result = userService.userSearch(userSearchInfo);
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("搜索成功");
        returnMessage.setInfos(result);
        return JSONObject.toJSONString(returnMessage);
    }
}
