package com.bl.demo;
import com.bl.demo.Temp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TempMapper {
    int addTemp(Temp temp);
    int deleteTempById(Integer id);
    Temp getTempById(Integer id);
    List<Temp> getTempsInFive();

}

