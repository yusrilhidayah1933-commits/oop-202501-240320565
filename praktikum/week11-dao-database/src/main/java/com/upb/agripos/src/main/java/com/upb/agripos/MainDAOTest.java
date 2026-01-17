package com.upb.agripos;

import com.upb.agripos.config.DatabaseConnection;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.dao.JdbcTransactionDAO;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionDetail;
import java.time.LocalDate;
import java.util.List;

public class MainDAOTest {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  WEEK 11: DAO & Java DB (Derby) - NO DOWNLOAD NEEDED!    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("=== FEATURES ===");
        System.out.println("• Uses Java Built-in Database (Derby)");
        System.out.println("• No external downloads required");
        System.out.println("• Auto-creates database and tables");
        System.out.println("• Includes sample data");
        System.out.println("• 100% JDBC compliant\n");
        
        // Test database connection
        DatabaseConnection.testConnection();
        
        // Create DAOs
        JdbcProductDAO productDAO = new JdbcProductDAO();
        JdbcTransactionDAO transactionDAO = new JdbcTransactionDAO();
        
        System.out.println("\n=== PRODUCT CRUD OPERATIONS ===");
        
        // 1. Display all products
        System.out.println("\n1. All Products:");
        List<Product> allProducts = productDAO.findAll();
        System.out.printf("Total products: %d%n", allProducts.size());
        System.out.println("┌─────────────────────────────────────────────────────────┐");
        System.out.printf("│ %-8s %-20s %-12s %-8s │%n", "CODE", "NAME", "PRICE", "STOCK");
        System.out.println("├─────────────────────────────────────────────────────────┤");
        for (Product p : allProducts) {
            System.out.printf("│ %-8s %-20s Rp%,-9.0f %-8d │%n",
                p.getCode(), p.getName(), p.getPrice(), p.getStock());
        }
        System.out.println("└─────────────────────────────────────────────────────────┘");
        
        // 2. Find product by code
        System.out.println("\n2. Find Product by Code (P01):");
        Product p01 = productDAO.findByCode("P01");
        if (p01 != null) {
            System.out.printf("  Found: %s - %s (Rp%,.0f, Stock: %d)%n",
                p01.getCode(), p01.getName(), p01.getPrice(), p01.getStock());
        }
        
        // 3. Insert new product
        System.out.println("\n3. Insert New Product:");
        Product newProduct = new Product("P11", "Tepung Terigu", "Bahan Pokok", 18000, 50);
        boolean inserted = productDAO.insert(newProduct);
        System.out.println("  Insert success: " + inserted);
        
        // 4. Update product
        System.out.println("\n4. Update Product:");
        newProduct.setPrice(20000);
        newProduct.setStock(60);
        boolean updated = productDAO.update(newProduct);
        System.out.println("  Update success: " + updated);
        
        // 5. Delete product
        System.out.println("\n5. Delete Product:");
        boolean deleted = productDAO.delete("P11");
        System.out.println("  Delete success: " + deleted);
        
        System.out.println("\n=== TRANSACTION OPERATIONS ===");
        
        // 1. Generate transaction ID
        System.out.println("\n1. Generate Transaction ID:");
        String txId = transactionDAO.generateTransactionId();
        System.out.println("  Generated ID: " + txId);
        
        // 2. Create transaction
        System.out.println("\n2. Create Transaction:");
        Transaction transaction = new Transaction(txId, 275000, "CASH");
        transaction.setPaymentDetails("Cash payment - Change: 25000");
        transaction.setCashierId("CASHIER001");
        
        transaction.addDetail(new TransactionDetail(txId, "P01", "Beras Premium", 2, 50000));
        transaction.addDetail(new TransactionDetail(txId, "P02", "Pupuk Organik", 1, 30000));
        transaction.addDetail(new TransactionDetail(txId, "P07", "Minyak Goreng", 3, 25000));
        
        System.out.println("  Transaction details:");
        for (TransactionDetail detail : transaction.getDetails()) {
            System.out.printf("    %s x%d = Rp%,.0f%n",
                detail.getProductName(), detail.getQuantity(), detail.getSubtotal());
        }
        System.out.printf("  Total: Rp%,.0f%n", transaction.getTotalAmount());
        
        // 3. Insert transaction
        boolean txSuccess = transactionDAO.insert(transaction);
        System.out.println("  Transaction saved: " + txSuccess);
        
        System.out.println("\n=== SALES REPORTING ===");
        
        LocalDate today = LocalDate.now();
        LocalDate weekAgo = today.minusDays(7);
        
        double totalSales = transactionDAO.getTotalSales(weekAgo, today);
        int transactionCount = transactionDAO.getTransactionCount(weekAgo, today);
        
        System.out.println("\nSales Report (Last 7 Days):");
        System.out.println("┌─────────────────────────────────────┐");
        System.out.printf("│ Period: %s to %s%n", weekAgo, today);
        System.out.printf("│ Transactions: %d%n", transactionCount);
        System.out.printf("│ Total Sales: Rp%,.0f%n", totalSales);
        if (transactionCount > 0) {
            System.out.printf("│ Average per Transaction: Rp%,.0f%n", totalSales / transactionCount);
        }
        System.out.println("└─────────────────────────────────────┘");
        
        // Close database connection
        DatabaseConnection.getInstance().closeConnection();
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║               WEEK 11 COMPLETED SUCCESSFULLY              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}