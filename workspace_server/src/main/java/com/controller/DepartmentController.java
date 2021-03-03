package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.department.DepartmentService;
import com.pojo.*;
import com.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @Transactional
    @RequestMapping(value = "/department/departmentCreate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userRegister(@RequestBody String paramJson) {
        ReturnMessage returnMessage = new ReturnMessage();

        //前端传输过来的数据必须经过验证
        DepartmentCreateInfo departmentCreateInfo = JSON.parseObject(paramJson, DepartmentCreateInfo.class);
        String now = System.currentTimeMillis()+"";
        String deptCode = "DEPT"+now;
        departmentCreateInfo.setDepartmentCode(deptCode);
        //人员唯一时间戳由部门创建时间戳以及用创建时间戳
        String personnelCode = "PERO"+now+departmentCreateInfo.getDepartmentRoot().substring(4);
        departmentCreateInfo.setPersonnelCode(personnelCode);
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

    /*
    *修改部门数据
     */
    @Transactional
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

    /*
     *添加部门人员
     */
    @Transactional
    @RequestMapping(value = "/department/personAuthorization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String personAuthorization(@RequestBody String paramJson) {
        ReturnMessage returnMessage = new ReturnMessage();
        PersonAuthonizationInfo personAuthonizationInfo = JSON.parseObject(paramJson, PersonAuthonizationInfo.class);
        String personnelCode = "PERO"+personAuthonizationInfo.getDepartmentCode().substring(4)+personAuthonizationInfo.getUserCode().substring(4);
        personAuthonizationInfo.setPersonnelCode(personnelCode);
        try {
            departmentService.addPersonAuthorization(personAuthonizationInfo);
        }catch (Exception e){
            //e.printStackTrace();
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("添加部门人员失败.此人已经在部门内部");
            return JSONObject.toJSONString(returnMessage);
        }
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("添加部门人员成功");
        return JSONObject.toJSONString(returnMessage);
    }

    /*
     *删除部门人员
     */
    @Transactional
    @RequestMapping(value = "/department/personDel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String personDel(@RequestBody String paramJson) {
        ReturnMessage returnMessage = new ReturnMessage();
        PersonDelInfo personAuthonizationInfo = JSON.parseObject(paramJson, PersonDelInfo.class);
        Map<String,String> temp = new HashMap<>();
        //获取需要删除者和被删除者之间的等级关系
        temp.put("departmentCode",personAuthonizationInfo.getDepartmentCode());
        temp.put("userCode",personAuthonizationInfo.getUserCodeSelf());
        Map<String,Object> resultSelf = departmentService.personAuthorization(temp);
        temp.put("userCode",personAuthonizationInfo.getUserCodeDel());
        Map<String,Object> resultDel = departmentService.personAuthorization(temp);

        if(resultDel==null || resultSelf ==null){
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("删除部门人员失败.此人已经不在部门内部");
            return JSONObject.toJSONString(returnMessage);
        }else{
            if((int)resultDel.get("userRole")==1){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("删除部门人员失败.此人是部门创建者");
            }else if((int)resultDel.get("userRole")<(int)resultSelf.get("userRole")){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("删除部门人员失败.权限不足");
            }else{
                departmentService.delPerson(personAuthonizationInfo);
                returnMessage.setExecuteStatus("1");
                returnMessage.setExecuteMsg("删除部门人员成功");
            }
            return JSONObject.toJSONString(returnMessage);
        }
    }

    @RequestMapping(value = "/department/searchDepartment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String searchDepartment(@RequestBody String paramJson) {
        ReturnMessage returnMessage = new ReturnMessage();
        PersonDelInfo personAuthonizationInfo = JSON.parseObject(paramJson, PersonDelInfo.class);
        Map<String,String> temp = new HashMap<>();
        //获取需要删除者和被删除者之间的等级关系
        temp.put("departmentCode",personAuthonizationInfo.getDepartmentCode());
        temp.put("userCode",personAuthonizationInfo.getUserCodeSelf());
        Map<String,Object> resultSelf = departmentService.personAuthorization(temp);
        temp.put("userCode",personAuthonizationInfo.getUserCodeDel());
        Map<String,Object> resultDel = departmentService.personAuthorization(temp);

        if(resultDel==null || resultSelf ==null){
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("删除部门人员失败.此人已经不在部门内部");
            return JSONObject.toJSONString(returnMessage);
        }else{
            if((int)resultDel.get("userRole")==1){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("删除部门人员失败.此人是部门创建者");
            }else if((int)resultDel.get("userRole")<(int)resultSelf.get("userRole")){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("删除部门人员失败.权限不足");
            }else{
                departmentService.delPerson(personAuthonizationInfo);
                returnMessage.setExecuteStatus("1");
                returnMessage.setExecuteMsg("删除部门人员成功");
            }
            return JSONObject.toJSONString(returnMessage);
        }
    }
}
