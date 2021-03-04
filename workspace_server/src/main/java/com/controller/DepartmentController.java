package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.department.DepartmentService;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.user.UserService;
import com.utli.PubicMethod;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "部门创建",notes = "使用必选参数用户创建")
    @Transactional
    @RequestMapping(value = "/department/departmentCreate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String userRegister(@RequestBody @ApiParam(name = "查询设备",value = "传入参数",required = true) DepartmentCreateInfo departmentCreateInfo) {
        ReturnMessage returnMessage = new ReturnMessage();

        //前端传输过来的数据必须经过验证
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
    @ApiOperation(value = "部门修改",notes = "使用必选参数部门修改")
    @Transactional
    @RequestMapping(value = "/department/departmentUpdate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String departmentUpdateName(@RequestBody @ApiParam(name = "查询设备",value = "传入参数",required = true) DepartmentUpdateInfo departmentUpdateInfo) {
        ReturnMessage returnMessage = new ReturnMessage();
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
    @ApiOperation(value = "添加部门人员",notes = "使用必选参数添加部门人员")
    @Transactional
    @RequestMapping(value = "/department/personAuthorization", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String personAuthorization(@RequestBody @ApiParam(name = "添加部门人员",value = "传入参数",required = true)PersonAuthonizationInfo personAuthonizationInfo) {
        ReturnMessage returnMessage = new ReturnMessage();
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
    @ApiOperation(value = "删除部门人员",notes = "使用必选参数删除部门人员")
    @Transactional
    @RequestMapping(value = "/department/personDel", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String personDel(@RequestBody @ApiParam(name = "添加部门人员",value = "传入参数",required = true)PersonDelInfo personAuthonizationInfo) {
        ReturnMessage returnMessage = new ReturnMessage();
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

    @Transactional
    @ApiOperation(value = "查找部门",notes = "用户使用部门名字、部门唯一表示查找部门-使用分页")
    @RequestMapping(value = "/department/searchDepartment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String searchDepartment(@RequestBody @ApiParam(name = "搜寻部门",value = "传入参数",required = true) DepartmentSearch departmentSearch) {
        ReturnMessage returnMessage = new ReturnMessage();
        int index = departmentSearch.getIndex();
        int limit = departmentSearch.getLimit();
        List<Map<String,Object>> results = departmentService.searchDepartment(departmentSearch,index,limit);
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);
        Long count = pageInfo.getTotal();

        Map<String,Object> result = PubicMethod.countPage(index,limit,count);
        returnMessage.setInfo(result);
        returnMessage.setInfos(results);
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("查找部门成功");
        return JSONObject.toJSONString(returnMessage);
    }
}
