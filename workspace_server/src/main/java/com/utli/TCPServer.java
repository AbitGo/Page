package com.utli;

/**
 * @author 韦海涛
 * @version 1.0
 * @date 4/13/2021 2:00 PM
 */

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.*;

import static com.utli.SocketParam.*;

public class TCPServer extends Thread {
    ServerSocket serverSocket = null;
    public static TCPServer tcpServer;

    @PostConstruct
    public void init() {
        tcpServer = this;
        tcpServer.start();
    }

    public String socketSendData(String deviceCode, String deviceData) {
        //不为空时
        subSocketClient temp = DeviceCode2SocketMap.get(deviceCode);
        Long time = DeviceCode2LastestTime.get(deviceCode);
        if (null == time || System.currentTimeMillis() / 1000 - time > 300) {
            return TimeOut;
        }
        if (null != temp) {
            return temp.sendSocketData(deviceData, deviceCode);
        } else {
            return SendError;
        }
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PortNum);
        } catch (IOException e) {

        }
        while (true) {
            try {
                System.out.println("wait. .. ...");
                //使用accept()是阻塞方法
                Socket socketTemp = serverSocket.accept();
                new subSocketClient(this.serverSocket, socketTemp).start();
                //为每个连接都创建一个线程客户端
            } catch (IOException e) {
                //System.out.println("happening");
            }
        }
    }
}
