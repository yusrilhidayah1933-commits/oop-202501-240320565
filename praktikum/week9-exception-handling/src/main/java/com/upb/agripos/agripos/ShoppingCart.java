package com.upb.agripos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class untuk mengelola keranjang belanja dengan exception handling
 * 
 * Updated Week 9 - Menambahkan exception handling
 * Compatible dengan implementasi Week 7
 * 
 * @author [Nama Anda]
 * @version 2.0
 */
public class ShoppingCart {
    private final Map<Product, Integer> items = new HashMap<>();

    // ==================== WEEK 9 METHODS (WITH EXCEPTION) ====================

    /**
     * Menambahkan produk ke keranjang belanja
     * Week 9 - Dengan exception handling dan validasi stok
     * 
     * @param product Produk yang akan ditambahkan
     * @param quantity Jumlah produk
     * @throws InvalidQuantityException Jika quantity <= 0
     * @throws InsufficientStockException Jika stok tidak mencukupi
     */
    public void addProduct(Product product, int quantity) 
            throws InvalidQuantityException, InsufficientStockException {
        
        // Validasi quantity
        if (quantity <= 0) {
            throw new InvalidQuantityException(
                "Quantity harus lebih dari 0. Quantity yang dimasukkan: " + quantity
            );
        }
        
        // Validasi stok
        int currentQuantity = items.getOrDefault(product, 0);
        int totalQuantity = currentQuantity + quantity;
        
        if (totalQuantity > product.getStock()) {
            throw new InsufficientStockException(
                String.format("Stok tidak mencukupi untuk produk '%s'. " +
                    "Diminta: %d, Tersedia: %d", 
                    product.getName(), totalQuantity, product.getStock())
            );
        }
        
        // Tambahkan ke keranjang
        items.put(product, totalQuantity);
        System.out.println("✓ Produk ditambahkan: " + product.getName() + 
            " x" + quantity);
    }

    /**
     * Update quantity produk yang sudah ada di keranjang
     * Week 9 - Method baru
     * 
     * @param product Produk yang akan diupdate
     * @param newQuantity Quantity baru
     * @throws InvalidQuantityException Jika quantity <= 0
     * @throws InsufficientStockException Jika stok tidak mencukupi
     * @throws ProductNotFoundException Jika produk tidak ada di keranjang
     */
    public void updateQuantity(Product product, int newQuantity) 
            throws InvalidQuantityException, InsufficientStockException, ProductNotFoundException {
        
        if (!items.containsKey(product)) {
            throw new ProductNotFoundException(
                "Produk '" + product.getName() + "' tidak ada dalam keranjang."
            );
        }
        
        if (newQuantity <= 0) {
            throw new InvalidQuantityException(
                "Quantity harus lebih dari 0. Quantity yang dimasukkan: " + newQuantity
            );
        }
        
        if (newQuantity > product.getStock()) {
            throw new InsufficientStockException(
                String.format("Stok tidak mencukupi untuk produk '%s'. " +
                    "Diminta: %d, Tersedia: %d", 
                    product.getName(), newQuantity, product.getStock())
            );
        }
        
        items.put(product, newQuantity);
        System.out.println("✓ Quantity diupdate: " + product.getName() + 
            " menjadi " + newQuantity);
    }

    /**
     * Menghapus produk dari keranjang belanja
     * Week 9 - Dengan exception handling
     * 
     * @param product Produk yang akan dihapus
     * @throws ProductNotFoundException Jika produk tidak ada dalam keranjang
     */
    public void removeProduct(Product product) throws ProductNotFoundException {
        if (!items.containsKey(product)) {
            throw new ProductNotFoundException(
                "Produk '" + product.getName() + "' tidak ada dalam keranjang."
            );
        }
        
        items.remove(product);
        System.out.println("✓ Produk dihapus: " + product.getName());
    }

