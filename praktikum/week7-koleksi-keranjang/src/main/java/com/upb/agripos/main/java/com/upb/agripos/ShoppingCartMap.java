package main.java.com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartMap {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p) { 
        items.put(p, items.getOrDefault(p, 0) + 1); 
        System.out.println("✓ Produk ditambahkan: " + p.getName() + " (Qty: " + items.get(p) + ")");
    }

    public void removeProduct(Product p) {
        if (!items.containsKey(p)) {
            System.out.println("✗ Produk tidak ditemukan: " + p.getName());
            return;
        }
        int qty = items.get(p);
        if (qty > 1) {
            items.put(p, qty - 1);
            System.out.println("✓ Qty dikurangi: " + p.getName() + " (Sisa: " + items.get(p) + ")");
        } else {
            items.remove(p);
            System.out.println("✓ Produk dihapus: " + p.getName());
        }
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void printCart() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         ISI KERANJANG (HashMap)        ║");
        System.out.println("╚════════════════════════════════════════╝");
        
        if (items.isEmpty()) {
            System.out.println("  Keranjang kosong");
        } else {
            int no = 1;
            for (Map.Entry<Product, Integer> e : items.entrySet()) {
                Product p = e.getKey();
                int qty = e.getValue();
                double subtotal = p.getPrice() * qty;
                System.out.printf("  %d. %s - %s%n", no++, p.getCode(), p.getName());
                System.out.printf("     Rp %.2f x %d = Rp %.2f%n", 
                    p.getPrice(), qty, subtotal);
            }
        }
        System.out.println("─────────────────────────────────────────");
        System.out.printf("  TOTAL: Rp %.2f%n", getTotal());
        System.out.println("═════════════════════════════════════════\n");
    }
}