package com.bl.demo.tour;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TourMapper {
    int addTour(Map<String, Object> param);
    int deleteTour(String tourCode);
    List<Map<String,Object>> getTourList(String name);
    int addDreamList(Map<String,Object> param);
    int deleteDreamList(String dreamListCode);
    long existDreamList(Map<String,Object> param);
    List<Map<String,Object>> getDreamList(String userCode);
    List<Map<String,Object>> getTourComment(String tourCode);
    int addTourComment(Map<String,Object> param);
    List<Map<String,Object>> statDreamList();
    int addTourPic(Map<String,Object> param);
    List<Map<String,Object>> getTourPic(String tourCode);

}
