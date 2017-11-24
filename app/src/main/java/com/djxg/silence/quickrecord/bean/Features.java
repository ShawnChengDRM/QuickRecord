package com.djxg.silence.quickrecord.bean;

/**
 * Created by silence on 2017/11/23.
 */

public class Features {

    private String name;

    private int imageId;

    public Features(String name, int imageId) {
        this.name = name;
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
