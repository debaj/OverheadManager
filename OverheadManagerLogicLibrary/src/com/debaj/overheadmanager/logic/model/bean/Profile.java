package com.debaj.overheadmanager.logic.model.bean;

/**
 * Data transfer bean for profiles.
 * @author Tamas
 *
 */
public class Profile {
    private int id;
    private String name;
    private String address;
    private String kind;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getKind() {
        return kind;
    }
    public void setKind(String kind) {
        this.kind = kind;
    }
}
