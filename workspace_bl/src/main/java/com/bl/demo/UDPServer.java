package com.bl.demo;

import com.alibaba.fastjson.JSONObject;
import com.bl.demo.device.DeviceMapper;
import com.bl.demo.device.DeviceService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

@Component
public class UDPServer implements Runnable  {

    @Autowired
    private DeviceMapper deviceMapper;

    public static UDPServer udpServer;

    @PostConstruct
    public void init(){
        udpServer = this;
        udpServer.deviceMapper = this.deviceMapper;
    }

    public static void addTemp(String data)
    {
        JSONObject Json = JSONObject.parseObject(data);
        String deviceCode = Json.getString("deviceCode");
        String temp = Json.getString("temp");
        String hum = Json.getString("hum");
        String sound = Json.getString("sound");
        String light = Json.getString("light");

        Map<String,Object> param = new HashMap();
        param.put("deviceCode",deviceCode);
        param.put("temp",temp);
        param.put("hum",hum);
        param.put("sound",sound);
        param.put("light",light);
        param.put("timeRec",System.currentTimeMillis()/1000);

        System.out.println("data:"+param);
        udpServer.deviceMapper.addDeviceData(param);
    }

    @Override
    public void run() {
        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket(2345);
        } catch (SocketException e) {
            datagramSocket.close();
            e.printStackTrace();
        }
        while(true)
        {
            try {
                byte[] buf = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length);
                datagramSocket.receive(datagramPacket);
                System.out.println("接收端接收到的数据："+ new String(buf,0,datagramPacket.getLength()));
                String Data = (new String(buf,0,datagramPacket.getLength()));

                addTemp(Data);
                Thread.sleep(100);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
