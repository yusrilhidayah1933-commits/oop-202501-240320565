package com.upb.agripos.model;

public class Product {
    private String code;
    private String name;
    private String category;
    private double price;
    private int stock;
    
    // Constructors
    public Product() {}
    
    public Product(String code, String name, double price, int stock) {
        this(code, name, "Agriculture", price, stock);
    }
    
    public Product(String code, String name, String category, double price, int stock) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }
    
    // Getters and Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    
    // Business methods
    public void reduceStock(int quantity) {
        if (quantity > 0 && stock >= quantity) {
            stock -= quantity;
        }
    }
    
    public void restoreStock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }
    
    public double calculateSubtotal(int quantity) {
        return price * quantity;
    }
    
    @Override
    public String toString() {
        return String.format("%s - %s (Rp%,.0f)", code, name, price);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return code.equals(product.code);
    }
    
    @Override
    public int hashCode() {
        return code.hashCode();
    }
}