package com.provinceAndArea;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AreaMapper {
    //通过省份的code查找区、县的资料
    List<AreaPojo> findAreaByProvince(String provincesCode);
    //查找所有的省，并将此返回出去
    List<ProvincePojo> findProvince();
}
