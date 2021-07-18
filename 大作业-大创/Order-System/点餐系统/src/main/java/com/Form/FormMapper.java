package com.Form;

import com.pojo.OrderList;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Mapper
public interface FormMapper {
    //获取菜单
    public List<Map<String,Object>> getMenu();
    public List<Map<String,Object>> getFoodClass();
    public List<Map<String,Object>> findFoodByName(String foodName);
    public void addFoodClass(String foodClassName);
    public void deleteFoodClass(int foodId);
    public void addMenuFood(Map<String,Object> food);
    public void updateMenuFood(Map<String,Object> food);
    public void updateMenuFoodEnable(Map<String,Object> foodCode);
    public void deleteMenuFood(String foodCode);
    public void insertOrder(Map<String,Object> param);
    public void insertInnerOrder(List<OrderList> param);
    public List<Map<String,Object>> getOrder(int status);
    public List<Map<String,Object>> getInnerOrder(String orderCode);
    public void updateOrderStatue(Map<String,Object> param);
}
