package com.DeviceData;

import com.IOT.utils.Constant;
import com.IOT.utils.HttpsUtil;
import com.IOT.utils.JsonUtil;
import com.IOT.utils.StreamClosedHttpResponse;
import com.Redis.RedisService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.PubicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DeviceController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    RedisService redisService;

    //通过唯一硬件标识符添加设备
    @RequestMapping(value = "/Device/addDeviceByIMEI", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String addDeviceByIMEI(@RequestBody String AddJSON) throws Exception {
        JSONObject AddJson = JSONObject.parseObject(AddJSON);
        String DeviceIMEI = AddJson.getString("DeviceIMEI");
        String DeviceOwnner = AddJson.getString("DeviceOwnner");
        String DeviceName = AddJson.getString("DeviceName");
        String DeviceUser = AddJson.getString("DeviceUser");
        String DeviceCode = "Dev"+PubicMethod.getAcademeCode();

        String DianXinCode = deviceService.getDeviceByDeviceIMEI(DeviceIMEI);
        if(DianXinCode!=null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg","添加失败,设备已存在");
            jsonObject.put("flag","0");
            return jsonObject.toString();
        }

        DianXinCode = AddDeviceFromDX(DeviceIMEI);
//将电信返回的唯一标识符打印出来
//        System.out.println("DianXinCode:"+DianXinCode);
        if(DianXinCode.equals("null")){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg","电信平台添加设备失败,请联系管理员");
            jsonObject.put("flag","0");
            return jsonObject.toString();
        }

        Map<String,Object> param = new HashMap<>();
        param.put("DeviceIMEI",DeviceIMEI);
        param.put("DeviceOwnner",DeviceOwnner);
        param.put("DeviceCode",DeviceCode);
        param.put("DeviceName",DeviceName);
        param.put("DeviceUser",DeviceUser);
        param.put("DianXinCode",DianXinCode);

        JSONObject jsonObject = new JSONObject();

        try {
            int result = deviceService.addDeviceByUserCode(param);
            if(result==1)
            {
                jsonObject.put("msg","添加成功,请查看设备列表");
                jsonObject.put("flag","1");
            }
        }catch (Exception e){
            jsonObject.put("msg","添加失败,设备已存在");
            jsonObject.put("flag","0");
        }
        return jsonObject.toString();
    }
    public String AddDeviceFromDX(String DeviceIMEI) throws Exception {

        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication.get token
        String accessToken = Constant.accessToken;

        //Please make sure that the following parameter values have been modified in the Constant file.
        String appId = Constant.APPID;
        String urlRegisterDirectConnectedDevice = Constant.REGISTER_DIRECT_CONNECTED_DEVICE;

        //please replace the verifyCode and nodeId and timeout, when you call this interface.
        String verifyCode = DeviceIMEI;
        String nodeId = verifyCode;
        Integer timeout = 0;

        String manufacturerId= "1e1e44510d3141039ddae66bd45c51d5";
        String manufacturerName = "String_lite";
        String deviceType = "DoorLock";
        String model = "JQTX_NBLOCK";
        String protocolType = "CoAP";

        Map<String, Object> paramDeviceInfo = new HashMap<>();
        paramDeviceInfo.put("manufacturerId", manufacturerId);
        paramDeviceInfo.put("manufacturerName", manufacturerName);
        paramDeviceInfo.put("deviceType", deviceType);
        paramDeviceInfo.put("model", model);
        paramDeviceInfo.put("protocolType", protocolType);

        Map<String, Object> paramReg = new HashMap<>();
        paramReg.put("verifyCode", verifyCode.toUpperCase());
        paramReg.put("nodeId", nodeId.toUpperCase());
        paramReg.put("deviceInfo", paramDeviceInfo);
        paramReg.put("timeout", timeout);

        String jsonRequest = JsonUtil.jsonObj2Sting(paramReg);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseRegisterDirectConnectedDevice = httpsUtil.doPostJsonGetStatusLine(urlRegisterDirectConnectedDevice, header, jsonRequest);

        String responseBody = responseRegisterDirectConnectedDevice.getContent();
        JSONObject jsonObject = JSONObject.parseObject(responseBody);
        String deviceId = (String) jsonObject.get("deviceId");
        if(deviceId==null){
            //该状态值为空,可以直接返回null字符串
            deviceId="null";
        }
        return deviceId;
    }

    //通过硬件持有者查找设备
    @RequestMapping(value = "/Device/getDeviceByUserCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String getDeviceByUserCode(@RequestBody String AddJSON) throws Exception {
        JSONObject AddJson = JSONObject.parseObject(AddJSON);
        String UserCode = AddJson.getString("UserCode");
        int Page = AddJson.getIntValue("Page");
        int Limit = AddJson.getIntValue("Limit");

        Map<String,Object> param = new HashMap<>();
        param.put("UserCode",UserCode);

        PageHelper.startPage(Page,Limit);
        List<Map<String,Object>> results = deviceService.getDeviceByUserCode(param,Page,Limit);
        //设置返回的总记录数
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<>(results);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Map<String,Object> result:results){
            JSONObject paramJSON = new JSONObject();
            paramJSON.put("DeviceIMEI",result.get("DeviceIMEI"));
            paramJSON.put("DeviceName",result.get("DeviceName"));
            paramJSON.put("DeviceCode",result.get("DeviceCode"));
            paramJSON.put("DeviceOwnner",result.get("DeviceOwnner"));
            paramJSON.put("DeviceUser",result.get("DeviceUser"));
            paramJSON.put("UserName",result.get("UserName"));
            paramJSON.put("OwnnerName",result.get("OwnnerName"));
            paramJSON.put("DianXinCode",result.get("DianXinCode"));

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

    @RequestMapping(value = "/Device/getDeviceData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private String getDeviceData(@RequestBody String DeviceTable) throws Exception {
        JSONObject DataJSON = JSONObject.parseObject(DeviceTable);
        int DataLen =DataJSON.getInteger("Length");
        JSONArray jsonArray = DataJSON.getJSONArray("DianXinCodeList");
        List<String> DeviceIdList = new ArrayList<>();
        List<Map<String, Object>> DeviceDataList = new ArrayList<>();
        for(int i=0;i<DataLen;i++)
        {
            JSONObject param = jsonArray.getJSONObject(i);
            DeviceIdList.add(param.getString("DianXinCode"));
        }
        for(String DeviceId:DeviceIdList){
            DeviceDataList.add(redisService.GetHashKeyAndValue("deviceId:"+DeviceId));
        }
        //开始拼接数据
        JSONObject result = new JSONObject();

        JSONArray results = new JSONArray();
        for(Map<String,Object> data:DeviceDataList){
            JSONObject paramJSON = new JSONObject();
            paramJSON.put("deviceId",data.get("deviceId"));
            paramJSON.put("SS_staus",data.get("SS_staus"));
            paramJSON.put("mc_staus",data.get("mc_staus"));
            paramJSON.put("shuijing",data.get("shuijing"));
            paramJSON.put("yangan",data.get("yangan"));
            paramJSON.put("zhengdong",data.get("zhengdong"));
            paramJSON.put("voltage",data.get("voltage"));
            paramJSON.put("dbm",data.get("dbm"));
            paramJSON.put("temp",data.get("temp"));
            paramJSON.put("humi",data.get("humi"));
            paramJSON.put("updataTime",data.get("updataTime"));
            paramJSON.put("lock_number",data.get("lock_number"));

            results.add(paramJSON);
        }
        result.put("msg",results);
        return result.toString();
    }

    //该函数是用来删除设备信息的,因为redis中存在着设备的历史数据
    //清空释放内存空间
    @RequestMapping(value = "/Device/DelDeviceData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String DelDeviceData(@RequestBody String getJson) throws Exception {
        JSONObject DataJson = JSONObject.parseObject(getJson);
        String param = DataJson.getString("deviceId");
        //deviceId:
        Boolean result = redisService.RemoveHashKeyAndValue("deviceId:"+param);

        //这里需要对result进行判断,是否删除
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag","1");
        jsonObject.put("msg","已经删除成功");

        return jsonObject.toString();
    }

    //该函数是用来删除设备信息的,因为redis中存在着设备的历史数据
    //清空释放内存空间
    @RequestMapping(value = "/Device/DelDeviceByDeviceCode", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String DelDeviceByDeviceCode(@RequestBody String dataJson) throws Exception {
        JSONObject DataJson = JSONObject.parseObject(dataJson);
        String DeviceCode = DataJson.getString("DeviceCode");

        String deviceId = deviceService.getDeviceByDeviceCode(DeviceCode);
//        System.out.println("deviceId:"+deviceId);
        String result = DeleteDevice(deviceId);
        if(result.equals("ok"))
        {
            deviceService.deleteDeviceByDeviceCode(DeviceCode);
        }else
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("flag","0");
            jsonObject.put("msg","电信平台删除失败,请尝试再次删除或联系管理员");
            return jsonObject.toString();
        }
        //这里需要对result进行判断,是否删除
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag","1");
        jsonObject.put("msg","已经删除成功");
        return jsonObject.toString();
    }
    public String DeleteDevice(String deviceId) throws Exception {

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();
        String accessToken = Constant.accessToken;
        String appId = Constant.APPID;

        String urlDeleteDirectConnectedDevice = Constant.DELETE_DIRECT_CONNECTED_DEVICE + "/" +deviceId;

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseDeleteDirectConnectedDevice = httpsUtil.doDeleteWithParasGetStatusLine(urlDeleteDirectConnectedDevice, null, header);

        String result = "null";
        String Response =responseDeleteDirectConnectedDevice.getStatusLine().toString();
        //成功Status Code: 204 No Content
        //
        if(Response.contains("204")){

            result = "ok";
        }
        return result;
    }
}
