package com.bl.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TempService {
    @Autowired
    TempMapper tempMapper;
    public int addTemp(Temp temp)
    {
        return tempMapper.addTemp(temp);
    }
    public int deleteTemp(Integer id)
    {
        return tempMapper.deleteTempById(id);
    }

    public Temp getTemp(Integer id)
    {
        return tempMapper.getTempById(id);
    }

    public List<Temp> getTemps()
    {
        return tempMapper.getTempsInFive();
    }


}
