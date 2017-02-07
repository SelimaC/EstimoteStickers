package com.example.selima.stickers;

import java.util.ArrayList;

/**
 * Created by selim on 07/02/2017.
 */

public class Fields {
    String id;
    boolean motion;
    long lastMotionStateDuration;
    long currentMotionStateDuration;
    String acceleration;
    double temperature;
    String batterylevel;
    String color;
    String type;

    public Fields(String id, boolean motion, long lastMotionStateDuration,long currentMotionStateDuration, String acceleration, double temperature, String batterylevel,
                  String color, String type ){
        this.id=id;
        this.motion=motion;
        this.lastMotionStateDuration=lastMotionStateDuration;
        this.currentMotionStateDuration=currentMotionStateDuration;
        this.acceleration=acceleration;
        this.temperature=temperature;
        this.batterylevel=batterylevel;
        this.color=color;
        this.type=type;
    }

    @Override
    public String toString(){

        return "ID: " + id + "\nMotion: " + motion + "\nLastMotionStateDuration: " + lastMotionStateDuration + "s" +
                "\nCurrentMotionStateDuration: " + currentMotionStateDuration+ "s" + "\nAcceleration (x,y,z): " + acceleration +
                "\nTemperature: " + temperature + "\nBatteryLevel: " + batterylevel + "\nColor: " + color +
                "\nType: " + type;
    }

    public String onlyChecked(ArrayList<Boolean> check){
        String res = "";

        if(check.get(0)) res += "\nID: " + id;
        if(check.get(1)) res += "\nMotion: " + motion;
        if(check.get(2)) res += "\nLastMotionStateDuration: " + lastMotionStateDuration + "s";
        if(check.get(3)) res += "\nCurrentMotionStateDuration: " + currentMotionStateDuration + "s";
        if(check.get(4)) res += "\nAcceleration (x,y,z): " + acceleration;
        if(check.get(5)) res += "\nTemperature: " + temperature;
        if(check.get(6)) res += "\nBatteryLevel: " + batterylevel;
        if(check.get(7)) res += "\nColor: " + color;
        if(check.get(8)) res += "\nType: " + type;

        return res;

    }
}
