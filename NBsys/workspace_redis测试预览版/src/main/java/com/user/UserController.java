package com.user;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.Redis.RedisService;
import com.alibaba.fastjson.JSONObject;
import com.util.PubicMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    RedisService redisService;

    //注册用户
    @RequestMapping(value = "/User/AddUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String addUser(@RequestBody String addJSON) throws Exception {
        JSONObject AddJson = JSONObject.parseObject(addJSON);

        //进行用户名验证，如果已存在需要提示
        String LoginName = AddJson.getString("LoginName");
        String LoginPassword = AddJson.getString("LoginPassword");
        String UserMail = AddJson.getString("UserMail");
        String UserName = AddJson.getString("UserName");
        String UserCode = "Usr"+PubicMethod.getAcademeCode();
        String UserPhone = AddJson.getString("UserPhone");
        String Description = AddJson.getString("Description");
        String ProvinceInfo = AddJson.getString("ProvinceInfo");
        String AreaInfo = AddJson.getString("AreaInfo");
        int UserRole = AddJson.getIntValue("UserRole");
        String LeaderCode = AddJson.getString("LeaderCode");

        logger.info("AddUser: "+"-----Start-----");
        logger.info("LoginName: "+LoginName+" LoginPassword: "+LoginPassword+" UserName: "+UserName);
        logger.info("UserMail: "+UserMail+" UserPhone: "+UserPhone+" Description: "+Description);
        logger.info("ProvinceInfo: "+ProvinceInfo+" AreaInfo: "+AreaInfo+" UserRole: "+UserRole);
        logger.info("LeaderCode: "+LeaderCode+" UserCode: "+UserCode);
        logger.info("AddUser: "+"-----End-----");

        Boolean Activity = false;
        if(UserRole==2)
            Activity = true;
        else
        {
            Activity = AddJson.getBoolean("Activity");
        }

        Map<String,Object> param = new HashMap<>();
        param.put("LoginName",LoginName);
        param.put("LoginPassword",LoginPassword);
        param.put("UserMail",UserMail);
        param.put("UserName",UserName);
        param.put("UserCode",UserCode);
        param.put("UserPhone",UserPhone);
        param.put("Description",Description);
        param.put("UserArea",AreaInfo);
        param.put("UserProvince",ProvinceInfo);
        param.put("UserRole",UserRole);
        param.put("Activity",Activity);
        param.put("LeaderCode",LeaderCode);

        JSONObject jsonObject = new JSONObject();
        try
        {
            userServiceImpl.addUser(param);
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "注册成功");
        }catch (Exception e)
        {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "注册失败,用户名已存在");
        }
        return jsonObject.toString();
    }

    //用户登录与用户信息的详细返回
    @RequestMapping(value = "/User/UserLogin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String UserLogin(@RequestBody String LoginJSON) throws Exception {
        JSONObject LoginJson = JSONObject.parseObject(LoginJSON);
        //获取登录名与登录密码
        String LoginNameInfo = LoginJson.getString("LoginNameInfo");
        String LoginPassordInfo = LoginJson.getString("LoginPassordInfo");

        logger.info("UserLogin: "+"-----Start-----");
        logger.info("LoginNameInfo: "+LoginNameInfo+" LoginPassordInfo: "+LoginPassordInfo);
        logger.info("UserLogin: "+"-----End-----");

        Map<String, Object> userInfo = userServiceImpl.UserLogin(LoginNameInfo);

        JSONObject jsonObject = new JSONObject();
        //账号不存在
        if(userInfo==null)
        {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "账号不存在");
            return jsonObject.toString();
        }
        //账号存在则将用户信息全部发送
        else{
            String LoginName = (String) userInfo.get("LoginName");
            String LoginPassword = (String)userInfo.get("LoginPassword");
            String UserMail = (String)userInfo.get("UserMail");
            String UserName = (String)userInfo.get("UserName");
            String UserCode = (String)userInfo.get("UserCode");
            String UserPhone = (String)userInfo.get("UserPhone");
            String Description = (String)userInfo.get("Description");
            String ProvinceInfo = (String)userInfo.get("ProvinceInfo");
            String AreaInfo = (String)userInfo.get("AreaInfo");
            int UserRole = (int)userInfo.get("UserRole");
            String UserProvince = (String)userInfo.get("UserProvince");
            String UserArea = (String)userInfo.get("UserArea");
            Boolean Activity = (Boolean)userInfo.get("Activity");

            if (LoginPassordInfo.equals(LoginPassword)) {
                jsonObject.put("flag", "1");
                jsonObject.put("userCode", UserCode);
                jsonObject.put("loginName", LoginName);
                jsonObject.put("userMail", UserMail);
                jsonObject.put("userName", UserName);
                jsonObject.put("userPhone", UserPhone);
                jsonObject.put("description", Description);
                jsonObject.put("provinceInfo", ProvinceInfo);
                jsonObject.put("areaInfo", AreaInfo);
                jsonObject.put("userRole", UserRole);
                jsonObject.put("activity", Activity);
                jsonObject.put("UserProvince", UserProvince);
                jsonObject.put("UserArea", UserArea);
                jsonObject.put("msg", "登陆成功");
            } else {
                jsonObject.put("flag", "0");
                jsonObject.put("msg", "账号或者密码错误");
            }
        }
        return jsonObject.toString();
    }

    //首先需要通过usercode获取用户的详情信息，然后才能进行用户详情信息的获取并更新。
    @RequestMapping(value = "/User/UpdateUserInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String UpdateUserInfo(@RequestBody String UpdateJSON) throws Exception {
        JSONObject UpdateJson = JSONObject.parseObject(UpdateJSON);

        String LoginName = UpdateJson.getString("LoginName");
        String LoginPassword = UpdateJson.getString("LoginPassword");
        String UserMail = UpdateJson.getString("UserMail");
        String UserName = UpdateJson.getString("UserName");
        String UserPhone = UpdateJson.getString("UserPhone");
        String Description = UpdateJson.getString("Description");
        String UserCode = UpdateJson.getString("UserCode");
        String AreaInfo = UpdateJson.getString("AreaInfo");
        Boolean Activity = UpdateJson.getBoolean("Activity");

        logger.info("UpdateUserInfo: "+"-----Start------");
        logger.info("LoginName: "+LoginName+" LoginPassword: "+LoginPassword+" UserName: "+UserName);
        logger.info("UserMail: "+UserMail+" UserPhone: "+UserPhone+" Description: "+Description);
        logger.info("AreaInfo: "+AreaInfo+" UserCode: "+UserCode);
        logger.info("UpdateUserInfo: "+"-----End-----");

        Map<String,Object> param = new HashMap<>();
        param.put("LoginName",LoginName);
        param.put("LoginPassword",LoginPassword);
        param.put("UserMail",UserMail);
        param.put("UserName",UserName);

        param.put("UserPhone",UserPhone);
        param.put("Description",Description);
        param.put("UserCode",UserCode);
        param.put("Activity",Activity);
        param.put("AreaInfo",AreaInfo);

        int result = userServiceImpl.UpdateUserInfo(param);

        JSONObject jsonObject = new JSONObject();

        //result为当前编号的用户数量，一般般都是为1
        if(result==1)
        {
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "更新成功");
        }else {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "更新失败,用户不存在");
        }
        return jsonObject.toString();
    }

    //首先需要通过usercode获取用户的详情信息，然后才能进行用户详情信息的获取并更新。
    @RequestMapping(value = "/User/DeleteUserInfo", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String DeleteUserInfo(@RequestBody String DeleteJSON) throws Exception {
        JSONObject DeleteJson = JSONObject.parseObject(DeleteJSON);
        //获取UserCode即EmployeeCode
        String UserCode = DeleteJson.getString("UserCode");

        logger.info("DeleteUserInfo: "+"-----Start-----");
        logger.info("UserCode: "+UserCode);
        logger.info("DeleteUserInfo: "+"-----End-----");

        Map<String,Object> param = new HashMap<>();
        param.put("UserCode",UserCode);
        long result = userServiceImpl.DeleteUserInfo(param);

        JSONObject jsonObject = new JSONObject();

        if(result!=0)
        {
            jsonObject.put("flag", "1");
            jsonObject.put("msg", "删除成功");
        }else {
            jsonObject.put("flag", "0");
            jsonObject.put("msg", "删除失败,权限不足或者用户不存在");
        }
        return jsonObject.toString();
    }

    //首先需要通过usercode获取用户的详情信息，然后才能进行用户详情信息的获取并更新。
    @RequestMapping(value = "/User/GetUserInfoForAdmin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String GetUserInfo(@RequestBody String GetJSON) throws Exception {
        JSONObject GetJson = JSONObject.parseObject(GetJSON);

        //以下皆使用模糊查询
        //获取需要查询用户/管理员的姓名
        String UserName = GetJson.getString("UserName");
        //获取需要查询用户/管理员的编号
        String UserCode = GetJson.getString("UserCode");
        //获取状态
        Boolean Activity = GetJson.getBoolean("Activity");
        //获取领导唯一标识符
        String LeaderCode = GetJson.getString("LeaderCode");
        int Page = GetJson.getIntValue("Page");
        int Limit = GetJson.getIntValue("Limit");

        System.out.println("UserName: "+UserName+"UserCode: "+UserCode+"Activity: "+Activity);
        Map<String,Object> param = new HashMap<>();
        param.put("UserName",UserName);
        param.put("UserCode",UserCode);
        param.put("Activity",Activity);
        param.put("LeaderCode",LeaderCode);

        PageHelper.startPage(Page,Limit);
        List<Map<String,Object>> results = userServiceImpl.GetUserInfoForAdmin(param,Page,Limit);
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);


        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Map<String,Object> result:results){
            JSONObject paramJSON = new JSONObject();
            paramJSON.put("UserCode",result.get("UserCode"));
            paramJSON.put("UserName",result.get("UserName"));
            paramJSON.put("UserRole",result.get("UserRole"));
            paramJSON.put("UserMail",result.get("UserMail"));
            paramJSON.put("UserPhone",result.get("UserPhone"));
            paramJSON.put("ProvinceInfo",result.get("ProvinceInfo"));
            paramJSON.put("AreaInfo",result.get("AreaInfo"));
            paramJSON.put("Activity",result.get("Activity"));
            paramJSON.put("Description",result.get("Description"));
            paramJSON.put("UserProvince",result.get("UserProvince"));
            paramJSON.put("UserArea",result.get("UserArea"));
            paramJSON.put("LoginName",result.get("LoginName"));
            jsonArray.add(paramJSON);
        }
        Long count= pageInfo.getTotal();
        //不满足一页数
        Long page=0L;
        if(count!=0)
        {
            page = count/Limit;
            if(count%Limit!=0) {
                page++;
            }
        }else{
            page = 0L;
        }
        jsonObject.put("msg",jsonArray);
        jsonObject.put("count",count);
        jsonObject.put("page",page);
        jsonObject.put("index",Page);
        jsonObject.put("limit",Limit);
        return jsonObject.toString();
    }

    //首先需要通过usercode获取用户的详情信息，然后才能进行用户详情信息的获取并更新。
    @RequestMapping(value = "/User/GetUserInfoForRoot", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String GetUserInfoForRoot(@RequestBody String GetJSON) throws Exception {
        JSONObject GetJson = JSONObject.parseObject(GetJSON);
        //以下皆使用模糊查询
        //获取需要查询用户/管理员的姓名
        String UserName = GetJson.getString("UserName");
        //获取需要查询用户/管理员的编号
        String UserCode = GetJson.getString("UserCode");
        int Page = GetJson.getIntValue("Page");
        int Limit = GetJson.getIntValue("Limit");

        Map<String,Object> param = new HashMap<>();
        param.put("UserName",UserName);
        param.put("UserCode",UserCode);

        PageHelper.startPage(Page,Limit);
        List<Map<String,Object>> results = userServiceImpl.GetUserInfoForRoot(param,Page,Limit);
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Map<String,Object> result:results){
            JSONObject paramJSON = new JSONObject();
            paramJSON.put("UserCode",result.get("UserCode"));
            paramJSON.put("UserName",result.get("UserName"));
            paramJSON.put("UserRole",result.get("UserRole"));
            paramJSON.put("UserMail",result.get("UserMail"));
            paramJSON.put("UserPhone",result.get("UserPhone"));
            paramJSON.put("ProvinceInfo",result.get("ProvinceInfo"));
            paramJSON.put("AreaInfo",result.get("AreaInfo"));
            paramJSON.put("Activity",result.get("Activity"));
            paramJSON.put("Description",result.get("Description"));
            paramJSON.put("UserProvince",result.get("UserProvince"));
            paramJSON.put("UserArea",result.get("UserArea"));
            paramJSON.put("LoginName",result.get("LoginName"));
            jsonArray.add(paramJSON);
        }
        Long count= pageInfo.getTotal();
        //不满足一页数
        Long page=0L;
        if(count!=0)
        {
            page = count/Limit;
            if(count%Limit!=0) {
                page++;
            }
        }else{
            page = 0L;
        }
        jsonObject.put("msg",jsonArray);
        jsonObject.put("count",count);
        jsonObject.put("page",page);
        jsonObject.put("index",Page);
        jsonObject.put("limit",Limit);
        return jsonObject.toString();
    }
}

