package com.pharmacy.model;

public class Medicine {

    public String medID;
    public String medName;
    public double medPrice;
    public int medCount;
    public String medInfo;
    public boolean needsRx;
    public String medCategory;


    public Medicine() {
        this.medID = "";
        this.medName = "";
        this.medPrice = 0.0;
        this.medCount = 0;
        this.medInfo = "";
        this.needsRx = false;
        this.medCategory = "";
    }
    
    public Medicine(String medID, String medName, double medPrice, boolean needsRx) {
        this.medID = medID;
        this.medName = medName;
        this.medPrice = medPrice;
        this.medCount = 0;
        this.medInfo = "";
        this.needsRx = needsRx;
        this.medCategory = "";
    }
    
    public boolean checkStock() {
        if (medCount > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean sellMedicine(int num) {
        if (this.medCount >= num) {
            this.medCount = this.medCount - num;
            return true;
        } else {
            return false;
        }
    }
    
    public void addStock(int num) {
        this.medCount = this.medCount + num;
    }
    
    public String getID() {
        return medID;
    }

    public void setID(String id) {
        this.medID = id;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String name) {
        this.medName = name;
    }

    public double getMedPrice() {
        return medPrice;
    }

    public void setMedPrice(double price) {
        if (price >= 0) {
            this.medPrice = price;
        }
    }

    public int getCount() {
        return medCount;
    }

    public void setCount(int count) {
        this.medCount = count;
    }

    public String getInfo() {
        return medInfo;
    }

    public void setInfo(String info) {
        this.medInfo = info;
    }

    public boolean getNeedsRx() {
        return needsRx;
    }

    public void setNeedsRx(boolean needsRx) {
        this.needsRx = needsRx;
    }

    public String getType() {
        return medCategory;
    }

    public void setType(String type) {
        this.medCategory = type;
    }

    // Print medicine details
    public String toString() {
        String output = "";
        output = output + "Medicine: " + medName + " (ID: " + medID + ")\n";
        output = output + "Price: ₹" + medPrice + "\n";
        output = output + "Available: " + medCount + "\n";
        output = output + "Prescription Required: " + needsRx + "\n";
        output = output + "Category: " + medCategory;
        return output;
    }
} 