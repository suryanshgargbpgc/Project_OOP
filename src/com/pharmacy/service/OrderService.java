package com.pharmacy.service;

import com.pharmacy.model.Order;
import com.pharmacy.model.Medicine;
import java.util.Map;

public interface OrderService {

    Order placeOrder(String customerId, Map<String, Integer> medicineQuantities, String shippingAddress);

    Order placePrescriptionOrder(String customerId, String prescriptionId, String shippingAddress);

    Order placeEmergencyOrder(String customerId, Map<String, Integer> medicineQuantities, String shippingAddress);

    boolean cancelOrder(String orderId, String reason);

    Order updateOrderStatus(String orderId, String newStatus);

    Order getOrder(String orderId);

    Order[] getCustomerOrders(String customerId);

    Map<String, Object> getOrderTracking(String orderId);

    Order[] getOrderHistory(String customerId);

    Order addItemsToOrder(String orderId, Map<String, Integer> medicineQuantities);

    Order removeItemsFromOrder(String orderId, String[] medicineIds);

    Medicine[] getRecommendedMedicines(String customerId);

    Order autoReorderMedications(String customerId);
} 