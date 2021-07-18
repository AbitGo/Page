package com.controller;

import com.MBService.ParkingService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pojo.ElemPojo;
import com.util.Constant;
import com.util.PubicMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.*;

@RestController
public class TotleController {
    @Autowired
    ParkingService parkingService;

    @RequestMapping(value = "/EasyParking/addBuildElem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String addBuildElem(@RequestBody String AddJSON) throws Exception {
        JSONObject AddJson = JSONObject.parseObject(AddJSON);
        String Depotname = AddJson.getString("Depotname");
        int storey = AddJson.getIntValue("storey");
        double width = AddJson.getDoubleValue("width");
        double height = AddJson.getDoubleValue("height");

        String AreaId = "AreId" + PubicMethod.getAcademeCode().substring(7);
        Map<String, Object> param = new HashMap<>();
        param.put("Depotname", Depotname);
        param.put("storey", storey);
        param.put("width", width);
        param.put("height", height);
        param.put("AreaId", AreaId);

        JSONObject addResult = new JSONObject();
        int result_addBuildAction = parkingService.addBuildElem(param);
        if (result_addBuildAction != 1) {
            addResult.put("flag", "0");
            addResult.put("msg", "插入失败");
            return addResult.toString();
        }

        //System.out.print("result_addBuildAction"+result_addBuildAction);
        String paramList = AddJson.getString("paths");
        JSONObject jo1 = JSONObject.parseObject(paramList);

        List<ElemPojo> IDNameList = new ArrayList<>();
        Set<String> keys = jo1.keySet();
        for (String key : keys) {
            JSONObject parmJSON = jo1.getJSONObject(key);

            //"47.50 0.00";
            String path = parmJSON.getString("path");
            //System.out.println("path len:"+path.length());
            int index_dot1 = path.indexOf(".");
            int index_space = path.indexOf(" ");
            String path2 = path.substring(index_space);
            int index_dot2 = path2.indexOf(".");
            int X = Integer.parseInt(path.substring(1, index_dot1));
            int Y = Integer.parseInt(path2.substring(1, index_dot2));

            String buildCode = "Blc" + PubicMethod.getAcademeCode().substring(7);
            ElemPojo elemPojo = new ElemPojo();
            elemPojo.setBuildCode(buildCode);
            elemPojo.setPosX(X);
            elemPojo.setPosY(Y);
            //id
            elemPojo.setPath(path);
            elemPojo.setName(parmJSON.getString("name"));
            elemPojo.setPaths(key);
            elemPojo.setAreaId(AreaId);

            IDNameList.add(elemPojo);
        }
        int result_2 = parkingService.addParkingElem(IDNameList);
        if (result_2 == 0) {
            addResult.put("flag", "0");
            addResult.put("msg", "插入失败");

            return addResult.toString();
        } else {
            addResult.put("flag", "1");
            addResult.put("msg", "添加成功");

            return addResult.toString();
        }
    }

    @RequestMapping(value = "/EasyParking/getParkingElem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String getParkingElem(@RequestBody String GetJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJSON);

        Map<String, Object> param = new HashMap<>();
        String Depotname = getJson.getString("Depotname");
        param.put("Depotname", Depotname);

        List<Map<String, Object>> parkList = parkingService.getPrkingElem(param);

        JSONObject addResult = new JSONObject();
        if (parkList.isEmpty()) {
            addResult.put("flag", "0");
            addResult.put("msg", "暂无数据");
            return addResult.toString();
        } else {
            JSONArray resultArray = new JSONArray();
            for (Map<String, Object> mapx : parkList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Depotname", mapx.get("Depotname"));
                jsonObject.put("storey", mapx.get("storey"));
                jsonObject.put("width", mapx.get("width"));
                jsonObject.put("height", mapx.get("height"));
                jsonObject.put("AreaId", mapx.get("AreaId"));
                resultArray.add(jsonObject);
            }
            addResult.put("flag", "1");
            addResult.put("msg", resultArray);
            return addResult.toString();
        }
    }

    @RequestMapping(value = "/EasyParking/getParkingPass", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String getParkingPass(@RequestBody String GetJSON) throws Exception {
        JSONObject getJSON = JSONObject.parseObject(GetJSON);

        JSONObject result = new JSONObject();

        String AreaId = getJSON.getString("AreaId");
        File readFile = new File(Constant.filePath + AreaId+Constant.fileSuffix);

        String XandY = "";

        //矩阵
        Map<String, String> idx = new HashMap<>();

        JSONObject paramJSON = new JSONObject();
        try {
            FileReader fileReader = new FileReader(readFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str = bufferedReader.readLine();

            //行数以及列数
            XandY = str;
            int row = Integer.parseInt(str.substring(0,str.indexOf(",")));

            int count = 0;

            while ((str = bufferedReader.readLine()) != null && count<row) {

                String id = "id" + count;
                idx.put(id, str);

                paramJSON.put(id, str);
                count++;
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Exception e) {
            System.out.print(e);
            result.put("flag", "0");
            result.put("msg", "当前车位不在，请联系管理眼进行");
            return result.toString();
        }
        result.put("flag", "1");
        result.put("XandY", XandY);


        result.put("msg", paramJSON);
        return result.toString();

    }

    @RequestMapping(value = "/EasyParking/getBuildElem", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String getBuildElem(@RequestBody String GetJSON) throws Exception {
        JSONObject getJson = JSONObject.parseObject(GetJSON);

        String AreaId = getJson.getString("AreaId");

        List<Map<String, Object>> buildList = parkingService.getBuildElem(AreaId);

        JSONObject addResult = new JSONObject();
        if (buildList.isEmpty()) {
            addResult.put("flag", "0");
            addResult.put("msg", "暂无数据");
            return addResult.toString();
        } else {
            JSONObject js2 = new JSONObject();
            for (Map<String, Object> mapx : buildList) {
                //System.out.print("len"+buildList.size());
                JSONObject js3 = new JSONObject();
                js3.put("name", mapx.get("name"));
                JSONArray jsonArray = new JSONArray();
                jsonArray.add(0, mapx.get("posX"));
                jsonArray.add(1, mapx.get("posY"));

                js3.put("path", mapx.get("path"));
                //js3.put("buildCode",mapx.get("buildCode"));
                js3.put("pos", jsonArray);
                js2.put((String) mapx.get("paths"), js3);

            }
            addResult.put("paths", js2);
            addResult.put("flag", "1");
            addResult.put("msg", "数据已获取");
            addResult.put("len", buildList.size());
            return addResult.toString();
        }
    }

    @RequestMapping(value = "/EasyParking/getTestNavigation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    private String getTestNavigation(@RequestBody String GetJSON) throws Exception {
        String result = "{\"lines\":{\"1\":{\"name\":1,\"path\":\"526.7085,500.4167 301.8257,245.455 302.0364,240.4446 302.1188,236.995 303.246,234.7314 303.3577,234.5717 \"}}}";
        return result;
    }
}
