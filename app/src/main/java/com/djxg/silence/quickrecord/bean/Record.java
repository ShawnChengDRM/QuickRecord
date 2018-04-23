package com.djxg.silence.quickrecord.bean;

import org.litepal.crud.DataSupport;

/**
 * Created by silence on 2018/1/5.
 */

public class Record extends DataSupport {

    private int id;

    private byte[] record_image;

    private String record_text;

    private String record_time;

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public byte[] getRecord_image() {
        return record_image;
    }

    public void setRecord_image(byte[] record_image) {
        this.record_image = record_image;
    }

    public String getRecord_text() {
        return record_text;
    }

    public void setRecord_text(String record_text) {
        this.record_text = record_text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
