package com.Mapper;


import com.pojo.ElemPojo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ParkingMapper {
    List<Map<String,Object>> getPrkingElem(Map<String,Object> param);
    List<Map<String,Object>> getBuildElem(String param);
    int addBuildElem(Map<String,Object> param);
    int addParkingElem(List<ElemPojo> param);
}
