package com.upb.agripos.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    // Java DB (Apache Derby) - Built into JDK!
    private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String DB_NAME = "agriposDB";
    private static final String DB_PATH = "database/" + DB_NAME;
    private static final String CONNECTION_URL = "jdbc:derby:" + DB_PATH + ";create=true";
    
    private DatabaseConnection() {
        initializeDatabase();
    }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try {
            // 1. Load Derby Driver
            Class.forName(DRIVER);
            System.out.println("✓ Java DB (Derby) Driver loaded");
            
            // 2. Create connection
            connection = DriverManager.getConnection(CONNECTION_URL);
            System.out.println("✓ Connected to Java DB (Derby)");
            System.out.println("  Database: " + DB_PATH);
            
            // 3. Create tables
            createTables();
            
            // 4. Insert sample data
            insertSampleData();
            
            System.out.println("✓ Database initialized successfully!");
            
        } catch (ClassNotFoundException e) {
            System.err.println("✗ Java DB (Derby) driver not found!");
            System.err.println("  Make sure you're using JDK 8 or later");
        } catch (SQLException e) {
            System.err.println("✗ Database error: " + e.getMessage());
        }
    }
    
    private void createTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            
            // Drop tables if exist
            try {
                stmt.execute("DROP TABLE transaction_details");
                stmt.execute("DROP TABLE transactions");
                stmt.execute("DROP TABLE products");
            } catch (SQLException e) {
                // Tables don't exist yet, that's OK
            }
            
            // Create products table
            String createProducts = """
                CREATE TABLE products (
                    code VARCHAR(10) PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    category VARCHAR(50),
                    price DECIMAL(10,2) NOT NULL,
                    stock INT NOT NULL
                )
                """;
            stmt.execute(createProducts);
            
            // Create transactions table
            String createTransactions = """
                CREATE TABLE transactions (
                    id VARCHAR(20) PRIMARY KEY,
                    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                    total_amount DECIMAL(10,2) NOT NULL,
                    payment_method VARCHAR(20) NOT NULL,
                    payment_details VARCHAR(500),
                    cashier_id VARCHAR(50)
                )
                """;
            stmt.execute(createTransactions);
            
            // Create transaction_details table
            String createTransactionDetails = """
                CREATE TABLE transaction_details (
                    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    transaction_id VARCHAR(20),
                    product_code VARCHAR(10),
                    quantity INT NOT NULL,
                    unit_price DECIMAL(10,2) NOT NULL,
                    subtotal DECIMAL(10,2) NOT NULL
                )
                """;
            stmt.execute(createTransactionDetails);
        }
    }
    
    private void insertSampleData() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            
            // Insert sample products
            String insertProducts = """
                INSERT INTO products (code, name, category, price, stock) VALUES
                ('P01', 'Beras Premium', 'Bahan Pokok', 50000.00, 100),
                ('P02', 'Pupuk Organik', 'Pertanian', 30000.00, 50),
                ('P03', 'Bibit Jagung', 'Benih', 25000.00, 200),
                ('P04', 'Pestisida', 'Pertanian', 75000.00, 30),
                ('P05', 'Alat Semprot', 'Alat Tani', 150000.00, 10),
                ('P06', 'Gula Pasir', 'Bahan Pokok', 15000.00, 150),
                ('P07', 'Minyak Goreng', 'Bahan Pokok', 25000.00, 80),
                ('P08', 'Benih Padi', 'Benih', 35000.00, 120),
                ('P09', 'Herbisida', 'Pertanian', 90000.00, 25),
                ('P10', 'Cangkul', 'Alat Tani', 80000.00, 15)
                """;
            stmt.executeUpdate(insertProducts);
            
            // Insert sample transactions
            String insertTransactions = """
                INSERT INTO transactions (id, total_amount, payment_method, payment_details, cashier_id) VALUES
                ('TX001', 175000.00, 'CASH', 'Cash payment - Change: 25000', 'CASHIER001'),
                ('TX002', 300000.00, 'E-WALLET', 'GoPay - 081234567890', 'CASHIER002'),
                ('TX003', 125000.00, 'CASH', 'Cash payment - Exact amount', 'CASHIER001')
                """;
            stmt.executeUpdate(insertTransactions);
            
            // Insert transaction details
            String insertDetails = """
                INSERT INTO transaction_details (transaction_id, product_code, quantity, unit_price, subtotal) VALUES
                ('TX001', 'P01', 2, 50000.00, 100000.00),
                ('TX001', 'P02', 1, 30000.00, 30000.00),
                ('TX001', 'P03', 2, 25000.00, 50000.00),
                ('TX002', 'P04', 1, 75000.00, 75000.00),
                ('TX002', 'P05', 1, 150000.00, 150000.00),
                ('TX002', 'P06', 5, 15000.00, 75000.00),
                ('TX003', 'P07', 3, 25000.00, 75000.00),
                ('TX003', 'P08', 1, 35000.00, 35000.00),
                ('TX003', 'P10', 1, 80000.00, 80000.00)
                """;
            stmt.executeUpdate(insertDetails);
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✓ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    public static void testConnection() {
        System.out.println("Testing Java DB (Derby) Connection...");
        DatabaseConnection db = DatabaseConnection.getInstance();
        if (db.getConnection() != null) {
            System.out.println("✓ Connection test PASSED");
        } else {
            System.out.println("✗ Connection test FAILED");
        }
    }
}