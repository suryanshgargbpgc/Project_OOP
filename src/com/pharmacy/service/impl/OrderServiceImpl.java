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

public class OrderServiceImpl implements OrderService {

    private Map<String, Order> orders = new HashMap<>();

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

        Order order = new Order(customerId, shippingAddress);
        

        orders.put(order.getID(), order);
        
        return order;
    }
    
    @Override
    public Order placePrescriptionOrder(String customerId, String prescriptionId, String shippingAddress) {
        if (customerId == null || prescriptionId == null || shippingAddress == null) {
            return null;
        }
        
        Order order = new Order(customerId, shippingAddress);
        orders.put(order.getID(), order);
        
        return order;
    }
    
    @Override
    public Order placeEmergencyOrder(String customerId, Map<String, Integer> medicineQuantities, String shippingAddress) {
        if (customerId == null || medicineQuantities == null || shippingAddress == null) {
            return null;
        }
        
        Order order = new Order(customerId, shippingAddress);
        order.status = "EMERGENCY";

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
        
        if (STATUS_SHIPPED.equals(order.status) || STATUS_DELIVERED.equals(order.status)) {
            return false;
        }
        
        order.status = STATUS_CANCELLED;
        
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
        
        for (Order order : orders.values()) {
            if (order.getCustID().equals(customerId)) {
                customerOrders.add(order);
            }
        }
        
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
        
        Map<String, Object> trackingInfo = new HashMap<>();
        trackingInfo.put("orderId", order.getID());
        trackingInfo.put("status", order.getStatus());
        trackingInfo.put("lastUpdated", new Date());
        
        return trackingInfo;
    }
    
    @Override
    public Order[] getOrderHistory(String customerId) {
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
        
        if (STATUS_SHIPPED.equals(order.status) ||
            STATUS_DELIVERED.equals(order.status) || 
            STATUS_CANCELLED.equals(order.status)) {
            return null;
        }
        
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
        
        if (STATUS_SHIPPED.equals(order.status) ||
            STATUS_DELIVERED.equals(order.status) || 
            STATUS_CANCELLED.equals(order.status)) {
            return null;
        }
        
        return order;
    }
    
    @Override
    public Medicine[] getRecommendedMedicines(String customerId) {
        if (customerId == null) {
            return new Medicine[0];
        }
        return new Medicine[0];
    }
    
    @Override
    public Order autoReorderMedications(String customerId) {
        if (customerId == null) {
            return null;
        }
        return null;
    }
} 