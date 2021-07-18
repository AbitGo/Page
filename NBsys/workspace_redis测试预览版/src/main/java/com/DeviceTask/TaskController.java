package com.DeviceTask;

import com.IOT.utils.Constant;
import com.IOT.utils.HttpsUtil;
import com.IOT.utils.JsonUtil;
import com.Redis.RedisService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.user.UserController;
import com.util.PubicMethod;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    TaskService taskService;
    @Autowired
    RedisService redisService;
    @RequestMapping(value = "/Task/AddEffectiveTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String AddEffectiveTask(@RequestBody String AddJSON) throws Exception {
        JSONObject addJson = JSONObject.parseObject(AddJSON);
        //设备唯一标识符
        String DeviceCode = addJson.getString("DeviceCode");
        //设备持有者
        String DeviceOwnner = addJson.getString("DeviceOwnner");
        //设备使用者也就是用户
        String DeviceUser = addJson.getString("DeviceUser");
        //设备可开锁时间
        Long StartTime = addJson.getLong("StartTime");
        //设备不可开锁时间
        Long EndTime = addJson.getLong("EndTime");
        //设备任务描述
        String Desc = addJson.getString("Desc");
        //设备任务唯一标识符
        String TaskCode = "Tsk"+PubicMethod.getAcademeCode();
        Map<String,Object> param = new HashMap<>();
        param.put("DeviceCode",DeviceCode);
        param.put("DeviceOwnner",DeviceOwnner);
        param.put("DeviceUser",DeviceUser);
        param.put("StartTime",StartTime);
        param.put("EndTime",EndTime);
        param.put("Desc",Desc);
        param.put("TaskCode",TaskCode);

        int result = taskService.addEffectiveDeviceTask(param);

        String TableName1 = "StartTime";
        String TableName2 = "EndTime";
        Boolean result_redis1 = redisService.AddZSetKeyAndValue(TableName1,TaskCode,(double) StartTime);
        Boolean result_redis2 = redisService.AddZSetKeyAndValue(TableName2,TaskCode,(double) EndTime);

        JSONObject jsonObject = new JSONObject();
        if(result==1 && result_redis1 && result_redis2){
            jsonObject.put("flag","1");
            jsonObject.put("msg","添加成功");
            jsonObject.put("TaskCode",TaskCode);
        }
        else{
            jsonObject.put("flag","0");
            jsonObject.put("msg","添加失败,请重新添加");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/Task/AddApplyTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String AddApplyTask(@RequestBody String AddJSON) throws Exception {
        JSONObject addJson = JSONObject.parseObject(AddJSON);
        //设备唯一标识符
        String DeviceCode = addJson.getString("DeviceCode");
        //设备持有者
        String DeviceOwnner = addJson.getString("DeviceOwnner");
        //设备使用者也就是用户
        String DeviceUser = addJson.getString("DeviceUser");
        //设备可开锁时间
        Long StartTime = addJson.getLong("StartTime");
        //设备不可开锁时间
        Long EndTime = addJson.getLong("EndTime");
        //设备任务描述
        String Desc = addJson.getString("Desc");
        //设备任务唯一标识符
        String TaskCode = "Tsk"+PubicMethod.getAcademeCode();
        Map<String,Object> param = new HashMap<>();
        param.put("DeviceCode",DeviceCode);
        param.put("DeviceOwnner",DeviceOwnner);
        param.put("DeviceUser",DeviceUser);
        param.put("StartTime",StartTime);
        param.put("EndTime",EndTime);
        param.put("Desc",Desc);
        param.put("TaskCode",TaskCode);

        int result = taskService.addApplyDeviceTask(param);

        JSONObject jsonObject = new JSONObject();
        if(result==1){
            jsonObject.put("flag","1");
            jsonObject.put("msg","申请任务成功");
            jsonObject.put("TaskCode",TaskCode);
        }
        else{
            jsonObject.put("flag","0");
            jsonObject.put("msg","申请任务成功失败,请重新申请");
        }
        return jsonObject.toString();
    }

    //获取任务(已经生效的/已经获批的)
    @RequestMapping(value = "/Task/GetEffectiveTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String GetEffectiveTask(@RequestBody String GetJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJSON);
        //设备持有者
        String UserCode = getJson.getString("UserCode");
        //设备使用者也就是用户
        Integer Status = getJson.getInteger("Status");
        //设备任务唯一标识符
        int Page = getJson.getIntValue("Page");
        int Limit = getJson.getIntValue("Limit");

        Map<String,Object> param = new HashMap<>();
        param.put("UserCode",UserCode);
        param.put("Status",Status);
        param.put("Page",Page);
        param.put("Limit",Limit);

        PageHelper.startPage(Page,Limit);
        List<Map<String,Object>> results = taskService.getEffectiveTask(param,Page,Limit);
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Map<String,Object> result:results){
            JSONObject paramJSON = new JSONObject();
            paramJSON.put("id",result.get("id"));
            paramJSON.put("DeviceCode",result.get("DeviceCode"));
            paramJSON.put("DeviceOwnner",result.get("DeviceOwnner"));
            paramJSON.put("DeviceUser",result.get("DeviceUser"));
            paramJSON.put("StartTime",result.get("StartTime"));
            paramJSON.put("EndTime",result.get("EndTime"));
            paramJSON.put("TaskCode",result.get("TaskCode"));
            paramJSON.put("Description",result.get("Description"));
            paramJSON.put("Status",result.get("Status"));
            paramJSON.put("Activity",result.get("Activity"));
            paramJSON.put("DeviceIMEI",result.get("DeviceIMEI"));
            paramJSON.put("DeviceName",result.get("DeviceName"));

            jsonArray.add(paramJSON);
        }
        Long count= pageInfo.getTotal();
        //不满足一页数
        Long pages=0L;
        if(count!=0)
        {
            pages = count/Limit;
            if(count%Limit!=0) {
                pages++;
            }
        }else{
            pages = 0L;
        }
        jsonObject.put("msg",jsonArray);
        jsonObject.put("count",count);
        jsonObject.put("pages",pages);
        jsonObject.put("index",Page);
        jsonObject.put("limit",Limit);
        return jsonObject.toString();

    }

    //获取用户申请的任务
    @RequestMapping(value = "/Task/GetApplyDeviceTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String GetApplyDeviceTask(@RequestBody String GetJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJSON);
        //设备持有者
        String UserCode = getJson.getString("UserCode");
        //设备任务唯一标识符
        int Page = getJson.getIntValue("Page");
        int Limit = getJson.getIntValue("Limit");

        Map<String,Object> param = new HashMap<>();
        param.put("UserCode",UserCode);
        param.put("Page",Page);
        param.put("Limit",Limit);

        PageHelper.startPage(Page,Limit);
        List<Map<String,Object>> results = taskService.getApplyDeviceTask(param,Page,Limit);
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Map<String,Object> result:results){
            JSONObject paramJSON = new JSONObject();
            paramJSON.put("id",result.get("id"));
            paramJSON.put("DeviceCode",result.get("DeviceCode"));
            paramJSON.put("DeviceOwnner",result.get("DeviceOwnner"));
            paramJSON.put("DeviceUser",result.get("DeviceUser"));
            paramJSON.put("StartTime",result.get("StartTime"));
            paramJSON.put("EndTime",result.get("EndTime"));
            paramJSON.put("TaskCode",result.get("TaskCode"));
            paramJSON.put("Description",result.get("Description"));
            paramJSON.put("Status",result.get("Status"));
            paramJSON.put("Activity",result.get("Activity"));
            paramJSON.put("DeviceUserName",result.get("DeviceUserName"));

            jsonArray.add(paramJSON);
        }
        Long count= pageInfo.getTotal();
        //不满足一页数
        Long pages=0L;
        if(count!=0)
        {
            pages = count/Limit;
            if(count%Limit!=0) {
                pages++;
            }
        }else{
            pages = 0L;
        }
        jsonObject.put("msg",jsonArray);
        jsonObject.put("count",count);
        jsonObject.put("pages",pages);
        jsonObject.put("index",Page);
        jsonObject.put("limit",Limit);
        return jsonObject.toString();

    }
    //批准用户申请的任务
    @RequestMapping(value = "/Task/ApproveApplyDeviceTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String ApproveApplyDeviceTask(@RequestBody String GetJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJSON);
        //申请任务唯一标识符
        String TaskCode = getJson.getString("TaskCode");
        String TaskId = getJson.getString("id");

        logger.info("ApproveApplyDeviceTask: "+"-----Start-----");
        logger.info("TaskCode: "+TaskCode+" TaskId: "+TaskId);
        logger.info("ApproveApplyDeviceTask: "+"-----End-----");

        Map<String,Object> param = new HashMap<>();
        param.put("TaskCode",TaskCode);
        param.put("TaskId",TaskId);
        //这个id使用来返回更改的id的不是上面的taskId
        param.put("id",0);
        param.put("StartTime",0);
        param.put("EndTime",0);

        int results = taskService.changeTaskApplyToEffective(param);

        JSONObject jsonObject = new JSONObject();

        if(results != 0)
        //申请任务通过审核
        {
            System.out.println("results:"+param.get("id"));
            String TableName1 = "StartTime";
            String TableName2 = "EndTime";

            Boolean result_redis1 = redisService.AddZSetKeyAndValue(TableName1,TaskCode,Double.valueOf(param.get("StartTime").toString()));
            Boolean result_redis2 = redisService.AddZSetKeyAndValue(TableName2,TaskCode,Double.valueOf(param.get("EndTime").toString()));

            jsonObject.put("msg","审核成功");
            jsonObject.put("flag","1");
        }else{
            jsonObject.put("msg","审核失败,请重新审核,或者该任务不存在");
            jsonObject.put("flag","0");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/Task/ActiveTheUnlockTask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String PerformTheUnlockTask(@RequestBody String GetJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJSON);
        //申请任务唯一标识符
        String TaskCode = getJson.getString("TaskCode");
        Long CreateTime = System.currentTimeMillis()/1000;
        String UserCode = getJson.getString("UserCode");
        String serviceId = getJson.getString("serviceId");
        String method = getJson.getString("method");
        String paras = getJson.getString("paras");

        String RecordCode = "Rec" + PubicMethod.getAcademeCode();

        JSONObject jsonObject = new JSONObject();
        Map<String,Object> result = taskService.getTaskByCode(TaskCode);
        if(result==null)
        {
            jsonObject.put("msg","该任务不存在,请刷新");
            jsonObject.put("flag","0");
            return jsonObject.toString();
        }else{
            if((int)result.get("Activity")==0){
                jsonObject.put("msg","该任务还未审核通过,请通知上级管理员审核该任务");
                jsonObject.put("flag","0");
                return jsonObject.toString();
            }
            if((int) result.get("Status")==0){
                jsonObject.put("msg","该任务执行时间未达到");
                jsonObject.put("flag","0");
                return jsonObject.toString();
            }else if((int) result.get("Status")==2){
                jsonObject.put("msg","该任务执行时间已过期");
                jsonObject.put("flag","0");
                return jsonObject.toString();
            }
        }


        //Command code start
        String DeviceCode = (String) result.get("DeviceCode");
        String DeviceOwnner = (String) result.get("DeviceOwnner");

        String deviceId = taskService.SearchDianXinCodeByDeviceCode(DeviceCode);
        Map<String,String> CommandParam = new HashMap<>();
        CommandParam.put("deviceId",deviceId);
        CommandParam.put("serviceId",serviceId);
        CommandParam.put("method",method);
        CommandParam.put("paras",paras);
        String CommandReult = CreateDeviceCommand(CommandParam);

        //Command code end


        logger.info("PerformTheUnlockTask: "+System.currentTimeMillis()+"-----Start-----");
        logger.info("TaskCode: "+TaskCode+" UserCode: "+UserCode);
        logger.info("serviceId: "+serviceId+" paras: "+paras);
        logger.info("PerformTheUnlockTask: "+"-----End-----");

        //Command request failed
        if(CommandReult.equals("null"))
        {
            jsonObject.put("msg","执行下发失败,请查看任务是否有效");
            jsonObject.put("flag","0");
            return jsonObject.toString();
        }
        //if(CommandReult.equals("SENT"))
        //Command request success
        Map<String,Object> param = new HashMap<>();
        param.put("TaskCode",TaskCode);
        param.put("CreateTime",CreateTime);
        param.put("DeviceCode",DeviceCode);
        param.put("DeviceOwnner",DeviceOwnner);
        //执行者的id即用户id
        param.put("PerformerCode",UserCode);
        param.put("RecordCode",RecordCode);

        int results = taskService.addUnlockRecord(param);
        jsonObject.put("msg","执行下发成功,任务记录已保存");
        jsonObject.put("flag","1");
        if(results != 0)
        //申请任务通过审核
        {
            jsonObject.put("msg","执行下发成功,任务记录已保存");
            jsonObject.put("flag","1");
        }else{
            jsonObject.put("msg","执行下发成功,人物记录未保存,请联系管理员");
            jsonObject.put("flag","1");
        }
        return jsonObject.toString();
    }

    //获取用户申请的任务
    @RequestMapping(value = "/Task/GetRecordByUserCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String GetRecordByUserCode(@RequestBody String GetJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJSON);
        //设备持有者
        String UserCode = getJson.getString("UserCode");
        //设备任务唯一标识符
        int Page = getJson.getIntValue("Page");
        int Limit = getJson.getIntValue("Limit");

        Map<String,Object> param = new HashMap<>();
        param.put("UserCode",UserCode);
        param.put("Page",Page);
        param.put("Limit",Limit);

        PageHelper.startPage(Page,Limit);
        List<Map<String,Object>> results = taskService.searchUnlockRecord(param,Page,Limit);
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Map<String,Object> result:results){
            JSONObject paramJSON = new JSONObject();
            paramJSON.put("TaskCode",result.get("TaskCode"));
            paramJSON.put("DeviceCode",result.get("DeviceCode"));
            paramJSON.put("PerformerCode",result.get("PerformerCode"));
            paramJSON.put("RecordResult",result.get("RecordResult"));
            paramJSON.put("CreateTime",result.get("CreateTime"));
            paramJSON.put("DeviceOwnnerName",result.get("DeviceOwnnerName"));
            paramJSON.put("PerformerName",result.get("PerformerName"));
            paramJSON.put("DeviceOwnner",result.get("DeviceOwnner"));
            jsonArray.add(paramJSON);
        }
        Long count= pageInfo.getTotal();
        //不满足一页数
        Long pages=0L;
        if(count!=0)
        {
            pages = count/Limit;
            if(count%Limit!=0) {
                pages++;
            }
        }else{
            pages = 0L;
        }
        jsonObject.put("msg",jsonArray);
        jsonObject.put("count",count);
        jsonObject.put("pages",pages);
        jsonObject.put("index",Page);
        jsonObject.put("limit",Limit);
        return jsonObject.toString();
    }

    public String CreateDeviceCommand(Map<String,String> param) throws Exception {
        //param start
        String deviceId = param.get("deviceId");
        String serviceId = param.get("serviceId");
        String method = param.get("method");
        ObjectNode paras = JsonUtil.convertObject2ObjectNode(param.get("paras"));
        //param start

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();
        String accessToken = Constant.accessToken;

        String urlCreateDeviceCommand = Constant.CREATE_DEVICE_CMD;
        String appId = Constant.APPID;
        String callbackUrl = Constant.REPORT_CMD_EXEC_RESULT_CALLBACK_URL;

        Integer expireTime = 0;
        Integer maxRetransmit = 3;
        Map<String, Object> paramCommand = new HashMap<>();
        paramCommand.put("serviceId", serviceId);
        paramCommand.put("method", method);
        paramCommand.put("paras", paras);

        Map<String, Object> paramCreateDeviceCommand = new HashMap<>();
        paramCreateDeviceCommand.put("deviceId", deviceId);
        paramCreateDeviceCommand.put("command", paramCommand);
        paramCreateDeviceCommand.put("callbackUrl", callbackUrl);
        paramCreateDeviceCommand.put("expireTime", expireTime);
        paramCreateDeviceCommand.put("maxRetransmit", maxRetransmit);

        String jsonRequest = JsonUtil.jsonObj2Sting(paramCreateDeviceCommand);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        HttpResponse responseCreateDeviceCommand = httpsUtil.doPostJson(urlCreateDeviceCommand, header, jsonRequest);
        String responseBody = httpsUtil.getHttpResponseBody(responseCreateDeviceCommand);
        JSONObject jsonObject = JSONObject.parseObject(responseBody);

        String status = (String)jsonObject.get("status");
        if(status==null){
            //该状态值为空,可以直接返回null字符串
            status="null";
        }
        return status;
    }


}
