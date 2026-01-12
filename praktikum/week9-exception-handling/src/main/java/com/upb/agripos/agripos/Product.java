package com.upb.agripos;

/**
 * Model class untuk merepresentasikan produk dalam sistem Agri-POS
 * Menyimpan informasi produk termasuk kode, nama, harga, dan stok
 * 
 * Updated Week 9 - Menambahkan fitur stok management
 * Compatible dengan Week 7
 * 
 * @author [Nama Anda]
 * @version 2.0
 */
public class Product {
    private final String code;
    private final String name;
    private final double price;
    private int stock; // Ditambahkan untuk Week 9

    /**
     * Constructor untuk membuat objek Product dengan stok
     * Week 9 Version
     * 
     * @param code Kode unik produk
     * @param name Nama produk
     * @param price Harga produk
     * @param stock Jumlah stok tersedia
     */
    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    /**
     * Constructor overload untuk backward compatibility dengan Week 7
     * Default stok = 100
     * 
     * @param code Kode unik produk
     * @param name Nama produk
     * @param price Harga produk
     */
    public Product(String code, String name, double price) {
        this(code, name, price, 100); // Default stok 100
    }

    // ==================== GETTERS ====================
    
    /**
     * Getter untuk kode produk
     * @return Kode produk
     */
    public String getCode() { 
        return code; 
    }

    /**
     * Getter untuk nama produk
     * @return Nama produk
     */
    public String getName() { 
        return name; 
    }

    /**
     * Getter untuk harga produk
     * @return Harga produk
     */
    public double getPrice() { 
        return price; 
    }

    /**
     * Getter untuk stok produk
     * @return Jumlah stok tersedia
     */
    public int getStock() { 
        return stock; 
    }

    // ==================== STOCK MANAGEMENT ====================

    /**
     * Mengurangi stok produk setelah pembelian
     * Week 9 - Untuk checkout process
     * 
     * @param qty Jumlah yang akan dikurangi dari stok
     */
    public void reduceStock(int qty) { 
        this.stock -= qty; 
    }

    /**
     * Menambah stok produk (untuk restocking)
     * Week 9 - Untuk restock process
     * 
     * @param qty Jumlah yang akan ditambahkan ke stok
     */
    public void addStock(int qty) {
        this.stock += qty;
    }

    /**
     * Set stok produk ke nilai tertentu
     * 
     * @param stock Nilai stok baru
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    // ==================== UTILITY METHODS ====================

    /**
     * Override toString untuk menampilkan informasi produk
     * @return String representasi produk
     */
    @Override
    public String toString() {
        return String.format("%s - %s (Rp %.0f) | Stok: %d", 
            code, name, price, stock);
    }

    /**
     * Override equals untuk perbandingan produk berdasarkan code
     * Penting untuk HashMap di ShoppingCart
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return code.equals(product.code);
    }

    /**
     * Override hashCode konsisten dengan equals
     * Penting untuk HashMap di ShoppingCart
     */
    @Override
    public int hashCode() {
        return code.hashCode();
    }
}