LAPORAN PRAKTIKUM MINGGU 11
Topik: DAO Pattern & Database Integration dengan H2 Database

Identitas
Nama : Wisnu

NIM : 240320565

Kelas : OOP 2025

Tujuan
Mahasiswa memahami dan mampu mengimplementasikan:

DAO (Data Access Object) Pattern untuk memisahkan layer data access dari business logic

JDBC (Java Database Connectivity) untuk koneksi ke database

CRUD Operations dengan PreparedStatement untuk mencegah SQL injection

Transaction Management dengan commit/rollback mechanism

Database Integration menggunakan H2 Database (embedded, no installation)

Dasar Teori
1. DAO (Data Access Object) Pattern
Pola desain yang memisahkan akses data dari business logic

Interface mendefinisikan operasi CRUD (Create, Read, Update, Delete)

Implementation mengimplementasikan interface dengan teknologi spesifik (JDBC, JPA, dll)

Keuntungan: loose coupling, mudah ganti database technology, testability

2. JDBC (Java Database Connectivity)
API Java untuk menghubungkan aplikasi dengan database

Komponen utama:

DriverManager: mengelola koneksi database

Connection: representasi koneksi ke database

PreparedStatement: statement SQL dengan parameter binding

ResultSet: hasil query dari database

3. Singleton Pattern
Memastikan hanya ada satu instance dari suatu class

Digunakan untuk database connection pooling

Mencegah multiple connections yang tidak perlu

4. H2 Database
Database embeddable dalam Java (in-memory atau file-based)

Tidak perlu install database server

100% JDBC compliant

Cocok untuk development dan testing

5. Transaction Management
ACID Properties:

Atomicity: semua operasi sukses atau semua gagal

Consistency: data tetap valid setelah transaction

Isolation: transaction tidak saling mengganggu

Durability: perubahan permanen setelah commit

Langkah Praktikum
1. Setup Project Structure
text
praktikum/week11-dao-database/
├── src/main/java/com/upb/agripos/
│   ├── config/     (DatabaseConnection.java)
│   ├── dao/        (ProductDAO, JdbcProductDAO, dll)
│   ├── model/      (Product, Transaction, TransactionDetail)
│   └── MainDAOTest.java
├── lib/            (h2-2.2.224.jar)
├── database/       (auto-created H2 database files)
└── screenshots/
2. Download H2 Database JAR
powershell
mkdir lib
Invoke-WebRequest -Uri "https://repo1.maven.org/maven2/com/h2database/h2/2.2.224/h2-2.2.224.jar" -OutFile "lib\h2-2.2.224.jar"
3. Implementasi Model Classes
Product.java: Model untuk produk dengan atribut code, name, category, price, stock

Transaction.java: Model untuk transaksi dengan details

TransactionDetail.java: Model untuk detail transaksi

4. Implementasi DAO Pattern
4.1 Interface DAO
ProductDAO.java: Interface dengan operasi CRUD untuk produk

TransactionDAO.java: Interface untuk manajemen transaksi

4.2 Implementation DAO
JdbcProductDAO.java: Implementasi dengan JDBC dan PreparedStatement

JdbcTransactionDAO.java: Implementasi dengan transaction management

5. Database Connection (Singleton)
DatabaseConnection.java: Singleton class untuk koneksi database H2

Auto-create tables dan sample data saat pertama kali run

6. Main Program
MainDAOTest.java: Program utama untuk demonstrasi semua fitur

7. Kompilasi dan Run
powershell
# Kompilasi semua file
javac -d bin src\main\java\com\upb\agripos\model\*.java
javac -d bin -cp bin src\main\java\com\upb\agripos\config\*.java
javac -d bin -cp bin src\main\java\com\upb\agripos\dao\*.java
javac -d bin -cp bin src\main\java\com\upb\agripos\MainDAOTest.java

# Jalankan dengan H2 JAR
java -cp "bin;lib/h2-2.2.224.jar" com.upb.agripos.MainDAOTest
8. Commit Message
text
week11-dao-database: implement DAO pattern with H2 database
week11-dao-database: add CRUD operations with PreparedStatement
week11-dao-database: implement transaction management
week11-dao-database: add reporting features
Kode Program
1. DatabaseConnection.java (Singleton Pattern)
java
package com.upb.agripos.config;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    private static final String DRIVER = "org.h2.Driver";
    private static final String CONNECTION_URL = "jdbc:h2:database/agripos;DB_CLOSE_DELAY=-1";
    
    private DatabaseConnection() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(CONNECTION_URL, "sa", "");
            initializeDatabase();
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
        }
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
    
    // ... methods for database initialization
}
2. ProductDAO.java (Interface)
java
package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDAO {
    boolean insert(Product product);
    Product findByCode(String code);
    List<Product> findAll();
    boolean update(Product product);
    boolean delete(String code);
    boolean reduceStock(String code, int quantity);
    List<Product> getLowStockProducts(int threshold);
}
3. JdbcProductDAO.java (Implementation)
java
package com.upb.agripos.dao;

