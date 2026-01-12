package com.upb.agripos;

/**
 * Custom Exception untuk menangani kasus stok produk tidak mencukupi
 * Dilempar ketika quantity yang diminta melebihi stok yang tersedia
 * 
 * Week 9 - Exception Handling
 * @author [Nama Anda]
 * @version 1.0
 */
public class InsufficientStockException extends Exception {
    
    /**
     * Constructor dengan pesan error
     * @param message Pesan error yang akan ditampilkan
     */
    public InsufficientStockException(String message) {
        super(message);
    }
    
    /**
     * Constructor dengan pesan dan cause
     * @param message Pesan error
     * @param cause Penyebab exception
     */
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}