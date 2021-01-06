package com.bl.demo.tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TourService {
    @Autowired
    TourMapper tourMapper;

    public int addTour(Map<String, Object> param) {
        return tourMapper.addTour(param);
    }

    public int deleteTour(String tourCode) {
        return tourMapper.deleteTour(tourCode);
    }

    public List<Map<String, Object>> getTourList(String name) {
        return tourMapper.getTourList(name);
    }

    public int addDreamList(Map<String, Object> param) {
        return tourMapper.addDreamList(param);
    }

    public int deleteDreamList(String dreamListCode) {
        return tourMapper.deleteDreamList(dreamListCode);
    }

    public long existDreamList(Map<String, Object> param) {
        return tourMapper.existDreamList(param);
    }
    public List<Map<String,Object>> getDreamList(String userCode){
        return tourMapper.getDreamList(userCode);
    }

    public List<Map<String,Object>> getTourComment(String tourCode){
        return tourMapper.getTourComment(tourCode);
    }
    public int addTourComment(Map<String,Object> param){
        return tourMapper.addTourComment(param);
    }
    public List<Map<String,Object>> statDreamList(){
        return tourMapper.statDreamList();
    }
}
