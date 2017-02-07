package com.example.selima.stickers;

import java.io.Serializable;

/**
 * Created by selim on 07/02/2017.
 */

public class InfoStickers implements Serializable {
    String ID;
    boolean motion;
    String acceleration;
    String temperature;

    public InfoStickers( String ID, boolean motion, String acceleration,String temperature){
        this.ID=ID;
        this.motion=motion;
        this.acceleration=acceleration;
        this.temperature=temperature;
    }
}
