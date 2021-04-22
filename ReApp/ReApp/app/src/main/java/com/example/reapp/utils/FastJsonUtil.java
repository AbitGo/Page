package com.example.reapp.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by zy on 08/25/19
 */
public class FastJsonUtil {
    //将json数据转换为Bean对象
    public static <T> T toBean(String jsonData, Class<T> tClass) {
        return JSON.parseObject(jsonData, tClass);
    }
    //将任意对象转换为json数据
    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
    //获取json字符串中单个数值
    public static Object getSingleValue(String jsonData, String key) {
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        return jsonObject.get(key);
    }
    //获取多个数值
    public static List<String> getValue(String jsonData,String...key){
        List<String> jsonList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(jsonData);
        for (int i = 0; i < key.length; i ++){
            jsonList.add(String.valueOf(jsonObject.get(key[i])));
        }
        return jsonList;
    }
    //将json数据准换为List对象列表
    public static <T> List<T> toList(String jsonData, Class<T> tClass) {
        return JSON.parseArray(jsonData, tClass);
    }
    //将json数据转换为map
    public static Map<String,Object> toMap(String jsonData){
        return JSON.parseObject(jsonData,new TypeReference<Map<String,Object>>(){});
    }
    //将json数据转换为List<Map<String,Object>>
    public static List<Map<String,Object>> toListMap(String jsonData){
        return JSON.parseObject(jsonData,new TypeReference<List<Map<String,Object>>>(){});
    }
}
