package com.pojo;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/3/1 13:46
 */
public class UserSearchInfo {
    private String userInfo;
    private String departmentCode;
    private int userRole;

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }
}
