package com.bl.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class TempController {
    @Autowired
    TempService tempService;
    @GetMapping("/getData")
    public List<Temp> getData()
    {
        List<Temp> temps = tempService.getTemps();
        return temps;
    }

    @GetMapping("/getData1")
    public Temp getData1()
    {
        return tempService.getTemp(2);
    }

    @GetMapping("/addData1")
    public void addData1(Double adouble)
    {
        Temp temp = new Temp();
        //temp.setId(0);
        temp.setTemp(adouble);
        tempService.addTemp(temp);

    }

    @GetMapping("/Hello")
    public String Hello()
    {
        return "hello";
    }
}
