package com.upb.agripos;

/**
 * Main program untuk mendemonstrasikan exception handling
 * dalam sistem keranjang belanja Agri-POS
 * 
 * Week 9 - Exception Handling Demo
 * Terintegrasi dengan Week 7 (Collections)
 * 
 * @author [Nama Anda]
 * @version 1.0
 */
public class MainExceptionDemo {
    
    public static void main(String[] args) {
        // Header program - GANTI [Nama Anda] dan [NIM Anda]
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║  Hello, I am [Wisnu Wibowo S]-[240320565] (Week9)  ║");
        System.out.println("║     Exception Handling Demo - Agri-POS        ║");
        System.out.println("║   Terintegrasi dengan Week 7 (Collections)    ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");

        // Inisialisasi produk (dengan stok)
        Product p1 = new Product("P01", "Pupuk Organik", 25000, 10);
        Product p2 = new Product("P02", "Bibit Padi", 15000, 20);
        Product p3 = new Product("P03", "Pestisida Alami", 35000, 5);
        Product p4 = new Product("P04", "Pupuk NPK", 45000, 8);
        Product p5 = new Product("P05", "Bibit Jagung", 20000, 15);

        // Display katalog produk
        System.out.println("═══════════════ KATALOG PRODUK ════════════════");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);
        System.out.println("═══════════════════════════════════════════════\n");

        ShoppingCart cart = new ShoppingCart();

        // ===== SKENARIO 1: TESTING EXCEPTION HANDLING =====
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│  SKENARIO 1: TESTING EXCEPTION HANDLING     │");
        System.out.println("└─────────────────────────────────────────────┘\n");

