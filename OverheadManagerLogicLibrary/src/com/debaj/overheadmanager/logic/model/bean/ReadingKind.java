package com.debaj.overheadmanager.logic.model.bean;

/**
 * Data transfer bean for reading kinds.
 * @author Tamas
 *
 */
public class ReadingKind {
    private int id;
    private String name;
    private String unit;
    private float price;
    private float discountPrice;
    private boolean enabled;
    
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
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public float getPrice() {
        return price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public float getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(float discountPrice) {
        this.discountPrice = discountPrice;
    }
    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
