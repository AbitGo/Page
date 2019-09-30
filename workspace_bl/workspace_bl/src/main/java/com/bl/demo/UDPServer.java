package com.bl.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

@Component
public class UDPServer implements Runnable  {

    @Autowired
    private TempMapper tempMapper_udp_save;
    public static UDPServer udpServer;

    @PostConstruct
    public void init(){
        udpServer = this;
        udpServer.tempMapper_udp_save = this.tempMapper_udp_save;
    }

    public static void addTemp(Double aDoule)
    {
        Temp temp1 = new Temp();
        temp1.setTemp(aDoule);
        udpServer.tempMapper_udp_save.addTemp(temp1);
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
                Double aDouble = Double.parseDouble((new String(buf,0,datagramPacket.getLength())));

                addTemp(aDouble);
                Thread.sleep(100);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
