package com.pharmacy.model;

import java.util.Date;

public class Order {
    
    public String orderID;
    public String custID;
    public Date orderDate;
    public String status;
    public double total;
    public String shipAddress;
    public String payMethod;
    public boolean isPaid;
    
    public Medicine[] items;
    public int[] counts;
    public int numItems;
    
    public Order() {
        this.orderID = makeID();
        this.orderDate = new Date();
        this.status = "NEW";
        this.items = new Medicine[10];
        this.counts = new int[10];
        this.numItems = 0;
        this.isPaid = false;
    }
    
    public Order(String custID, String address) {
        this.orderID = makeID();
        this.custID = custID;
        this.orderDate = new Date();
        this.status = "NEW";
        this.shipAddress = address;
        
        this.items = new Medicine[10];
        this.counts = new int[10];
        this.numItems = 0;
        this.isPaid = false;
    }
    
    private String makeID() {
        long time = System.currentTimeMillis();
        return "ORD-" + time/1e9;
    }
    
    public void addMed(Medicine med, int qty) {
        if (med == null) {
            return;
        }
        if (qty <= 0) {
            return;
        }
        
        for (int i = 0; i < numItems; i++) {
            if (items[i].medID.equals(med.medID)) {
                counts[i] = counts[i] + qty;
                updateTotal();
                return;
            }
        }
        
        if (numItems < items.length) {
            items[numItems] = med;
            counts[numItems] = qty;
            numItems = numItems + 1;
            updateTotal();
        } else {
            System.out.println("Error: Order is full, can't add more items");
        }
    }

    private void updateTotal() {
        double subTotal = 0.0;
        
        for (int i = 0; i < numItems; i++) {
            double price = items[i].medPrice;
            int qty = counts[i];
            subTotal = subTotal + (price * qty);
        }

        this.total = subTotal;
    }
    
    public double getSubTotal() {
        double sum = 0.0;
        for (int i = 0; i < numItems; i++) {
            sum = sum + (items[i].medPrice * counts[i]);
        }
        return sum;
    }
    
    public boolean pay(String howPaid) {
        this.payMethod = howPaid;
        this.isPaid = true;
        this.status = "PAID";
        return true;
    }
    
    public Medicine getMed(int pos) {
        if (pos >= 0 && pos < numItems) {
            return items[pos];
        }
        return null;
    }
    
    public int getQty(int pos) {
        if (pos >= 0 && pos < numItems) {
            return counts[pos];
        }
        return 0;
    }
    
    public String getID() {
        return orderID;
    }
    
    public String getCustID() {
        return custID;
    }
    
    public Date getDate() {
        return orderDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public double getTotal() {
        return total;
    }
    
    public String getAddress() {
        return shipAddress;
    }
    
    public String getPayMethod() {
        return payMethod;
    }
    
    public boolean getPaid() {
        return isPaid;
    }
    
    public void setPaid(boolean paid) {
        this.isPaid = paid;
    }
    
    public int getItemCount() {
        return numItems;
    }

    public int OrderStatus() {
        if (status.equals("NEW")) {
            return 0;
        } else if (status.equals("PAID")) {
            return 1;
        } else if (status.equals("SHIPPED")) {
            return 2;
        } else if (status.equals("DELIVERED")) {
            return 3;
        } return 0;
    }
    
    public String toString() {
        String info = "";
        info = info + "Order #" + orderID + "\n";
        info = info + "Date: " + orderDate + "\n";
        info = info + "Customer: " + custID + "\n";
        info = info + "Status: " + status + "\n";
        info = info + "Number of Items: " + numItems + "\n";
        
        for (int i = 0; i < numItems; i++) {
            info = info + "- " + items[i].medName;
            info = info + " (" + counts[i] + ")";
            info = info + " @ ₹" + items[i].medPrice;
            info = info + " = ₹" + (items[i].medPrice * counts[i]);
            info = info + "\n";
        }
        
        info = info + "Total: ₹" + total;
        return info;
    }
}