        // Test 1.1: InvalidQuantityException - Quantity Negatif
        System.out.println("▶ Test 1.1: Menambahkan produk dengan quantity negatif");
        try {
            cart.addProduct(p1, -5);
        } catch (InvalidQuantityException e) {
            System.out.println("✗ Exception tertangkap: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("✗ Exception tertangkap: " + e.getMessage());
        }

        // Test 1.2: InvalidQuantityException - Quantity 0
        System.out.println("\n▶ Test 1.2: Menambahkan produk dengan quantity 0");
        try {
            cart.addProduct(p2, 0);
        } catch (InvalidQuantityException e) {
            System.out.println("✗ Exception tertangkap: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("✗ Exception tertangkap: " + e.getMessage());
        }

        // Test 2: ProductNotFoundException
        System.out.println("\n▶ Test 2: Menghapus produk yang tidak ada dalam keranjang");
        try {
            cart.removeProduct(p1);
        } catch (ProductNotFoundException e) {
            System.out.println("✗ Exception tertangkap: " + e.getMessage());
        }

        // Test 3: InsufficientStockException
        System.out.println("\n▶ Test 3: Menambahkan produk melebihi stok tersedia");
        try {
            cart.addProduct(p3, 10); // Stok hanya 5
        } catch (InvalidQuantityException e) {
            System.out.println("✗ Exception tertangkap: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("✗ Exception tertangkap: " + e.getMessage());
        }

        // ===== SKENARIO 2: OPERASI NORMAL (SUKSES) =====
        System.out.println("\n\n┌─────────────────────────────────────────────┐");
        System.out.println("│  SKENARIO 2: OPERASI BERBELANJA NORMAL      │");
        System.out.println("└─────────────────────────────────────────────┘\n");

        System.out.println("▶ Menambahkan produk ke keranjang:");
        try {
            cart.addProduct(p1, 3);
            cart.addProduct(p2, 5);
            cart.addProduct(p3, 2);
            cart.addProduct(p4, 1);
        } catch (InvalidQuantityException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        // Tampilkan isi keranjang
        cart.displayCart();

        // Test update quantity
        System.out.println("▶ Update quantity produk:");
        try {
            cart.updateQuantity(p1, 5);
        } catch (InvalidQuantityException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (ProductNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        cart.displayCart();

        // ===== SKENARIO 3: TRY-CATCH-FINALLY =====
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│  SKENARIO 3: DEMONSTRASI TRY-CATCH-FINALLY  │");
        System.out.println("└─────────────────────────────────────────────┘\n");

        System.out.println("▶ Mencoba hapus produk dengan finally block:");
        try {
            System.out.println("  - Mencoba menghapus Bibit Padi...");
            cart.removeProduct(p2);
        } catch (ProductNotFoundException e) {
            System.out.println("  ✗ Catch: " + e.getMessage());
        } finally {
            System.out.println("  ✓ Finally: Blok ini SELALU dieksekusi");
            System.out.println("  ✓ Bisa digunakan untuk cleanup resources");
        }

        cart.displayCart();

        // ===== SKENARIO 4: CHECKOUT BERHASIL =====
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│  SKENARIO 4: PROSES CHECKOUT                │");
        System.out.println("└─────────────────────────────────────────────┘\n");

        System.out.println("▶ Melakukan checkout:");
        try {
            cart.checkout();
        } catch (InsufficientStockException e) {
            System.out.println("✗ Checkout gagal: " + e.getMessage());
        }

        // Tampilkan stok setelah checkout
        System.out.println("═══════════ STOK SETELAH CHECKOUT ═════════════");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);
        System.out.println("═══════════════════════════════════════════════\n");

        // ===== SKENARIO 5: CHECKOUT GAGAL (STOK TIDAK CUKUP) =====
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│  SKENARIO 5: CHECKOUT GAGAL - STOK HABIS    │");
        System.out.println("└─────────────────────────────────────────────┘\n");

        try {
            System.out.println("▶ Menambahkan produk dengan quantity besar:");
            cart.addProduct(p1, 10); // Stok p1 tersisa 5
        } catch (InvalidQuantityException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("✗ Exception tertangkap: " + e.getMessage());
        }

        // ===== SKENARIO 6: BACKWARD COMPATIBILITY WEEK 7 =====
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│  SKENARIO 6: COMPATIBILITY DENGAN WEEK 7    │");
        System.out.println("└─────────────────────────────────────────────┘\n");

        ShoppingCart cart2 = new ShoppingCart();
        
        System.out.println("▶ Menggunakan method Week 7 (tambahProduk):");
        cart2.tambahProduk(p5, 3);
        cart2.tambahProduk(p4, 2);
        
        System.out.println("\n▶ Menggunakan method Week 7 (tampilkanKeranjang):");
        cart2.tampilkanKeranjang();

        // ===== SKENARIO 7: MULTIPLE EXCEPTION HANDLING =====
        System.out.println("┌─────────────────────────────────────────────┐");
        System.out.println("│  SKENARIO 7: MULTIPLE CATCH BLOCKS          │");
        System.out.println("└─────────────────────────────────────────────┘\n");

        System.out.println("▶ Testing multiple catch dengan berbagai exception:");
        try {
            cart2.addProduct(p5, -1); // InvalidQuantityException
        } catch (InvalidQuantityException e) {
            System.out.println("✗ Caught InvalidQuantityException: " + e.getMessage());
        } catch (InsufficientStockException e) {
            System.out.println("✗ Caught InsufficientStockException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Caught generic Exception: " + e.getMessage());
        } finally {
            System.out.println("✓ Finally block dieksekusi untuk cleanup");
        }

        // ===== BONUS: DEMO SINGLETON PATTERN =====
        System.out.println("\n┌─────────────────────────────────────────────┐");
        System.out.println("│  BONUS: SINGLETON PATTERN DEMO              │");
        System.out.println("└─────────────────────────────────────────────┘\n");

        System.out.println("▶ Menggunakan ProductService (Singleton):");
        ProductService service = ProductService.getInstance();
        service.displayAllProducts();

        System.out.println("▶ Mencari produk dengan kode P03:");
        service.findProductByCode("P03").ifPresent(p -> {
            System.out.println("  Ditemukan: " + p);
        });

        System.out.println("\n▶ Produk dengan stok rendah (< 10):");
        service.displayLowStockProducts(10);

        // ===== SUMMARY =====
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║              RINGKASAN PEMBELAJARAN            ║");
        System.out.println("╠════════════════════════════════════════════════╣");
        System.out.println("║ ✓ Custom Exception berhasil dibuat            ║");
        System.out.println("║   - InvalidQuantityException                   ║");
        System.out.println("║   - ProductNotFoundException                   ║");
        System.out.println("║   - InsufficientStockException                 ║");
        System.out.println("║                                                ║");
        System.out.println("║ ✓ Try-Catch-Finally diimplementasikan          ║");
        System.out.println("║ ✓ Exception terintegrasi di Shopping Cart     ║");
        System.out.println("║ ✓ Backward compatibility dengan Week 7        ║");
        System.out.println("║ ✓ Validasi bisnis pada setiap operasi         ║");
        System.out.println("║ ✓ Singleton Pattern untuk ProductService      ║");
        System.out.println("╚════════════════════════════════════════════════╝");
    }
}
