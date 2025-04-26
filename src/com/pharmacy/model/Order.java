package com.pharmacy.model;

import java.util.Date;

/**
 * Order class for customer purchases
 * Written by: Student
 * Date: 11/10/2023
 */
public class Order {
    
    // Order information - all public for easy access
    public String orderID;
    public String custID;
    public Date orderDate;
    public String status;
    public double total;
    public String shipAddress;
    public String payMethod;
    public boolean isPaid;
    
    // Store items in order
    public Medicine[] items;
    public int[] counts;
    public int numItems;
    
    // Empty constructor
    public Order() {
        this.orderID = makeID();
        this.orderDate = new Date(); // Today's date
        this.status = "NEW";
        this.items = new Medicine[10]; // Start with space for 10 items
        this.counts = new int[10];
        this.numItems = 0;
        this.isPaid = false;
    }
    
    // Basic constructor with customer info
    public Order(String custID, String address) {
        // Set up basic info
        this.orderID = makeID();
        this.custID = custID;
        this.orderDate = new Date(); // Today's date
        this.status = "NEW";
        this.shipAddress = address;
        
        // Create arrays for medicines
        this.items = new Medicine[10]; // Start with space for 10 items
        this.counts = new int[10];
        this.numItems = 0;
        this.isPaid = false;
    }
    
    // Make a random ID for the order
    private String makeID() {
        long time = System.currentTimeMillis();
        return "ORD-" + time;
    }
    
    // Add a medicine to the order
    public void addMed(Medicine med, int qty) {
        // Check for bad inputs
        if (med == null) {
            return;
        }
        if (qty <= 0) {
            return;
        }
        
        // Check if medicine already in order
        for (int i = 0; i < numItems; i++) {
            // If found, just update quantity
            if (items[i].medID.equals(med.medID)) {
                counts[i] = counts[i] + qty;
                updateTotal();
                return;
            }
        }
        
        // Not in order yet, so add it if there's room
        if (numItems < items.length) {
            items[numItems] = med;
            counts[numItems] = qty;
            numItems = numItems + 1;
            updateTotal();
        } else {
            System.out.println("Error: Order is full, can't add more items");
        }
    }
    
    // Update the order total price
    private void updateTotal() {
        double subTotal = 0.0;
        
        // Add up all item prices
        for (int i = 0; i < numItems; i++) {
            double price = items[i].medPrice;
            int qty = counts[i];
            subTotal = subTotal + (price * qty);
        }
        
        // Add shipping cost
        double shipping = getShipping(subTotal);
        
        // Set the total
        this.total = subTotal + shipping;
    }
    
    // Calculate shipping cost
    public double getShipping(double subTotal) {
        // Free shipping over $50
        if (subTotal >= 50.0) {
            return 0.0;
        } else {
            return 5.99; // Standard shipping fee
        }
    }
    
    // Calculate subtotal before shipping
    public double getSubTotal() {
        double sum = 0.0;
        // Add up all items
        for (int i = 0; i < numItems; i++) {
            sum = sum + (items[i].medPrice * counts[i]);
        }
        return sum;
    }
    
    // Get shipping amount
    public double getShipCost() {
        return getShipping(getSubTotal());
    }
    
    // Pay for the order
    public boolean pay(String howPaid) {
        this.payMethod = howPaid;
        this.isPaid = true;
        this.status = "PAID";
        return true;
    }
    
    // Get a medicine from the order
    public Medicine getMed(int pos) {
        if (pos >= 0 && pos < numItems) {
            return items[pos];
        }
        return null;
    }
    
    // Get quantity for a medicine
    public int getQty(int pos) {
        if (pos >= 0 && pos < numItems) {
            return counts[pos];
        }
        return 0;
    }
    
    // Basic getters with simple names
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
    
    // Print order information
    public String toString() {
        String info = "";
        info = info + "Order #" + orderID + "\n";
        info = info + "Date: " + orderDate + "\n";
        info = info + "Customer: " + custID + "\n";
        info = info + "Status: " + status + "\n";
        info = info + "Number of Items: " + numItems + "\n";
        
        // Print each medicine
        for (int i = 0; i < numItems; i++) {
            info = info + "- " + items[i].medName;
            info = info + " (" + counts[i] + ")";
            info = info + " @ $" + items[i].medPrice;
            info = info + " = $" + (items[i].medPrice * counts[i]);
            info = info + "\n";
        }
        
        info = info + "Total: $" + total;
        return info;
    }
} 