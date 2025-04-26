package com.pharmacy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Order class represents a customer's medicine order
 */
public class Order implements Serializable {

    // Replace enum with proper class for order status
    public static class OrderStatus implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String status;
        
        private OrderStatus(String status) {
            this.status = status;
        }
        
        public String getStatus() {
            return status;
        }
        
        @Override
        public String toString() {
            return status;
        }
        
        // Static instances instead of enum values
        public static final OrderStatus PLACED = new OrderStatus("PLACED");
        public static final OrderStatus CONFIRMED = new OrderStatus("CONFIRMED");
        public static final OrderStatus PROCESSING = new OrderStatus("PROCESSING");
        public static final OrderStatus SHIPPED = new OrderStatus("SHIPPED");
        public static final OrderStatus DELIVERED = new OrderStatus("DELIVERED");
        public static final OrderStatus CANCELLED = new OrderStatus("CANCELLED");
        public static final OrderStatus RETURNED = new OrderStatus("RETURNED");
        public static final OrderStatus EMERGENCY = new OrderStatus("EMERGENCY");
    }
    
    // Replace enum with proper class for payment method
    public static class PaymentMethod implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String method;
        
        private PaymentMethod(String method) {
            this.method = method;
        }
        
        public String getMethod() {
            return method;
        }
        
        @Override
        public String toString() {
            return method;
        }
        
        // Static instances instead of enum values
        public static final PaymentMethod CREDIT_CARD = new PaymentMethod("CREDIT_CARD");
        public static final PaymentMethod DEBIT_CARD = new PaymentMethod("DEBIT_CARD");
        public static final PaymentMethod NET_BANKING = new PaymentMethod("NET_BANKING");
        public static final PaymentMethod UPI = new PaymentMethod("UPI");
        public static final PaymentMethod WALLET = new PaymentMethod("WALLET");
        public static final PaymentMethod CASH_ON_DELIVERY = new PaymentMethod("CASH_ON_DELIVERY");
    }
    
    private String orderId;
    private String customerId;
    private Date orderDate;
    private OrderStatus status;
    private double totalAmount;
    private String shippingAddress;
    private PaymentMethod paymentMethod;
    private boolean isPaid;
    private String prescriptionId; // If order requires prescription
    private Date deliveryDate;
    private boolean isEmergency;
    private String trackingNumber;
    private OrderItem[] orderItems; // Changed from List to array
    private int itemCount; // Track number of items in the array
    
    // Inner class for order items
    public static class OrderItem implements Serializable {
        private static final long serialVersionUID = 1L;
        
        private Medicine medicine;
        private int quantity;
        private double price; // Price at the time of order
        
        public OrderItem(Medicine medicine, int quantity) {
            this.medicine = medicine;
            this.quantity = quantity;
            this.price = medicine.getPrice();
        }
        
        // Calculate total price for this item
        public double getTotalPrice() {
            return price * quantity;
        }
        
        // Getters and Setters
        public Medicine getMedicine() {
            return medicine;
        }
        
        public void setMedicine(Medicine medicine) {
            this.medicine = medicine;
        }
        
        public int getQuantity() {
            return quantity;
        }
        
        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
        public double getPrice() {
            return price;
        }
        
        public void setPrice(double price) {
            this.price = price;
        }
        
        @Override
        public String toString() {
            return medicine.getName() + 
                   " - " + quantity + 
                   " x $" + price + 
                   " = $" + getTotalPrice();
        }
    }
    
    // Default constructor
    public Order() {
        this.orderId = java.util.UUID.randomUUID().toString();
        this.customerId = "";
        this.orderDate = new Date();
        this.status = OrderStatus.PLACED;
        this.totalAmount = 0.0;
        this.shippingAddress = "";
        this.paymentMethod = PaymentMethod.CASH_ON_DELIVERY;
        this.isPaid = false;
        this.prescriptionId = "";
        this.deliveryDate = null;
        this.isEmergency = false;
        this.trackingNumber = "";
        this.orderItems = new OrderItem[10]; // Initial capacity
        this.itemCount = 0;
    }
    
    // Constructor with basic information
    public Order(String customerId, String shippingAddress) {
        this.orderId = java.util.UUID.randomUUID().toString();
        this.customerId = customerId;
        this.orderDate = new Date();
        this.status = OrderStatus.PLACED;
        this.totalAmount = 0.0;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = PaymentMethod.CASH_ON_DELIVERY;
        this.isPaid = false;
        this.prescriptionId = "";
        this.deliveryDate = null; 
        this.isEmergency = false;
        this.trackingNumber = "";
        this.orderItems = new OrderItem[10]; // Initial capacity
        this.itemCount = 0;
    }
    
    // Constructor for orders with prescription
    public Order(String customerId, String shippingAddress, String prescriptionId) {
        this(customerId, shippingAddress);
        this.prescriptionId = prescriptionId;
    }
    
    // Constructor for emergency orders
    public Order(String customerId, String shippingAddress, boolean isEmergency) {
        this(customerId, shippingAddress);
        this.isEmergency = isEmergency;
        if (isEmergency) {
            this.status = OrderStatus.EMERGENCY;
        }
    }
    
    // Add an item to the order
    public void addItem(Medicine medicine, int quantity) {
        // Check if medicine is in stock
        if (medicine.getStock() >= quantity) {
            // Check if medicine already exists in the order
            for (int i = 0; i < itemCount; i++) {
                if (orderItems[i].getMedicine().getMedicineId().equals(medicine.getMedicineId())) {
                    // Update quantity if medicine already exists
                    orderItems[i].setQuantity(orderItems[i].getQuantity() + quantity);
                    calculateTotal();
                    return;
                }
            }
            
            // Add new order item if medicine doesn't exist
            OrderItem orderItem = new OrderItem(medicine, quantity);
            
            // Resize array if needed
            if (itemCount >= orderItems.length) {
                OrderItem[] newOrderItems = new OrderItem[orderItems.length * 2];
                System.arraycopy(orderItems, 0, newOrderItems, 0, orderItems.length);
                orderItems = newOrderItems;
            }
            
            orderItems[itemCount++] = orderItem;
            calculateTotal();
        }
    }
    
    // Vararg method to add multiple items at once
    public void addItems(Object... items) {
        // Items should be provided in pairs: Medicine, quantity, Medicine, quantity, etc.
        if (items.length % 2 != 0) {
            throw new IllegalArgumentException("Items must be provided in pairs: Medicine, quantity");
        }
        
        for (int i = 0; i < items.length; i += 2) {
            if (items[i] instanceof Medicine && items[i+1] instanceof Integer) {
                Medicine medicine = (Medicine) items[i];
                int quantity = (Integer) items[i+1];
                addItem(medicine, quantity);
            } else {
                throw new IllegalArgumentException("Each pair must be a Medicine object followed by an Integer quantity");
            }
        }
    }
    
    // Method to calculate the total amount
    private void calculateTotal() {
        this.totalAmount = 0.0;
        for (int i = 0; i < itemCount; i++) {
            this.totalAmount += orderItems[i].getTotalPrice();
        }
    }
    
    // Method to process payment
    public boolean processPayment(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        // In a real system, this would connect to a payment gateway
        this.isPaid = true;
        if (this.status == OrderStatus.PLACED) {
            this.status = OrderStatus.CONFIRMED;
        }
        return true;
    }
    
    // Method to update order status
    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        
        // If delivered, set the delivery date
        if (newStatus == OrderStatus.DELIVERED) {
            this.deliveryDate = new Date();
        }
    }
    
    // Method to generate tracking information
    public void generateTrackingNumber() {
        // In a real system, this would be provided by a shipping service
        this.trackingNumber = "TRK" + System.currentTimeMillis();
    }
    
    // Method to check if order contains prescription medicines
    public boolean containsPrescriptionMedicines() {
        for (int i = 0; i < itemCount; i++) {
            if (orderItems[i].getMedicine().isRequiresPrescription()) {
                return true;
            }
        }
        return false;
    }
    
    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isEmergency() {
        return isEmergency;
    }

    public void setEmergency(boolean emergency) {
        isEmergency = emergency;
        if (emergency) {
            this.status = OrderStatus.EMERGENCY;
        }
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public OrderItem[] getOrderItems() {
        // Return a trimmed array with only the filled elements
        OrderItem[] result = new OrderItem[itemCount];
        System.arraycopy(orderItems, 0, result, 0, itemCount);
        return result;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.orderItems = orderItems;
        this.itemCount = orderItems.length;
        calculateTotal();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("Customer ID: ").append(customerId).append("\n");
        sb.append("Order Date: ").append(orderDate).append("\n");
        sb.append("Status: ").append(status).append("\n");
        sb.append("Total Amount: $").append(totalAmount).append("\n");
        sb.append("Items: \n");
        
        for (int i = 0; i < itemCount; i++) {
            sb.append("- ").append(orderItems[i].toString()).append("\n");
        }
        
        sb.append("Payment Method: ").append(paymentMethod).append("\n");
        sb.append("Paid: ").append(isPaid ? "Yes" : "No").append("\n");
        sb.append("Emergency: ").append(isEmergency ? "Yes" : "No").append("\n");
        
        if (trackingNumber != null && !trackingNumber.isEmpty()) {
            sb.append("Tracking Number: ").append(trackingNumber).append("\n");
        }
        
        if (deliveryDate != null) {
            sb.append("Delivery Date: ").append(deliveryDate).append("\n");
        }
        
        return sb.toString();
    }
} 