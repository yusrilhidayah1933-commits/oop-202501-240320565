package com.upb.agripos.dao;

import com.upb.agripos.model.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionDAO {
    // Create
    boolean insert(Transaction transaction);
    
    // Read
    Transaction findById(String id);
    List<Transaction> findAll();
    List<Transaction> findByDate(LocalDate date);
    List<Transaction> findByCashier(String cashierId);
    
    // Report
    double getTotalSales(LocalDate startDate, LocalDate endDate);
    int getTransactionCount(LocalDate startDate, LocalDate endDate);
    List<Object[]> getDailySalesReport(LocalDate startDate, LocalDate endDate);
    
    // Utility
    String generateTransactionId();
}