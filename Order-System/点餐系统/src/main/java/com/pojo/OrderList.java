package com.pojo;
public class OrderList {
    private String foodCode;
    private int foodCount;
    private String orderCode;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }
    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }
    public int getFoodCount() {
        return foodCount;
    }

}
