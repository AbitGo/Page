package com.provinceAndArea;

public class AreaPojo {
    private String AreaName;
    private String ProvinceCode;
    private String AreaCode;

    public String getProvinceCode() {
        return ProvinceCode;
    }

    public String getAreaCode() {
        return AreaCode;
    }

    public String getAreaName() {
        return AreaName;

    }

    public void setProvinceCode(String provinceCode) {
        ProvinceCode = provinceCode;
    }

    public void setAreaCode(String areaCode) {
        AreaCode = areaCode;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }
}