public class JdbcProductDAO implements ProductDAO {
    private final Connection connection;
    
    public JdbcProductDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public boolean insert(Product product) {
        String sql = "INSERT INTO products (code, name, category, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getCode());
            ps.setString(2, product.getName());
            ps.setString(3, product.getCategory());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Insert error: " + e.getMessage());
            return false;
        }
    }
    
    // ... other CRUD operations
}
4. MainDAOTest.java (Demo Program)
java
package com.upb.agripos;

public class MainDAOTest {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║  WEEK 11: DAO & H2 Database - NO INSTALLATION NEEDED!    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        // Test database connection
        DatabaseConnection.testConnection();
        
        // Create DAOs
        ProductDAO productDAO = new JdbcProductDAO();
        TransactionDAO transactionDAO = new JdbcTransactionDAO();
        
        // Demonstrate CRUD operations
        System.out.println("\n=== PRODUCT CRUD OPERATIONS ===");
        
        // 1. Find all products
        List<Product> allProducts = productDAO.findAll();
        System.out.println("Total products: " + allProducts.size());
        
        // 2. Insert new product
        Product newProduct = new Product("P11", "Tepung Terigu", "Bahan Pokok", 18000, 50);
        boolean inserted = productDAO.insert(newProduct);
        System.out.println("Insert success: " + inserted);
        
        // 3. Create transaction
        Transaction transaction = new Transaction("TX004", 275000, "CASH");
        transaction.addDetail(new TransactionDetail("TX004", "P01", "Beras Premium", 2, 50000));
        boolean txSuccess = transactionDAO.insert(transaction);
        System.out.println("Transaction saved: " + txSuccess);
        
        // 4. Generate sales report
        double totalSales = transactionDAO.getTotalSales(
            LocalDate.now().minusDays(7), 
            LocalDate.now()
        );
        System.out.printf("Total sales (7 days): Rp%,.0f%n", totalSales);
    }
}

Analisis
1. Cara Kerja Kode
Singleton Pattern pada DatabaseConnection memastikan hanya satu koneksi database

DAO Pattern memisahkan logic: Interface mendefinisikan kontrak, Implementation handle JDBC details

PreparedStatement mencegah SQL injection dengan parameter binding

Transaction Management menggunakan connection.setAutoCommit(false), commit(), dan rollback()

H2 Database auto-creates tables dan insert sample data saat pertama run

2. Perbedaan dengan Minggu Sebelumnya
Week 10 (Pattern & Testing)	Week 11 (DAO & Database)
Mock DAO dengan data in-memory	Real DAO dengan database
Strategy Pattern untuk payment	DAO Pattern untuk data access
Unit testing dengan JUnit	Integration testing dengan database
Singleton untuk connection mock	Singleton untuk real database connection
3. Kendala dan Solusi
Kendala 1: Java DB (Derby) tidak tersedia di JDK
Masalah: ClassNotFoundException: org.apache.derby.jdbc.EmbeddedDriver

Solusi: Ganti ke H2 Database yang lebih modern dan mudah setup

Kendala 2: Kompilasi urutan dependencies
Masalah: Error "package does not exist" saat kompilasi

Solusi: Kompilasi dengan urutan: Model → Config → DAO Interface → DAO Implementation → Main

Kendala 3: PowerShell tidak support **\*.java
Masalah: javac -d bin src\main\java\com\upb\agripos\**\*.java error

Solusi: Kompilasi per folder dengan urutan dependencies

Kendala 4: Transaction rollback tidak bekerja
Masalah: Error di detail transaksi, tapi produk tetap ter-update

Solusi: Implementasi proper transaction dengan setAutoCommit(false) dan rollback()

Kesimpulan
DAO Pattern berhasil diimplementasikan dengan baik, memisahkan data access layer dari business logic

H2 Database merupakan solusi tepat untuk development dan testing: embedded, no installation, JDBC compliant

