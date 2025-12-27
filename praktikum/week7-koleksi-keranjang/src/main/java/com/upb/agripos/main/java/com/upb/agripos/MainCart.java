package main.java.com.upb.agripos;

public class MainCart {
    public static void main(String[] args) {
        // GANTI DENGAN NAMA DAN NIM ANDA
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║  Hello, I am Wisnu-240320565 (Week7)   ║");
        System.out.println("║  Agri-POS Shopping Cart System         ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        // ========================================
        // DEMO 1: ArrayList Implementation
        // ========================================
        System.out.println(">>> DEMO 1: SHOPPING CART (ArrayList) <<<\n");
        
        Product p1 = new Product("P01", "Beras Premium", 50000);
        Product p2 = new Product("P02", "Pupuk Organik", 30000);
        Product p3 = new Product("P03", "Bibit Jagung", 25000);

        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(p1);
        cart.addProduct(p2);
        cart.addProduct(p3);
        cart.printCart();

        cart.removeProduct(p1);
        cart.printCart();

        // ========================================
        // DEMO 2: HashMap Implementation (dengan Quantity)
        // ========================================
        System.out.println("\n>>> DEMO 2: SHOPPING CART (HashMap) <<<\n");
        
        Product p4 = new Product("P04", "Pestisida", 75000);
        Product p5 = new Product("P05", "Alat Semprot", 150000);

        ShoppingCartMap cartMap = new ShoppingCartMap();
        
        // Tambah produk yang sama beberapa kali
        cartMap.addProduct(p1);  // Beras
        cartMap.addProduct(p1);  // Beras lagi (qty +1)
        cartMap.addProduct(p2);  // Pupuk
        cartMap.addProduct(p4);  // Pestisida
        cartMap.addProduct(p4);  // Pestisida lagi (qty +1)
        cartMap.addProduct(p5);  // Alat Semprot
        cartMap.printCart();

        // Hapus 1 qty dari produk yang qty > 1
        cartMap.removeProduct(p1);  // Beras qty 2 -> 1
        cartMap.removeProduct(p4);  // Pestisida qty 2 -> 1
        cartMap.printCart();

        // Hapus seluruh produk
        cartMap.removeProduct(p1);  // Beras qty 1 -> dihapus
        cartMap.printCart();

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║     Program Selesai - Terima Kasih     ║");
        System.out.println("╚════════════════════════════════════════╝");
    }
}