package com.example.reapp.pojo;
public class DepartmentInner {
    private int id;
    private String departmentName;
    private String departmentCode;
    private int userRole;
    private String userCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }
    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
    public int getUserRole() {
        return userRole;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
    public String getUserCode() {
        return userCode;
    }

}