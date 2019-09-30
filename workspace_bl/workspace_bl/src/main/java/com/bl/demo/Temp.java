package com.bl.demo;

import java.sql.Timestamp;

public class Temp {
    private Integer id;
    private Double temp;
    private Timestamp time;

    public Integer getId() {
        return id;
    }

    public Double getTemp() {
        return temp;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
}
