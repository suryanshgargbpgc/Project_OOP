package com.pharmacy.model;

public class Customer extends User {
    public Prescription[] prescList;
    public int prescCount;
    
    public Customer() {
        super();
        this.prescList = new Prescription[10];
        this.prescCount = 0;
    }
    
    public Customer(String id, String name, String email, String phone) {
        super(id, name, email, phone);
        this.prescList = new Prescription[10];
        this.prescCount = 0;
    }
    
    @Override
    public String getUserType() {
        return "Customer";
    }
    
    public void addRx(Prescription presc) {
        if (prescCount >= prescList.length) {
            Prescription[] newList = new Prescription[prescList.length * 2];
            
            for (int i = 0; i < prescList.length; i++) {
                newList[i] = prescList[i];
            }
            
            prescList = newList;
        }
        
        // Add the new prescription
        this.prescList[prescCount] = presc;
        prescCount = prescCount + 1;
    }
    
    public Prescription[] getPrescList() {
        Prescription[] result = new Prescription[prescCount];

        for (int i = 0; i < prescCount; i++) {
            result[i] = prescList[i];
        }
        
        return result;
    }

    public void setPrescList(Prescription[] prescArray) {
        this.prescList = prescArray;
        this.prescCount = prescArray.length;
    }
    
    public String toString() {
        String output = "";
        output = output + "Customer ID: " + getUserId() + "\n";
        output = output + "Name: " + getName() + "\n";
        output = output + "Email: " + getEmail() + "\n";
        output = output + "Phone: " + getPhoneNumber() + "\n"; 
        output = output + "Address: " + getAddress() + "\n";
        output = output + "Number of prescriptions: " + prescCount;
        return output;
    }
} 