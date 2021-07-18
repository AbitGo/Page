package com.utli;

import java.util.HashMap;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 4/13/2021 1:59 PM
 */
public class SocketParam {
    public static HashMap<String, subSocketClient> DeviceCode2SocketMap = new HashMap<>();
    public static HashMap<String, Long> DeviceCode2LastestTime = new HashMap<>();
    public static int PortNum = 7880;
    public static String TypeRegister = "01";
    public static String TypeUpdateData = "02";
    public static String SendSuccess = "OK";
    public static String SendError = "ERROR";
    public static String TimeOut = "TIMEOUT";
}
