package com.provinceAndArea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AreaService {
    @Autowired
    AreaMapper areaMapper;
    public List<AreaPojo> findAreaByProvince(String provincesCode){
        return areaMapper.findAreaByProvince(provincesCode);
    }

    public List<ProvincePojo> findProvince(){
        return areaMapper.findProvince();
    }
}
