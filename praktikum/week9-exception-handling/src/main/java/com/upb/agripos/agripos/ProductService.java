
package com.upb.agripos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * ProductService menggunakan Singleton Pattern
 * Memastikan hanya ada satu instance service untuk mengelola produk
 * 
 * Week 9 - Design Pattern Implementation
 * @author [Nama Anda]
 * @version 1.0
 */
public class ProductService {
    
    // Instance tunggal (Singleton)
    private static ProductService instance;
    
    // Data produk dalam memory
    private final List<Product> products;
    
    /**
     * Private constructor untuk mencegah instantiation dari luar class
     * Ini adalah kunci dari Singleton Pattern
     */
    private ProductService() {
        products = new ArrayList<>();
        initializeProducts();
    }
    
    /**
     * Mendapatkan instance tunggal ProductService (Singleton Pattern)
     * Method ini adalah satu-satunya cara untuk mendapatkan instance
     * 
     * @return Instance ProductService yang sama setiap kali dipanggil
     */
    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }
    
    /**
     * Inisialisasi data produk awal
     * Dipanggil saat instance pertama kali dibuat
     */
    private void initializeProducts() {
        products.add(new Product("P01", "Pupuk Organik", 25000, 50));
        products.add(new Product("P02", "Bibit Padi", 15000, 100));
        products.add(new Product("P03", "Pestisida Alami", 35000, 30));
        products.add(new Product("P04", "Pupuk NPK", 45000, 40));
        products.add(new Product("P05", "Bibit Jagung", 20000, 75));
        products.add(new Product("P06", "Pupuk Urea", 30000, 60));
        products.add(new Product("P07", "Bibit Cabai", 12000, 80));
        products.add(new Product("P08", "Herbisida", 40000, 25));
    }
    
    /**
     * Mendapatkan semua produk
     * 
     * @return List baru berisi semua produk (defensive copy)
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    /**
     * Mencari produk berdasarkan kode
     * 
     * @param code Kode produk yang dicari
     * @return Optional berisi Product jika ditemukan, empty jika tidak
     */
    public Optional<Product> findProductByCode(String code) {
        return products.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst();
    }
    
    /**
     * Mencari produk berdasarkan nama (case insensitive, partial match)
     * 
     * @param name Nama atau sebagian nama produk
     * @return List produk yang cocok
     */
    public List<Product> findProductsByName(String name) {
        List<Product> results = new ArrayList<>();
        for (Product p : products) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }
    
    /**
     * Mencari produk berdasarkan range harga
     * 
     * @param minPrice Harga minimum
     * @param maxPrice Harga maksimum
     * @return List produk dalam range harga
     */
    public List<Product> findProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> results = new ArrayList<>();
        for (Product p : products) {
            if (p.getPrice() >= minPrice && p.getPrice() <= maxPrice) {
                results.add(p);
            }
        }
        return results;
    }
    
    /**
     * Menambahkan produk baru ke katalog
     * 
     * @param product Produk yang akan ditambahkan
     * @return true jika berhasil, false jika produk sudah ada
     */
    public boolean addProduct(Product product) {
        // Cek apakah produk dengan kode yang sama sudah ada
        if (findProductByCode(product.getCode()).isPresent()) {
            return false;
        }
        products.add(product);
        return true;
    }
    
    /**
     * Menghapus produk dari katalog berdasarkan kode
     * 
     * @param code Kode produk yang akan dihapus
     * @return true jika berhasil dihapus, false jika tidak ditemukan
     */
    public boolean removeProduct(String code) {
        return products.removeIf(p -> p.getCode().equals(code));
    }
    
    /**
     * Update stok produk
     * 
     * @param code Kode produk
     * @param newStock Stok baru
     * @return true jika berhasil, false jika produk tidak ditemukan
     */
    public boolean updateStock(String code, int newStock) {
        Optional<Product> product = findProductByCode(code);
        if (product.isPresent()) {
            product.get().setStock(newStock);
            return true;
        }
        return false;
    }
    
    /**
     * Mendapatkan jumlah total produk dalam katalog
     * 
     * @return Jumlah produk
     */
    public int getProductCount() {
        return products.size();
    }
    
    /**
     * Menampilkan semua produk ke console
     */
    public void displayAllProducts() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         KATALOG PRODUK AGRI-POS                ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        
        if (products.isEmpty()) {
            System.out.println("  Tidak ada produk dalam katalog\n");
            return;
        }
        
        for (Product p : products) {
            System.out.println("  " + p);
        }
        System.out.println("\nTotal: " + products.size() + " produk");
        System.out.println("════════════════════════════════════════════════\n");
    }
    
    /**
     * Mendapatkan produk dengan stok rendah (< threshold)
     * 
     * @param threshold Batas stok rendah
     * @return List produk dengan stok rendah
     */
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStock = new ArrayList<>();
        for (Product p : products) {
            if (p.getStock() < threshold) {
                lowStock.add(p);
            }
        }
        return lowStock;
    }
    
    /**
     * Menampilkan produk dengan stok rendah
     * 
     * @param threshold Batas stok rendah
     */
    public void displayLowStockProducts(int threshold) {
        List<Product> lowStock = getLowStockProducts(threshold);
        
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║         PRODUK DENGAN STOK RENDAH              ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        
        if (lowStock.isEmpty()) {
            System.out.println("  Semua produk memiliki stok cukup (>= " + threshold + ")\n");
            return;
        }
        
        for (Product p : lowStock) {
            System.out.println("  ⚠️  " + p);
        }
        System.out.println("\nTotal: " + lowStock.size() + " produk perlu restocking");
        System.out.println("════════════════════════════════════════════════\n");
    }
}