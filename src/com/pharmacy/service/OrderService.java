package com.pharmacy.service;

import com.pharmacy.model.Order;
import com.pharmacy.model.Medicine;
import java.util.Map;

/**
 * Interface for order-related services
 */
public interface OrderService {
    
    /**
     * Place a new order
     * 
     * @param customerId The ID of the customer
     * @param medicineQuantities Map of medicine IDs and quantities
     * @param shippingAddress Shipping address
     * @return The created order
     */
    Order placeOrder(String customerId, Map<String, Integer> medicineQuantities, String shippingAddress);
    
    /**
     * Place a prescription order
     * 
     * @param customerId The ID of the customer
     * @param prescriptionId The ID of the prescription
     * @param shippingAddress Shipping address
     * @return The created order
     */
    Order placePrescriptionOrder(String customerId, String prescriptionId, String shippingAddress);
    
    /**
     * Place an emergency order
     * 
     * @param customerId The ID of the customer
     * @param medicineQuantities Map of medicine IDs and quantities
     * @param shippingAddress Shipping address
     * @return The created emergency order
     */
    Order placeEmergencyOrder(String customerId, Map<String, Integer> medicineQuantities, String shippingAddress);
    
    /**
     * Cancel an order
     * 
     * @param orderId The ID of the order to cancel
     * @param reason The reason for cancellation
     * @return true if cancelled successfully, false otherwise
     */
    boolean cancelOrder(String orderId, String reason);
    
    /**
     * Update the status of an order
     * 
     * @param orderId The ID of the order
     * @param newStatus The new status as a String
     * @return The updated order
     */
    Order updateOrderStatus(String orderId, String newStatus);
    
    /**
     * Get an order by ID
     * 
     * @param orderId The ID of the order
     * @return The order object
     */
    Order getOrder(String orderId);
    
    /**
     * Get all orders for a customer
     * 
     * @param customerId The ID of the customer
     * @return Array of orders
     */
    Order[] getCustomerOrders(String customerId);
    
    /**
     * Get order tracking information
     * 
     * @param orderId The ID of the order
     * @return Map containing tracking information
     */
    Map<String, Object> getOrderTracking(String orderId);
    
    /**
     * Get order history for a customer
     * 
     * @param customerId The ID of the customer
     * @return Array of past orders
     */
    Order[] getOrderHistory(String customerId);
    
    /**
     * Add items to an existing order
     * 
     * @param orderId The ID of the order
     * @param medicineQuantities Map of medicine IDs and quantities to add
     * @return The updated order
     */
    Order addItemsToOrder(String orderId, Map<String, Integer> medicineQuantities);
    
    /**
     * Remove items from an existing order
     * 
     * @param orderId The ID of the order
     * @param medicineIds Array of medicine IDs to remove
     * @return The updated order
     */
    Order removeItemsFromOrder(String orderId, String[] medicineIds);
    
    /**
     * Get recommended medicines based on order history
     * 
     * @param customerId The ID of the customer
     * @return Array of recommended medicines
     */
    Medicine[] getRecommendedMedicines(String customerId);
    
    /**
     * Auto-reorder medications that need refill
     * 
     * @param customerId The ID of the customer
     * @return The created refill order if any medicines need refill, null otherwise
     */
    Order autoReorderMedications(String customerId);
} 