package com.debaj.overheadmanager.db.bean;

/**
 * Data transfer bean for bills.
 * @author Tamas
 *
 */
public class Bill {
    private int id;
    private long dueDate;
    private float sum;
    private float consumption;
    private String type;
    private int profile;
    private boolean settled;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public long getDueDate() {
        return dueDate;
    }
    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }
    public float getSum() {
        return sum;
    }
    public void setSum(float sum) {
        this.sum = sum;
    }
    public float getConsumption() {
        return consumption;
    }
    public void setConsumption(float consumption) {
        this.consumption = consumption;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getProfile() {
        return profile;
    }
    public void setProfile(int profile) {
        this.profile = profile;
    }
    public boolean isSettled() {
        return settled;
    }
    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
