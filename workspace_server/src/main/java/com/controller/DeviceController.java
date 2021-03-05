package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.department.DepartmentService;
import com.device.DeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.DeviceAddInfo;
import com.pojo.DeviceDeleteOrSearchInfo;
import com.pojo.ReturnMessage;
import com.pojo.TaskAddAndSearchInfo;
import com.utli.PubicMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/3/1 19:02
 */
@Api(tags = "设备数据接口")
@RestController
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    DepartmentService departmentService;

    @ApiOperation(value = "添加设备",notes = "只有部门创建以及部门管理者才可添加或设备")
    @Transactional
    @RequestMapping(value = "/device/addDevice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String addDevice(@RequestBody @ApiParam(name = "添加设备",value = "传入参数",required = true) DeviceAddInfo deviceAddInfo) throws Exception {
        ReturnMessage returnMessage = new ReturnMessage();
        //首选需要判定当前人员等级
        Map<String, String> temp = new HashMap<>();
        temp.put("departmentCode", deviceAddInfo.getDepartmentCode());
        temp.put("userCode", deviceAddInfo.getUserCode());

        Map<String, Object> resultSelf = departmentService.personAuthorization(temp);
        if (resultSelf == null) {
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("人员不存在");
            return JSONObject.toJSONString(returnMessage);
        } else {
            if ((int) resultSelf.get("userRole") == 3) {
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("权限不足");
                return JSONObject.toJSONString(returnMessage);
            }
        }
        try {
            deviceService.addDevice(deviceAddInfo);
        } catch (Exception e) {
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("设备IMEI已被注册");
            return JSONObject.toJSONString(returnMessage);
        }
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("设备注册成功");
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "删除设备",notes = "使用必选参数删除设备")
    @Transactional
    @RequestMapping(value = "/device/deleteDevice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String deleteDevice(@RequestBody @ApiParam(name = "查询设备",value = "传入参数",required = true) DeviceDeleteOrSearchInfo deviceDeleteInfo) throws Exception {
        ReturnMessage returnMessage = new ReturnMessage();
        //首选需要判定当前人员等级
        Map<String, String> temp = new HashMap<>();
        temp.put("departmentCode", deviceDeleteInfo.getDepartmentCode());
        temp.put("userCode", deviceDeleteInfo.getUserCode());
        Map<String, Object> resultSelf = departmentService.personAuthorization(temp);
        if (resultSelf == null) {
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("人员不存在");
            return JSONObject.toJSONString(returnMessage);
        } else if ((int) resultSelf.get("userRole") == 3) {
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("权限不足");
            return JSONObject.toJSONString(returnMessage);
        }
        deviceService.deleteDevice(deviceDeleteInfo);
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("设备删除成功");
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "查询设备",notes = "使用可选参数查询设备")
    @RequestMapping(value = "/device/SearchDevice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String SearchDevice(@RequestBody @ApiParam(name = "查询设备",value = "传入参数",required = true)DeviceDeleteOrSearchInfo deviceDeleteInfo) throws Exception {
        //首选需要判定当前人员等级
        int index = deviceDeleteInfo.getIndex();
        int limit = deviceDeleteInfo.getLimit();
        PageHelper.startPage(index,limit);
        ReturnMessage returnMessage = new ReturnMessage();
        List<Map<String,Object>> results = deviceService.searchDevice(deviceDeleteInfo,index,limit);

        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);
        Long count = pageInfo.getTotal();

        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("查找成功");
        returnMessage.setInfos(results);
        returnMessage.setInfo(PubicMethod.countPage(index,limit,count));
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "添加任务",notes = "使用必选参数添加任务")
    @RequestMapping(value = "/device/AddDeviceTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String AddDeviceTask(@RequestBody @ApiParam(name = "添加任务",value = "传入参数",required = true) TaskAddAndSearchInfo taskAddInfo) throws Exception {

        taskAddInfo.setTaskCode("TASK"+System.currentTimeMillis());
        ReturnMessage returnMessage = new ReturnMessage();
        //代码复用.锁具状态为0
        taskAddInfo.setTaskStatus(0);
        taskAddInfo.setTaskTime(System.currentTimeMillis()/1000);
        List<Map<String,Object>> results = deviceService.searchTaskByProposeCode(taskAddInfo,1,1);
        if(results.size() !=0 ){
            returnMessage.setExecuteStatus("0");
            returnMessage.setExecuteMsg("请勿重复提交");
            return JSONObject.toJSONString(returnMessage);
        }else {
            int result = deviceService.addTask(taskAddInfo);
            returnMessage.setExecuteStatus("1");
            returnMessage.setExecuteMsg("任务申请成功");
            return JSONObject.toJSONString(returnMessage);
        }
    }

    @ApiOperation(value = "用户查找任务",notes = "使用必选参数添加任务")
    @RequestMapping(value = "/device/SearchDeviceTaskByUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String SearchDeviceTaskByUser(@RequestBody @ApiParam(name = "用户查找任务",value = "传入参数",required = true) TaskAddAndSearchInfo taskAddInfo) throws Exception {


        int index = taskAddInfo.getIndex();
        int limit = taskAddInfo.getLimit();
        List<Map<String,Object>> results = deviceService.searchTaskByProposeCode(taskAddInfo,index,limit);
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);
        Long count = pageInfo.getTotal();
        Map<String,Object> result = PubicMethod.countPage(index,limit,count);
        ReturnMessage returnMessage = new ReturnMessage("1","任务查找成功",result,results);
        return JSONObject.toJSONString(returnMessage);
    }
}
