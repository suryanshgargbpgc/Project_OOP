package com.pharmacy.service.impl;

import com.pharmacy.model.Order;
import com.pharmacy.model.Medicine;
import com.pharmacy.service.OrderService;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * Simple implementation of OrderService interface
 * Written by: Student
 * Date: 11/10/2023
 */
public class OrderServiceImpl implements OrderService {
    
    private static final Logger logger = Logger.getLogger(OrderServiceImpl.class.getName());
    
    // In-memory storage for orders (simulated database)
    private Map<String, Order> orders = new HashMap<>();
    
    // Common order status values as Strings instead of enum
    public static final String STATUS_NEW = "NEW";
    public static final String STATUS_PROCESSING = "PROCESSING";
    public static final String STATUS_SHIPPED = "SHIPPED";
    public static final String STATUS_DELIVERED = "DELIVERED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    
    @Override
    public Order placeOrder(String customerId, Map<String, Integer> medicineQuantities, String shippingAddress) {
        if (customerId == null || medicineQuantities == null || shippingAddress == null) {
            return null;
        }
        
        // Create a new order
        Order order = new Order(customerId, shippingAddress);
        
        // Add items to the order
        // Note: In a real implementation, we would look up medicines from a database
        // For this simplified version, we'll just log that we would add items
        logger.info("Would add " + medicineQuantities.size() + " medicines to order " + order.getID());
        
        // Store the order
        orders.put(order.getID(), order);
        
        return order;
    }
    
    @Override
    public Order placePrescriptionOrder(String customerId, String prescriptionId, String shippingAddress) {
        if (customerId == null || prescriptionId == null || shippingAddress == null) {
            return null;
        }
        
        // Create a new order
        Order order = new Order(customerId, shippingAddress);
        
        // In a real implementation, we would:
        // 1. Look up the prescription
        // 2. Validate it's still valid
        // 3. Add the prescribed medicines to the order
        
        // For this simplified version, we'll just log that we would process a prescription
        logger.info("Would process prescription " + prescriptionId + " for order " + order.getID());
        
        // Store the order
        orders.put(order.getID(), order);
        
        return order;
    }
    
    @Override
    public Order placeEmergencyOrder(String customerId, Map<String, Integer> medicineQuantities, String shippingAddress) {
        if (customerId == null || medicineQuantities == null || shippingAddress == null) {
            return null;
        }
        
        // Create a new order with priority handling
        Order order = new Order(customerId, shippingAddress);
        
        // Mark as emergency (in a real implementation, this would set priority flags)
        // For our simplified version, we'll just use status to indicate it's an emergency
        order.status = "EMERGENCY";
        
        // Add items to the order
        // Note: In a real implementation, we would look up medicines from a database
        
        // Store the order
        orders.put(order.getID(), order);
        
        return order;
    }
    
    @Override
    public boolean cancelOrder(String orderId, String reason) {
        if (orderId == null) {
            return false;
        }
        
        Order order = orders.get(orderId);
        if (order == null) {
            return false;
        }
        
        // Can only cancel orders that aren't shipped or delivered
        if (STATUS_SHIPPED.equals(order.status) || STATUS_DELIVERED.equals(order.status)) {
            return false;
        }
        
        // Update status to cancelled
        order.status = STATUS_CANCELLED;
        
        // In a real implementation, we would log the reason
        logger.info("Order " + orderId + " cancelled. Reason: " + reason);
        
        return true;
    }
    
    @Override
    public Order updateOrderStatus(String orderId, String newStatus) {
        if (orderId == null || newStatus == null) {
            return null;
        }
        
        Order order = orders.get(orderId);
        if (order == null) {
            return null;
        }
        
        // Update the status
        order.status = newStatus;
        
        return order;
    }
    
    @Override
    public Order getOrder(String orderId) {
        if (orderId == null) {
            return null;
        }
        
        return orders.get(orderId);
    }
    
    @Override
    public Order[] getCustomerOrders(String customerId) {
        if (customerId == null) {
            return new Order[0];
        }
        
        List<Order> customerOrders = new ArrayList<>();
        
        // Find all orders for this customer
        for (Order order : orders.values()) {
            if (order.getCustID().equals(customerId)) {
                customerOrders.add(order);
            }
        }
        
        // Convert to array
        Order[] result = new Order[customerOrders.size()];
        return customerOrders.toArray(result);
    }
    
    @Override
    public Map<String, Object> getOrderTracking(String orderId) {
        if (orderId == null) {
            return null;
        }
        
        Order order = orders.get(orderId);
        if (order == null) {
            return null;
        }
        
        // Create a simple tracking info map
        Map<String, Object> trackingInfo = new HashMap<>();
        trackingInfo.put("orderId", order.getID());
        trackingInfo.put("status", order.getStatus());
        trackingInfo.put("lastUpdated", new Date());
        
        // In a real implementation, this would include shipping carrier info, tracking numbers, etc.
        
        return trackingInfo;
    }
    
    @Override
    public Order[] getOrderHistory(String customerId) {
        // For this simplified implementation, this is the same as getCustomerOrders
        return getCustomerOrders(customerId);
    }
    
    @Override
    public Order addItemsToOrder(String orderId, Map<String, Integer> medicineQuantities) {
        if (orderId == null || medicineQuantities == null) {
            return null;
        }
        
        Order order = orders.get(orderId);
        if (order == null) {
            return null;
        }
        
        // Can only add items to orders that aren't shipped, delivered, or cancelled
        if (STATUS_SHIPPED.equals(order.status) || 
            STATUS_DELIVERED.equals(order.status) || 
            STATUS_CANCELLED.equals(order.status)) {
            return null;
        }
        
        // In a real implementation, we would look up medicines and add them to the order
        logger.info("Would add " + medicineQuantities.size() + " medicines to order " + order.getID());
        
        return order;
    }
    
    @Override
    public Order removeItemsFromOrder(String orderId, String[] medicineIds) {
        if (orderId == null || medicineIds == null) {
            return null;
        }
        
        Order order = orders.get(orderId);
        if (order == null) {
            return null;
        }
        
        // Can only remove items from orders that aren't shipped, delivered, or cancelled
        if (STATUS_SHIPPED.equals(order.status) || 
            STATUS_DELIVERED.equals(order.status) || 
            STATUS_CANCELLED.equals(order.status)) {
            return null;
        }
        
        // In a real implementation, we would remove the specified medicines from the order
        logger.info("Would remove " + medicineIds.length + " medicines from order " + order.getID());
        
        return order;
    }
    
    @Override
    public Medicine[] getRecommendedMedicines(String customerId) {
        if (customerId == null) {
            return new Medicine[0];
        }
        
        // In a real implementation, this would analyze purchase history
        // and return recommendations based on past orders
        
        // For this simplified version, we'll just return an empty array
        return new Medicine[0];
    }
    
    @Override
    public Order autoReorderMedications(String customerId) {
        if (customerId == null) {
            return null;
        }
        
        // In a real implementation, this would:
        // 1. Check medication refill schedule
        // 2. Determine which medications need refill
        // 3. Create an order for those medications
        
        // For this simplified version, we'll just return null (no refills needed)
        return null;
    }
} 