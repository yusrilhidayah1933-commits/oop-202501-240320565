package com.upb.agripos;

/**
 * Custom Exception untuk menangani kasus produk tidak ditemukan
 * Dilempar ketika operasi dilakukan pada produk yang tidak ada dalam keranjang
 * 
 * Week 9 - Exception Handling
 * @author [Nama Anda]
 * @version 1.0
 */
public class ProductNotFoundException extends Exception {
    
    /**
     * Constructor dengan pesan error
     * @param message Pesan error yang akan ditampilkan
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
    
    /**
     * Constructor dengan pesan dan cause
     * @param message Pesan error
     * @param cause Penyebab exception
     */
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
