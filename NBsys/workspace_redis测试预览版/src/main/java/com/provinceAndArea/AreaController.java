package com.provinceAndArea;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class AreaController {
    @Autowired
    AreaService areaService;
    //获取县、市
    @RequestMapping(value = "/Area/getAreaInfo", method = RequestMethod.GET)
    public String getAreaInfo(String provinceCode) throws Exception {

        List<AreaPojo> areaPojoList = areaService.findAreaByProvince(provinceCode);
        JSONArray jsonArray = new JSONArray();
        for(AreaPojo areaPojo:areaPojoList)
        {
            JSONObject param = new JSONObject();
            param.put("areaName",areaPojo.getAreaName());
            param.put("areaCode",areaPojo.getAreaCode());
            jsonArray.add(param);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("provinceCode",provinceCode);
        jsonObject.put("flag","1");
        jsonObject.put("msg",jsonArray);
        return jsonObject.toString();
    }
    @RequestMapping(value = "/Area/getProvinceInfo", method = RequestMethod.GET)
    public String getProvinceInfo() throws Exception {

        List<ProvincePojo> provincePojoList = areaService.findProvince();
        JSONArray jsonArray = new JSONArray();
        for(ProvincePojo provincePojo:provincePojoList)
        {
            JSONObject param = new JSONObject();
            param.put("provinceCode",provincePojo.getProvinceCode());
            param.put("provinceName",provincePojo.getProvinceName());
            jsonArray.add(param);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag","1");
        jsonObject.put("msg",jsonArray);
        return jsonObject.toString();
    }
}
