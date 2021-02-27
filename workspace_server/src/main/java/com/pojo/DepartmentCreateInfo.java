package com.pojo;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 2021/2/27 14:48
 */
public class DepartmentCreateInfo {
    private String departmentName;
    private String departmentCode;
    private String departmentRoot;
    private String personnelCode;

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentRoot() {
        return departmentRoot;
    }

    public void setDepartmentRoot(String departmentRoot) {
        this.departmentRoot = departmentRoot;
    }
}
