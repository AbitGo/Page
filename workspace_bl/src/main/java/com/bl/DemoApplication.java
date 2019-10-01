package com.bl;

import com.bl.demo.UDPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        UDPServer udpServer = new UDPServer();
        udpServer.run();
    }

}
