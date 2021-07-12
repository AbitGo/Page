package com.Form;

import com.pojo.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FormServiceImpl {
    @Autowired
    FormMapper formMapper;
    public List<Map<String,Object>> getMenu(){
        return formMapper.getMenu();
    }
    public List<Map<String,Object>> getFoodClass(){
        return formMapper.getFoodClass();
    }

    public List<Map<String,Object>> findFoodByName(String foodName){
        return formMapper.findFoodByName(foodName);
    }

    public void addFoodClass(String foodClassName){
        formMapper.addFoodClass(foodClassName);
    }
    public void deleteFoodClass(int foodId){
        formMapper.deleteFoodClass(foodId);
    }
    public void addMenuFood(Map<String,Object> food){
        formMapper.addMenuFood(food);
    }
    public void updateMenuFood(Map<String,Object> food){
        formMapper.updateMenuFood(food);
    }
    public void deleteMenuFood(String foodCode){
        formMapper.deleteMenuFood(foodCode);
    }

    public void updateMenuFoodEnable(Map<String,Object> foodCode){
        formMapper.updateMenuFoodEnable(foodCode);
    }
    public void insertOrder(Map<String,Object> param){
        formMapper.insertOrder(param);
    }

    public void insertInnerOrder(List<OrderList> param){
        formMapper.insertInnerOrder(param);
    }

    public List<Map<String,Object>> getOrder(int status)
    {
        return formMapper.getOrder(status);
    }
    public List<Map<String,Object>> getInnerOrder(String orderCode){
        return formMapper.getInnerOrder(orderCode);
    }
    public void updateOrderStatue(Map<String,Object> param){
        formMapper.updateOrderStatue(param);
    }
}
