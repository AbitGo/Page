package com.bl.demo.pojo;

public class sensor {
    private String DeiceCode;
    private String temp;
    private String hum;
    private String sound;
    private String light;

    public String getTemp() {
        return temp;
    }

    public String getDeiceCode() {
        return DeiceCode;
    }

    public void setDeiceCode(String deiceCode) {
        DeiceCode = deiceCode;
    }
    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }
}
