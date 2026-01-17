package com.upb.agripos.model;

public class TransactionDetail {
    private int id;
    private String transactionId;
    private String productCode;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    
    public TransactionDetail() {}
    
    public TransactionDetail(String transactionId, String productCode, 
                           String productName, int quantity, double unitPrice) {
        this.transactionId = transactionId;
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = quantity * unitPrice;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { 
        this.quantity = quantity;
        this.subtotal = quantity * unitPrice;
    }
    
    public double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(double unitPrice) { 
        this.unitPrice = unitPrice;
        this.subtotal = quantity * unitPrice;
    }
    
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    
    @Override
    public String toString() {
        return String.format("%s x%d @Rp%,.0f = Rp%,.0f", 
            productName, quantity, unitPrice, subtotal);
    }
}