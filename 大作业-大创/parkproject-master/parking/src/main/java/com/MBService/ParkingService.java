package com.MBService;

import com.Mapper.ParkingMapper;
import com.pojo.ElemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParkingService {
    @Autowired
    ParkingMapper parkingMapper;
    public List<Map<String,Object>> getBuildElem(String param){
        return parkingMapper.getBuildElem(param);
    }
    public int addBuildElem(Map<String,Object> param){
        return parkingMapper.addBuildElem(param);
    }

    public int addParkingElem(List<ElemPojo> param){
        return parkingMapper.addParkingElem(param);
    }

    public List<Map<String,Object>> getPrkingElem(Map<String,Object> param){
        return parkingMapper.getPrkingElem(param);
    }


}
