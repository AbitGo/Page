package com;

import com.utli.TCPServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//开启事务
@EnableTransactionManagement
//开启自动扫描
@EnableScheduling
@SpringBootApplication
public class AllApplication {

    public static void main(String[] args) {
        SpringApplication.run(AllApplication.class, args);
        TCPServer tcpServer = new TCPServer();
        tcpServer.init();
    }

}
