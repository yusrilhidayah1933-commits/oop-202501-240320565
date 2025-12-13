# Laporan Praktikum Minggu 6
Topik: Desain Arsitektur Sistem dengan UML dan Prinsip SOLID

## Identitas
- Nama  : Wisnu Wibowo S
- NIM   : 240320565
- Kelas : 3DSRA

---

1. Deskripsi Sistem
Agri-POS adalah sistem Point of Sale untuk toko produk pertanian yang mengelola penjualan benih, pupuk, alat, dan obat pertanian. Sistem mendukung pembayaran tunai dan e-wallet, manajemen produk, pencetakan struk, dan laporan penjualan.
Aktor Sistem:

Customer - Pelanggan yang membeli produk
Kasir - Menangani transaksi dan pembayaran
Admin - Mengelola produk dan melihat laporan
Payment Gateway - Sistem eksternal untuk e-wallet


2. Class Diagram
Class Diagram menunjukkan struktur sistem dengan 4 layer utama:
Komponen Utama:
Domain Layer (Entities)

Product - Data produk (kode, nama, kategori, harga, stok)
Transaction - Data transaksi penjualan
TransactionItem - Item dalam transaksi
Customer - Data pelanggan
User - Data kasir/admin
Receipt - Struk pembayaran

Service Layer (Business Logic)

ProductService - Mengelola produk
TransactionService - Mengelola transaksi
PaymentService - Memproses pembayaran
ReportService - Membuat laporan
AuthenticationService - Login dan akses

Payment Strategy

PaymentMethod (interface) - Kontrak pembayaran
CashPayment - Pembayaran tunai
EWalletPayment - Pembayaran e-wallet

Repository Pattern

ProductRepository (interface) - Kontrak akses data produk
TransactionRepository (interface) - Kontrak akses data transaksi
JdbcProductRepository - Implementasi dengan JDBC
JdbcTransactionRepository - Implementasi dengan JDBC


3. Penerapan Prinsip SOLID
✓ Single Responsibility Principle (SRP)
Penerapan:
Setiap class memiliki satu tanggung jawab:

ProductService → hanya kelola produk
PaymentService → hanya proses pembayaran
ReportService → hanya buat laporan

Contoh: ProductService hanya menangani CRUD produk, tidak mencampur dengan logic pembayaran atau laporan.

✓ Open/Closed Principle (OCP)
Penerapan:
Sistem terbuka untuk ekstensi, tertutup untuk modifikasi melalui Strategy Pattern pada payment.
Contoh:
javainterface PaymentMethod {
    PaymentResult pay(double amount);
}

class CashPayment implements PaymentMethod { }
class EWalletPayment implements PaymentMethod { }
// Tambah payment baru tanpa ubah PaymentService
class BankTransferPayment implements PaymentMethod { }
Menambah metode pembayaran baru (transfer bank, QRIS) tidak perlu mengubah PaymentService.

✓ Liskov Substitution Principle (LSP)
Penerapan:
Semua implementasi PaymentMethod dapat menggantikan interface tanpa mengubah behavior.
Contoh:
javaPaymentMethod payment = new CashPayment(); // atau EWalletPayment
paymentService.setPaymentMethod(payment);  // Keduanya valid
PaymentService tidak peduli implementasi spesifik, semua implementasi bekerja sama.

✓ Interface Segregation Principle (ISP)
Penerapan:
Interface dipecah sesuai kebutuhan spesifik, tidak ada method yang tidak digunakan.
Contoh:

PaymentMethod → hanya method untuk pembayaran
ProductRepository → hanya method untuk produk
TransactionRepository → hanya method untuk transaksi

CashPayment tidak perlu implementasi PaymentGateway karena tidak butuh koneksi eksternal.

✓ Dependency Inversion Principle (DIP)
Penerapan:
Service bergantung pada interface (abstraksi), bukan implementasi konkret.
Contoh:
javaclass ProductService {
    private ProductRepository repository; // Interface
    
    public ProductService(ProductRepository repository) {
        this.repository = repository; // Dependency Injection
    }
}
Manfaat:

