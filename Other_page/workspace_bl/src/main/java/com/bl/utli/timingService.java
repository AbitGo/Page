package com.bl.utli;


import com.bl.demo.Redis.RedisService;
import com.bl.demo.device.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class timingService {
    @Autowired
    RedisService redisService;
    @Autowired
    DeviceService deviceService;

    //每10分钟添加数据
    @Scheduled(cron = "0 0/10 * * * ?")
    public void getPeriodData() throws Exception {
        List<String> deviceList = deviceService.getDevice();
        Map<String,Object> param = new HashMap<>();
        for(String deviceCode:deviceList){
            param = redisService.GetHashKeyAndValue("deviceData_page1:"+deviceCode);
            param.put("deviceCode",deviceCode);
            deviceService.addDeviceData(param);
            System.out.println("deviceData:"+param);
        }
    }

}
