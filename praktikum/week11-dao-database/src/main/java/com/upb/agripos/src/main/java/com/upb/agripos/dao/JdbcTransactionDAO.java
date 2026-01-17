package com.upb.agripos.dao;

import com.upb.agripos.config.DatabaseConnection;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionDetail;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDAO implements TransactionDAO {
    private final Connection connection;
    
    public JdbcTransactionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public boolean insert(Transaction transaction) {
        try {
            connection.setAutoCommit(false);
            
            // 1. Insert transaction
            String transSql = "INSERT INTO transactions (id, total_amount, payment_method, payment_details, cashier_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(transSql)) {
                ps.setString(1, transaction.getId());
                ps.setDouble(2, transaction.getTotalAmount());
                ps.setString(3, transaction.getPaymentMethod());
                ps.setString(4, transaction.getPaymentDetails());
                ps.setString(5, transaction.getCashierId());
                ps.executeUpdate();
            }
            
            // 2. Insert transaction details
            String detailSql = "INSERT INTO transaction_details (transaction_id, product_code, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(detailSql)) {
                for (TransactionDetail detail : transaction.getDetails()) {
                    ps.setString(1, transaction.getId());
                    ps.setString(2, detail.getProductCode());
                    ps.setInt(3, detail.getQuantity());
                    ps.setDouble(4, detail.getUnitPrice());
                    ps.setDouble(5, detail.getSubtotal());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            
            // 3. Update product stocks
            String updateStockSql = "UPDATE products SET stock = stock - ? WHERE code = ?";
            try (PreparedStatement ps = connection.prepareStatement(updateStockSql)) {
                for (TransactionDetail detail : transaction.getDetails()) {
                    ps.setInt(1, detail.getQuantity());
                    ps.setString(2, detail.getProductCode());
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            
            connection.commit();
            return true;
            
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback error: " + ex.getMessage());
            }
            System.err.println("Transaction insert error: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("Auto-commit reset error: " + e.getMessage());
            }
        }
    }
    
    @Override
    public Transaction findById(String id) {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getString("id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setTotalAmount(rs.getDouble("total_amount"));
                transaction.setPaymentMethod(rs.getString("payment_method"));
                transaction.setPaymentDetails(rs.getString("payment_details"));
                transaction.setCashierId(rs.getString("cashier_id"));
                
                // Load details
                transaction.setDetails(getTransactionDetails(id));
                
                return transaction;
            }
        } catch (SQLException e) {
            System.err.println("Find transaction error: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getString("id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setTotalAmount(rs.getDouble("total_amount"));
                transaction.setPaymentMethod(rs.getString("payment_method"));
                transaction.setPaymentDetails(rs.getString("payment_details"));
                transaction.setCashierId(rs.getString("cashier_id"));
                
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Find all transactions error: " + e.getMessage());
        }
        return transactions;
    }
    
    @Override
    public List<Transaction> findByDate(LocalDate date) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE DATE(transaction_date) = ? ORDER BY transaction_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getString("id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setTotalAmount(rs.getDouble("total_amount"));
                transaction.setPaymentMethod(rs.getString("payment_method"));
                transaction.setPaymentDetails(rs.getString("payment_details"));
                transaction.setCashierId(rs.getString("cashier_id"));
                
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Find by date error: " + e.getMessage());
        }
        return transactions;
    }
    
    @Override
    public List<Transaction> findByCashier(String cashierId) {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE cashier_id = ? ORDER BY transaction_date DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, cashierId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getString("id"));
                transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
                transaction.setTotalAmount(rs.getDouble("total_amount"));
                transaction.setPaymentMethod(rs.getString("payment_method"));
                transaction.setPaymentDetails(rs.getString("payment_details"));
                transaction.setCashierId(rs.getString("cashier_id"));
                
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            System.err.println("Find by cashier error: " + e.getMessage());
        }
        return transactions;
    }
    
    @Override
    public double getTotalSales(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM transactions WHERE DATE(transaction_date) BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Get total sales error: " + e.getMessage());
        }
        return 0;
    }
    
    @Override
    public int getTransactionCount(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) FROM transactions WHERE DATE(transaction_date) BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Get transaction count error: " + e.getMessage());
        }
        return 0;
    }
    
    @Override
    public List<Object[]> getDailySalesReport(LocalDate startDate, LocalDate endDate) {
        List<Object[]> report = new ArrayList<>();
        String sql = "SELECT DATE(transaction_date) as sale_date, COUNT(*) as transaction_count, SUM(total_amount) as total_sales FROM transactions WHERE DATE(transaction_date) BETWEEN ? AND ? GROUP BY DATE(transaction_date) ORDER BY sale_date";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[3];
                row[0] = rs.getDate("sale_date");
                row[1] = rs.getInt("transaction_count");
                row[2] = rs.getDouble("total_sales");
                report.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Get daily sales report error: " + e.getMessage());
        }
        return report;
    }
    
    @Override
    public String generateTransactionId() {
        String sql = "SELECT 'TX' || LPAD(COALESCE(MAX(CAST(SUBSTRING(id FROM 3) AS INTEGER)), 0) + 1, 3, '0') FROM transactions";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            System.err.println("Generate transaction ID error: " + e.getMessage());
        }
        return "TX001";
    }
    
    private List<TransactionDetail> getTransactionDetails(String transactionId) {
        List<TransactionDetail> details = new ArrayList<>();
        String sql = "SELECT td.*, p.name as product_name FROM transaction_details td JOIN products p ON td.product_code = p.code WHERE td.transaction_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, transactionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TransactionDetail detail = new TransactionDetail();
                detail.setId(rs.getInt("id"));
                detail.setTransactionId(rs.getString("transaction_id"));
                detail.setProductCode(rs.getString("product_code"));
                detail.setProductName(rs.getString("product_name"));
                detail.setQuantity(rs.getInt("quantity"));
                detail.setUnitPrice(rs.getDouble("unit_price"));
                detail.setSubtotal(rs.getDouble("subtotal"));
                
                details.add(detail);
            }
        } catch (SQLException e) {
            System.err.println("Get transaction details error: " + e.getMessage());
        }
        return details;
    }
}