PreparedStatement efektif mencegah SQL injection dan meningkatkan performance

Transaction Management dengan commit/rollback memastikan data consistency

Singleton Pattern optimal untuk database connection management

Implementasi Week 11 100% compatible dengan Week 7-10, siap untuk integrasi GUI di Week 12-13

Nilai Tambah:

✅ No external database installation needed

✅ Auto-create database and sample data

✅ Full CRUD operations with transaction safety

✅ Reporting capabilities

✅ Ready for production (bisa ganti ke PostgreSQL/MySQL dengan minimal changes)

Quiz Week 11
1. Jelaskan keuntungan menggunakan DAO Pattern dalam pengembangan aplikasi!
Jawaban:
DAO Pattern memberikan beberapa keuntungan:

Separation of Concerns: Memisahkan data access logic dari business logic

Abstraction: Menyembunyikan kompleksitas akses database dari layer atas

Testability: Mudah melakukan unit testing dengan mock DAO

Flexibility: Mudah mengganti teknologi database tanpa mengubah business logic

Reusability: DAO bisa di-reuse oleh multiple services/controllers

Maintainability: Perubahan di data access layer tidak mempengaruhi business layer

2. Mengapa PreparedStatement lebih direkomendasikan daripada Statement biasa?
Jawaban:
PreparedStatement lebih direkomendasikan karena:

SQL Injection Prevention: Menggunakan parameter binding (?) yang mencegah SQL injection

Performance: Pre-compiled statement, lebih cepat untuk eksekusi berulang

Type Safety: Validasi tipe data parameter saat compile time

Readability: Kode lebih mudah dibaca dan dipelihara

Security: Memisahkan SQL logic dari data values

Contoh perbandingan:

java
// RISK: SQL Injection Vulnerability
String sql = "SELECT * FROM users WHERE username = '" + username + "'";
Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery(sql);

// SAFE: PreparedStatement prevents SQL injection
String sql = "SELECT * FROM users WHERE username = ?";
PreparedStatement ps = connection.prepareStatement(sql);
ps.setString(1, username);  // Parameter binding
ResultSet rs = ps.executeQuery();
3. Apa perbedaan antara H2 Database in-memory dan file-based mode?
Jawaban:

Aspect	In-Memory Mode	File-Based Mode
Storage	Data disimpan di RAM	Data disimpan di file .db
Persistence	❌ Data hilang saat aplikasi stop	✅ Data tetap tersimpan
Performance	⚡ Sangat cepat (RAM access)	🏎️ Cepat (file I/O)
Use Case	Testing, Development	Production, Data persistence
Connection URL	jdbc:h2:mem:dbname	jdbc:h2:file:/path/dbname
Sharing	❌ Tidak bisa di-share antar aplikasi	✅ Bisa diakses multiple apps
Contoh implementasi:

java
// In-Memory (testing)
String url = "jdbc:h2:mem:agripos;DB_CLOSE_DELAY=-1";

// File-Based (production)
String url = "jdbc:h2:database/agripos;DB_CLOSE_DELAY=-1";
4. Jelaskan pentingnya transaction management dalam operasi database!
Jawaban:
Transaction management penting untuk menjaga ACID properties:

Atomicity: Operasi harus "all or nothing"

java
try {
    connection.setAutoCommit(false);
    // Multiple operations
    updateProductStock();
    insertTransaction();
    insertTransactionDetails();
    connection.commit();  // All succeed
} catch (Exception e) {
    connection.rollback();  // All failed
}
Consistency: Data harus valid sebelum dan sesudah transaction

Isolation: Transaction tidak saling mengganggu

Durability: Perubahan permanen setelah commit

Tanpa transaction: Jika error terjadi di tengah operasi, data bisa inconsistent
Dengan transaction: Rollback otomatis menjaga data consistency

5. Bagaimana Singleton Pattern meningkatkan performance aplikasi database?
Jawaban:
Singleton Pattern meningkatkan performance melalui:

Connection Pooling: Satu koneksi digunakan berulang, bukan buka/tutup setiap kali

Resource Management: Mencegah memory leak dari multiple connections

Thread Safety: Synchronized access ke koneksi database

Performance: Reduce overhead buka/tutup koneksi

Implementasi:

java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() {
        // Private constructor
    }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                    instance.initializeConnection();
                }
            }
        }
        return instance;
    }
    
    // Lazy initialization: connection dibuat saat pertama kali digunakan
    private void initializeConnection() {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(url, user, pass);
        }
    }
}