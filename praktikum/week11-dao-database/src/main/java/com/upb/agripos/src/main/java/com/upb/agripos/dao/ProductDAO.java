package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDAO {
    // Create
    boolean insert(Product product);
    
    // Read
    Product findByCode(String code);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    
    // Update
    boolean update(Product product);
    boolean updateStock(String code, int newStock);
    
    // Delete
    boolean delete(String code);
    
    // Utility
    int count();
    boolean exists(String code);
    
    // Additional methods
    List<Product> searchByName(String keyword);
    boolean reduceStock(String code, int quantity);
    List<Product> getLowStockProducts(int threshold);
}