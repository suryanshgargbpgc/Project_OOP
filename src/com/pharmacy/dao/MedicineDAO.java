package com.pharmacy.dao;

import com.pharmacy.model.Medicine;
import java.util.List;

/**
 * Data Access Object interface for Medicine entities
 */
public interface MedicineDAO {
    
    /**
     * Save a medicine to the database
     * 
     * @param medicine The medicine to save
     * @return The saved medicine with potentially updated ID
     */
    Medicine save(Medicine medicine);
    
    /**
     * Find a medicine by its ID
     * 
     * @param medicineId The ID of the medicine to find
     * @return The found medicine, or null if not found
     */
    Medicine findById(String medicineId);
    
    /**
     * Find medicines by their name (partial match)
     * 
     * @param name The name to search for
     * @return List of medicines matching the name
     */
    List<Medicine> findByName(String name);
    
    /**
     * Find medicines by manufacturer
     * 
     * @param manufacturer The manufacturer to search for
     * @return List of medicines from the manufacturer
     */
    List<Medicine> findByManufacturer(String manufacturer);
    
    /**
     * Find medicines by category
     * 
     * @param category The category to filter by
     * @return List of medicines in the category
     */
    List<Medicine> findByCategory(String category);
    
    /**
     * Find medicines that require a prescription
     * 
     * @return List of prescription medicines
     */
    List<Medicine> findPrescriptionMedicines();
    
    /**
     * Find medicines that don't require a prescription (OTC)
     * 
     * @return List of OTC medicines
     */
    List<Medicine> findOTCMedicines();
    
    /**
     * Find medicines that are in stock
     * 
     * @return List of in-stock medicines
     */
    List<Medicine> findInStockMedicines();
    
    /**
     * Find medicines that are out of stock
     * 
     * @return List of out-of-stock medicines
     */
    List<Medicine> findOutOfStockMedicines();
    
    /**
     * Find medicines below a certain price
     * 
     * @param maxPrice The maximum price
     * @return List of medicines below the price
     */
    List<Medicine> findByPriceLessThan(double maxPrice);
    
    /**
     * Get all medicines
     * 
     * @return List of all medicines
     */
    List<Medicine> findAll();
    
    /**
     * Update an existing medicine
     * 
     * @param medicine The medicine to update
     * @return The updated medicine
     */
    Medicine update(Medicine medicine);
    
    /**
     * Delete a medicine by its ID
     * 
     * @param medicineId The ID of the medicine to delete
     * @return true if deleted successfully, false otherwise
     */
    boolean deleteById(String medicineId);
    
    /**
     * Get the total number of medicines in the system
     * 
     * @return The total count
     */
    int getTotalCount();
} 