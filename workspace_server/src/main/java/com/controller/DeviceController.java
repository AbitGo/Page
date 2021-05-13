package com.controller;

import com.alibaba.fastjson.JSONObject;
import com.department.DepartmentService;
import com.device.DeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pojo.*;
import com.utli.PubicMethod;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.utli.SocketParam.*;
import static com.utli.TCPServer.tcpServer;

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
        System.out.println(deviceDeleteInfo.getDepartmentCode());
        List<Map<String,Object>> results = deviceService.searchDevice(deviceDeleteInfo.getDepartmentCode(),index,limit);

        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);
        Long count = pageInfo.getTotal();

        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("查找成功");
        returnMessage.setInfos(results);
        returnMessage.setInfo(PubicMethod.countPage(index,limit,count));
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "获取设备",notes = "使用可选参数查询设备")
    @RequestMapping(value = "/device/getDeviceByUserCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String getDeviceByUserCode(@RequestBody @ApiParam(name = "获取设备",value = "传入参数",required = true)DeviceDeleteOrSearchInfo deviceInfo) throws Exception {
        //首选需要判定当前人员等级
        int index = deviceInfo.getIndex();
        int limit = deviceInfo.getLimit();
        PageHelper.startPage(index,limit);
        ReturnMessage returnMessage = new ReturnMessage();
        List<Map<String,Object>> results = deviceService.getDevice(deviceInfo.getUserCode(),index,limit);

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
    public String AddDeviceTask(@RequestBody @ApiParam(name = "添加任务",value = "传入参数",required = true) TaskSearchByManager taskAddInfo) throws Exception {

        taskAddInfo.setTaskCode("TASK"+System.currentTimeMillis());
        ReturnMessage returnMessage = new ReturnMessage();
        //代码复用.锁具状态为0
        taskAddInfo.setTaskStatus("0");
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


    @ApiOperation(value = "查找任务",notes = "使用必选参数添加任务")
    @RequestMapping(value = "/device/SearchDeviceTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String SearchDeviceTask(@RequestBody @ApiParam(name = "用户查找任务",value = "传入参数",required = true) TaskSearchByManager taskSearchByManager) throws Exception {
        int index = taskSearchByManager.getIndex();
        int limit = taskSearchByManager.getLimit();
        int isUser = taskSearchByManager.getIsUser();
        List<Map<String,Object>> results = new ArrayList<>();

        if(isUser==0){
            results= deviceService.searchTaskByManager(taskSearchByManager,index,limit);
        }else if(isUser==1){
            results = deviceService.searchTaskByProposeCode(taskSearchByManager,index,limit);
        }
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);
        Long count = pageInfo.getTotal();
        Map<String,Object> result = PubicMethod.countPage(index,limit,count);
        ReturnMessage returnMessage = new ReturnMessage("1","任务查找成功",result,results);
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "管理员审核任务",notes = "使用必选参数添加任务")
    @RequestMapping(value = "/device/auditTask", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String auditTask(@RequestBody @ApiParam(name = "用户查找任务",value = "传入参数",required = true) TaskAuditInfo taskAuditInfo) throws Exception {

        int results = deviceService.auditTaskByTaskCode(taskAuditInfo);
        ReturnMessage returnMessage = new ReturnMessage("1","任务审核成功");
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "用户执行任务",notes = "使用必选参数添加任务")
    @RequestMapping(value = "/device/UnlockbyTask", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String UnlockbyTask(@RequestBody @ApiParam(name = "用户执行任务",value = "传入参数",required = true) TaskUnlockInfo taskUnlockInfo) throws Exception {

        TaskUnlockInfo result_task = deviceService.getTaskbyTaskCode(taskUnlockInfo.getTaskCode());
        ReturnMessage returnMessage = new ReturnMessage();

        Map<String,Object> param = new HashMap<>();
        if(!(null==result_task)){
            //当前任务不为空的时候
            if(0==result_task.getTaskStatus()){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("该任务未审核");
            }else if(2==result_task.getTaskStatus()){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("该任务审批未通过");
            }else if(3==result_task.getTaskStatus()){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("该任务已过时");
            }else if(4==result_task.getTaskStatus()){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("该任务已执行过");
            }
            else if(!result_task.getProposerCode().equals(taskUnlockInfo.getProposerCode())){
                returnMessage.setExecuteStatus("0");
                returnMessage.setExecuteMsg("该任务并不属于你");
            } else{

                //
                String result = tcpServer.socketSendData("","01");
                if(result.equals(SendSuccess)){
                    ;
                }else if(result.equals(SendError)){
                    returnMessage.setExecuteStatus("0");
                    returnMessage.setExecuteMsg("设备不在线");
                    return JSONObject.toJSONString(returnMessage);
                }else if(result.equals(TimeOut)){
                    returnMessage.setExecuteStatus("0");
                    returnMessage.setExecuteMsg("设备下发超时.请检查网络");
                    return JSONObject.toJSONString(returnMessage);
                }

                //
                returnMessage.setExecuteStatus("1");
                returnMessage.setExecuteMsg("该任务已下发");
                param.put("taskCode",result_task.getTaskCode());
                param.put("taskStatus",4);
                deviceService.UpdataTaskStatus(param);

                RecordBaseInfo recordBaseInfo = new RecordBaseInfo();
                recordBaseInfo.setDeviceIMEI(result_task.getDeviceIMEI());
                recordBaseInfo.setDoTime(System.currentTimeMillis()/1000);
                recordBaseInfo.setProposeCode(result_task.getProposerCode());
                recordBaseInfo.setRecordType(1);
                recordBaseInfo.setTaskCode(result_task.getTaskCode());
                deviceService.addRecord(recordBaseInfo);
            }
        }
        return JSONObject.toJSONString(returnMessage);
    }

    @ApiOperation(value = "查找记录",notes = "使用必选参数查找记录")
    @RequestMapping(value = "/device/searchRecord", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String searchRecord(@RequestBody @ApiParam(name = "用户查找任务",value = "传入参数",required = true) RecordBaseInfo recordBaseInfo) throws Exception {

        int index = recordBaseInfo.getIndex();
        int limit = recordBaseInfo.getLimit();
        String proposeCode = recordBaseInfo.getProposeCode();
        PageHelper.startPage(index,limit);
        ReturnMessage returnMessage = new ReturnMessage();
        List<Map<String,Object>> results = deviceService.searchRecord(proposeCode,index,limit);

        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);
        Long count = pageInfo.getTotal();
        returnMessage.setExecuteStatus("1");
        returnMessage.setExecuteMsg("查找成功");
        returnMessage.setInfos(results);
        returnMessage.setInfo(PubicMethod.countPage(index,limit,count));
        return JSONObject.toJSONString(returnMessage);
    }




    @RequestMapping(value = "/device/Test", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public String PostCommand(@RequestBody String GetJson) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJson);
        String deviceCode = getJson.getString("deviceCode");
        String deviceData = getJson.getString("deviceData");
        String result = tcpServer.socketSendData(deviceCode,deviceData);
        if(result.equals(SendSuccess)){
            return "下发成功";
        }else if(result.equals(SendError)){
            return "下发失败.设备不在线";
        }else if(result.equals(TimeOut)){
            return "下发失败.设备超时";
        }
        return "下发失败";
    }
}
