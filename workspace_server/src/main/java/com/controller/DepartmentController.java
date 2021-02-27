package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.department.DepartmentService;
import com.pojo.DepartmentCreateInfo;
import com.pojo.DepartmentUpdateInfo;
import com.pojo.ReturnMessage;
import com.pojo.UserRegisterInfo;
import com.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value = "/department/departmentCreate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userRegister(@RequestBody String paramJson) {
        ReturnMessage returnMessage = new ReturnMessage();

        //前端传输过来的数据必须经过验证
        DepartmentCreateInfo departmentCreateInfo = JSON.parseObject(paramJson, DepartmentCreateInfo.class);
        departmentCreateInfo.setDepartmentCode("DEPT"+System.currentTimeMillis());
        departmentCreateInfo.setPersonnelCode("PERO"+System.currentTimeMillis());
        try {
            departmentService.departmentCreate(departmentCreateInfo);
        }catch (Exception e){
            //e.printStackTrace();
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("创建失败.该部门已被创建");
            return JSONObject.toJSONString(returnMessage);
        }
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("创建成功");
        return JSONObject.toJSONString(returnMessage);
    }

    @RequestMapping(value = "/department/departmentUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String departmentUpdateName(@RequestBody String paramJson) {
        ReturnMessage returnMessage = new ReturnMessage();
        DepartmentUpdateInfo departmentUpdateInfo = JSON.parseObject(paramJson, DepartmentUpdateInfo.class);
        try {
            departmentService.departmentUpdate(departmentUpdateInfo);
        }catch (Exception e){
            //e.printStackTrace();
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("修改失败.部门名已被使用");
            return JSONObject.toJSONString(returnMessage);
        }
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("修改成功");
        return JSONObject.toJSONString(returnMessage);
    }

    @RequestMapping(value = "/department/personAuthorization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String personAuthorization(@RequestBody String paramJson) {
        ReturnMessage returnMessage = new ReturnMessage();
        DepartmentUpdateInfo departmentUpdateInfo = JSON.parseObject(paramJson, DepartmentUpdateInfo.class);
        try {
            departmentService.departmentUpdate(departmentUpdateInfo);
        }catch (Exception e){
            //e.printStackTrace();
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("修改失败.部门名已被使用");
            return JSONObject.toJSONString(returnMessage);
        }
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("修改成功");
        return JSONObject.toJSONString(returnMessage);
    }
}
