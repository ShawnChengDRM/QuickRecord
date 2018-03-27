package com.djxg.silence.quickrecord.bean;

/**
 * Created by silence on 2018/1/5.
 */

public class Record {

    private int record_image;

    private String record_text;

    private String record_time;

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public int getRecord_image() {
        return record_image;
    }

    public void setRecord_image(int record_image) {
        this.record_image = record_image;
    }

    public String getRecord_text() {
        return record_text;
    }

    public void setRecord_text(String record_text) {
        this.record_text = record_text;
    }

}
