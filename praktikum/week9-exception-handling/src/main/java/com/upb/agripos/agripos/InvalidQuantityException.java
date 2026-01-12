package com.upb.agripos;

/**
 * Custom Exception untuk menangani kesalahan input quantity yang tidak valid
 * Dilempar ketika quantity produk <= 0 atau tidak sesuai aturan bisnis
 * 
 * Week 9 - Exception Handling
 * @author [Nama Anda]
 * @version 1.0
 */
public class InvalidQuantityException extends Exception {
    
    /**
     * Constructor dengan pesan error
     * @param message Pesan error yang akan ditampilkan
     */
    public InvalidQuantityException(String message) {
        super(message);
    }
    
    /**
     * Constructor dengan pesan dan cause
     * @param message Pesan error
     * @param cause Penyebab exception
     */
    public InvalidQuantityException(String message, Throwable cause) {
        super(message, cause);
    }
}