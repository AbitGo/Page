package com.bl.demo.pojo;

public class SalaryPojo {
    //(#{checkYear}, #{checkMonth}, #{salary}, #{checkCount}, #{userCode});
    private String userCode;
    private int checkYear;
    private int checkMonth;
    private float salary;
    private int checkCount;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public int getCheckYear() {
        return checkYear;
    }

    public void setCheckYear(int checkYear) {
        this.checkYear = checkYear;
    }

    public int getCheckMonth() {
        return checkMonth;
    }

    public void setCheckMonth(int checkMonth) {
        this.checkMonth = checkMonth;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(int checkCount) {
        this.checkCount = checkCount;
    }
}