Mudah diganti dari JDBC ke JPA
Mudah di-mock untuk testing
Service tidak terikat pada detail implementasi


4. Tabel Traceability
Functional RequirementClass/InterfaceServiceRepositoryManajemen ProdukProductProductServiceProductRepositoryTransaksi PenjualanTransaction, TransactionItemTransactionServiceTransactionRepositoryPembayaran Tunai & E-walletPaymentMethod, CashPayment, EWalletPaymentPaymentService-Cetak StrukReceipt, ReceiptPrinter--Laporan PenjualanSalesReportReportServiceTransactionRepositoryLogin & Hak AksesUser, UserRoleAuthenticationServiceUserRepository

5. Relasi Antar Class
Composition (◆)

Transaction *-- TransactionItem (item tidak bisa eksis tanpa transaksi)

Association (→)

Transaction → User (transaksi diproses oleh kasir)
Transaction → Customer (transaksi untuk pelanggan)
TransactionItem → Product (item merujuk produk)

Realization (◁--)

CashPayment, EWalletPayment implements PaymentMethod
JdbcProductRepository implements ProductRepository

Multiplicity

Transaction memiliki 1..* TransactionItem (minimal 1 item)
Transaction untuk 0..1 Customer (opsional)


6. Kesimpulan
Keunggulan Desain:

Modular - Setiap komponen terpisah dan fokus pada tanggung jawab spesifik
Extensible - Mudah tambah fitur baru tanpa ubah kode lama (OCP)
Testable - Dependency injection memudahkan unit testing
Maintainable - Mudah dipahami dan dimodifikasi
Flexible - Dapat ganti database atau payment method tanpa ubah business logic

Potensi Pengembangan:

Tambah fitur diskon/promosi dengan Strategy Pattern
Loyalty program untuk customer
Refund dan return barang
Multi-warehouse inventory
Offline mode dengan sinkronisasi
Dashboard analytics untuk admin
Integrasi payment gateway multiple (Midtrans, Xendit)

Refleksi:
Desain ini menerapkan kelima prinsip SOLID secara konsisten, menghasilkan sistem yang robust dan siap dikembangkan. Penggunaan pattern seperti Strategy, Repository, dan Dependency Injection membuat kode lebih fleksibel dan mudah di-maintain. Sistem ini siap untuk skalabilitas dan penambahan fitur baru sesuai kebutuhan bisnis toko pertanian.

Quiz (Jawaban)
1. Jelaskan perbedaan aggregation dan composition serta berikan contoh penerapannya pada desain Anda.
Jawaban:

Composition (◆) - Relasi "part-of" yang kuat, bagian tidak bisa eksis tanpa whole. Jika whole dihapus, part ikut terhapus.

Contoh: Transaction *-- TransactionItem - Jika transaksi dihapus, item transaksi juga dihapus.


Aggregation (◇) - Relasi "has-a" yang lemah, bagian bisa eksis sendiri tanpa whole.

Contoh: Transaction ◇-- Customer - Jika transaksi dihapus, customer tetap ada.




2. Bagaimana prinsip Open/Closed dapat memastikan sistem mudah dikembangkan?
Jawaban:
OCP membuat sistem "terbuka untuk ekstensi, tertutup untuk modifikasi". Dalam Agri-POS:

Interface PaymentMethod memungkinkan tambah metode pembayaran baru (Transfer Bank, QRIS) dengan membuat implementasi baru
Tidak perlu ubah PaymentService yang sudah ada
Mengurangi risiko bug pada kode existing
Memudahkan penambahan fitur tanpa regression testing penuh


3. Mengapa Dependency Inversion Principle (DIP) meningkatkan testability? Berikan contoh penerapannya.
Jawaban:
DIP membuat high-level module bergantung pada abstraksi (interface), bukan implementasi konkret. Ini meningkatkan testability karena:
Contoh:
java// Tanpa DIP - sulit di-test
class ProductService {
    private JdbcProductRepository repository = new JdbcProductRepository();
    // Butuh database real untuk testing
}

// Dengan DIP - mudah di-test
class ProductService {
    private ProductRepository repository; // Interface
    
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
}

