package com.example.reapp.utils;

public class HttpParam {
    public final static String url="http://47.102.42.105:7891";

    public final static String userLogin = url + "/user/userLogin";
    public final static String userRegister = url + "/user/userRegister";
    public final static String userDepartment_get = url + "/department/getDepartment?userCode=";
    public final static String TASK_GET = url + "/device/SearchDeviceTaskByManager";
    public final static String DEPARTMENT_ADD = url + "/department/departmentCreate";
    public final static String TASK_ADD = url + "/device/AddDeviceTask";
    public final static String DEPARTMENT_SEARCH = url + "/user/userSearch";
    public final static String DEVICE_SEARCH = url + "/device/SearchDevice";
    public final static String DEVICE_SEARCH_RECORD = url + "/device/searchRecord";
    public final static String DEVICE_TEST = url + "/device/Test";
    public final static String SEARCH_DEVICE_TASK = url + "/device/SearchDeviceTask";
    public final static String TASK_VERIFY = url + "/device/auditTask";
    public final static String PERSON_Authorization = url + "/department/personAuthorization";
    public final static String DEVICE_ADD = url + "/device/addDevice";
    public final static String DEVICE_DELETE = url + "/device/deleteDevice";
    public final static String DEPARTMENT_UPDATE = url + "/department/departmentUpdate";

}
