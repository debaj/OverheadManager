package com.debaj.overheadmanager.db.bean;

/**
 * Data transfer bean for readings.
 * @author Tamas
 *
 */
public class Reading {
    private int id;
    private long time;
    private float value;
    private int kind;
    private int profile;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getTime() {
        return time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }
    public int getKind() {
        return kind;
    }
    public void setKind(int kind) {
        this.kind = kind;
    }
    public int getProfile() {
        return profile;
    }
    public void setProfile(int profile) {
        this.profile = profile;
    }
}