    /**
     * Melakukan checkout dan mengurangi stok produk
     * Week 9 - Dengan validasi stok dan exception handling
     * 
     * @throws InsufficientStockException Jika stok tidak mencukupi saat checkout
     */
    public void checkout() throws InsufficientStockException {
        if (items.isEmpty()) {
            System.out.println("Keranjang kosong, tidak ada yang di-checkout.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         PROSES CHECKOUT                ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        // Validasi stok untuk semua produk
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            
            if (product.getStock() < quantity) {
                throw new InsufficientStockException(
                    String.format("Stok tidak cukup untuk: %s. " +
                        "Diminta: %d, Tersedia: %d",
                        product.getName(), quantity, product.getStock())
                );
            }
        }
        
        // Kurangi stok dan hitung total
        double totalPrice = 0;
        System.out.println("\nDetail Pembelian:");
        System.out.println("─────────────────────────────────────────");
        
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            
            product.reduceStock(quantity);
            double subtotal = product.getPrice() * quantity;
            totalPrice += subtotal;
            
            System.out.printf("%-20s x%-3d @ Rp %8.0f = Rp %10.0f\n", 
                product.getName(), quantity, product.getPrice(), subtotal);
        }
        
        System.out.println("─────────────────────────────────────────");
        System.out.printf("TOTAL PEMBAYARAN:              Rp %10.0f\n", totalPrice);
        System.out.println("═════════════════════════════════════════");
        System.out.println("✓ Checkout berhasil! Terima kasih.\n");
        
        // Kosongkan keranjang setelah checkout
        items.clear();
    }

    // ==================== WEEK 7 COMPATIBILITY METHODS ====================

    /**
     * Menambahkan produk tanpa exception (Week 7 compatibility)
     * Exception ditangani secara internal
     * 
     * @param product Produk yang akan ditambahkan
     * @param quantity Jumlah produk
     */
    public void tambahProduk(Product product, int quantity) {
        try {
            addProduct(product, quantity);
        } catch (InvalidQuantityException | InsufficientStockException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Hapus produk tanpa exception (Week 7 compatibility)
     * Exception ditangani secara internal
     * 
     * @param product Produk yang akan dihapus
     */
    public void hapusProduk(Product product) {
        try {
            removeProduct(product);
        } catch (ProductNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Tampilkan keranjang (Week 7 compatibility)
     * Alias untuk displayCart()
     */
    public void tampilkanKeranjang() {
        displayCart();
    }

    // ==================== DISPLAY & UTILITY METHODS ====================

    /**
     * Menampilkan isi keranjang belanja
     * Week 9 - Format tampilan diperbaiki
     */
    public void displayCart() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         ISI KERANJANG BELANJA          ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (items.isEmpty()) {
            System.out.println("  Keranjang masih kosong\n");
            return;
        }
        
        double total = 0;
        int itemNumber = 1;
        
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            double subtotal = product.getPrice() * quantity;
            total += subtotal;
            
            System.out.printf("%d. %-20s x%-3d\n", itemNumber++, product.getName(), quantity);
            System.out.printf("   Rp %8.0f x %d = Rp %10.0f\n", 
                product.getPrice(), quantity, subtotal);
        }
        
        System.out.println("─────────────────────────────────────────");
        System.out.printf("Total Item: %d\n", getItemCount());
        System.out.printf("Total Harga: Rp %10.0f\n\n", total);
    }

    /**
     * Mendapatkan daftar produk dalam keranjang
     * Week 7 compatibility
     * 
     * @return List produk dalam keranjang
     */
    public List<Product> getProducts() {
        return new ArrayList<>(items.keySet());
    }

    /**
     * Mendapatkan quantity produk tertentu
     * 
     * @param product Produk yang dicari
     * @return Quantity produk, atau 0 jika tidak ada
     */
    public int getQuantity(Product product) {
        return items.getOrDefault(product, 0);
    }

    /**
     * Mendapatkan jumlah total item dalam keranjang
     * Week 7 compatibility
     * 
     * @return Jumlah total item
     */
    public int getItemCount() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    /**
     * Mendapatkan total harga keranjang
     * Week 7 compatibility
     * 
     * @return Total harga
     */
    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    /**
     * Mengosongkan keranjang
     */
    public void clear() {
        items.clear();
        System.out.println("✓ Keranjang dikosongkan");
    }

    /**
     * Cek apakah keranjang kosong
     * 
     * @return true jika kosong
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Mendapatkan jumlah jenis produk dalam keranjang
     * 
     * @return Jumlah jenis produk
     */
    public int getProductCount() {
        return items.size();
    }
}