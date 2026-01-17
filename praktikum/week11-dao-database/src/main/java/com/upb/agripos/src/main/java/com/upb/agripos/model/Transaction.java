package com.upb.agripos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private String id;
    private LocalDateTime transactionDate;
    private double totalAmount;
    private String paymentMethod;
    private String paymentDetails;
    private String cashierId;
    private List<TransactionDetail> details;
    
    public Transaction() {
        this.details = new ArrayList<>();
        this.transactionDate = LocalDateTime.now();
    }
    
    public Transaction(String id, double totalAmount, String paymentMethod) {
        this();
        this.id = id;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(String paymentDetails) { this.paymentDetails = paymentDetails; }
    
    public String getCashierId() { return cashierId; }
    public void setCashierId(String cashierId) { this.cashierId = cashierId; }
    
    public List<TransactionDetail> getDetails() { return details; }
    public void setDetails(List<TransactionDetail> details) { this.details = details; }
    
    public void addDetail(TransactionDetail detail) {
        this.details.add(detail);
    }
    
    @Override
    public String toString() {
        return String.format("Transaction[%s] - %s - Rp%,.0f", 
            id, transactionDate, totalAmount);
    }
}