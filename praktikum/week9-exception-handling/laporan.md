# Week 9 - Exception Handling Agri-POS

## 📋 Informasi Praktikum
- **Nama** : Wisnu Wibowo S
- **NIM** : 24320565
- **Topik**: Exception Handling dan Custom Exception
- **Week**: 9
- **Mata Kuliah**: Pemrograman Berorientasi Objek
- **Integrasi**: Week 7 (Collections & Shopping Cart)

## 🎯 Tujuan Pembelajaran
1. Memahami perbedaan Error dan Exception
2. Mengimplementasikan try-catch-finally
3. Membuat custom exception
4. Mengintegrasikan exception handling ke aplikasi
5. Menerapkan Singleton design pattern

## 📁 Struktur File

```
week9-exception-handling/
├── src/main/java/com/upb/agripos/
│   ├── Product.java                       
│   ├── ShoppingCart.java                  
exception handling
│   ├── ProductService.java              
│   ├── InvalidQuantityException.java    
│   ├── ProductNotFoundException.java
│   ├── InsufficientStockException.java   
│   └── MainExceptionDemo.java            
├── screenshots/
│   └── hasil.png
├── laporan_week9.md
└── README.md (file ini)
```

## 🚀 Cara Menjalankan

### Menggunakan IDE (IntelliJ IDEA / Eclipse / NetBeans)

1. **Buat Project Baru** atau gunakan project Week 7
2. **Buat package**: `com.upb.agripos`
3. **Copy semua file .java** ke dalam package
4. **PENTING**: Ganti `[Nama Anda]` dan `[NIM Anda]` di `MainExceptionDemo.java`
5. **Run** file `MainExceptionDemo.java`

### Menggunakan Command Line

```bash
# Masuk ke direktori src
cd src

# Compile semua file
javac com/upb/agripos/*.java

# Jalankan program
java com.upb.agripos.MainExceptionDemo
```

## 📝 Daftar File & Fungsinya

### 1. **InvalidQuantityException.java**
Custom exception untuk validasi quantity:
- Dilempar saat quantity ≤ 0
- Digunakan di method `addProduct()` dan `updateQuantity()`

### 2. **ProductNotFoundException.java**
Custom exception untuk produk tidak ditemukan:
- Dilempar saat operasi pada produk yang tidak ada
- Digunakan di method `removeProduct()` dan `updateQuantity()`

### 3. **InsufficientStockException.java**
Custom exception untuk stok tidak cukup:
- Dilempar saat quantity melebihi stok
- Digunakan di method `addProduct()` dan `checkout()`

### 4. **Product.java**
Model produk dengan fitur baru:
- Atribut `stock` (baru di Week 9)
- Method `reduceStock()` untuk mengurangi stok
- Method `addStock()` untuk menambah stok
- Backward compatible dengan Week 7

### 5. **ShoppingCart.java**
Keranjang belanja dengan exception handling:
- Method baru: `addProduct()`, `removeProduct()`, `checkout()` dengan exception
- Method lama (Week 7) masih berfungsi: `tambahProduk()`, `hapusProduk()`
- Validasi lengkap pada setiap operasi

### 6. **ProductService.java**
Service layer dengan Singleton Pattern:
- Hanya satu instance di seluruh aplikasi
- Mengelola katalog produk
- Fitur search dan filter produk

### 7. **MainExceptionDemo.java**
Program utama dengan 7 skenario testing:
1. Testing exception handling
2. Operasi normal berbelanja
3. Try-catch-finally demo
4. Checkout berhasil
5. Checkout gagal (stok habis)
6. Backward compatibility Week 7
7. Multiple catch blocks
8. Bonus: Singleton pattern demo

## 📸 Screenshot yang Perlu Diambil

1. Test invalid quantity (negatif dan 0)
2. Test product not found
3. Test insufficient stock
4. Operasi normal (tambah produk)
5. Checkout berhasil
6. Stok setelah checkout
7. Hasil lengkap semua test

Simpan semua screenshot di folder `screenshots/`

## 📤 Cara Submit

```bash
# 1. Add semua file
git add .

# 2. Commit dengan message yang benar
git commit -m "week9-exception: Implement custom exceptions and exception handling"

# 3. Push ke repository
git push origin main
```

## 🧪 Expected Output

Program akan menampilkan:
1. Header dengan nama dan NIM
2. Katalog produk dengan stok
3. Testing 7 skenario exception handling
4. Isi keranjang belanja
5. Proses checkout dengan detail
6. Stok produk setelah transaksi
7. Demo Singleton pattern
8. Ringkasan pembelajaran

## ⚠️ Common Issues & Solutions

### Error: "package does not exist"
**Solusi**: Pastikan struktur package `com.upb.agripos` benar

### Error: "cannot find symbol"
**Solusi**: Compile semua file dulu sebelum run

### Output tidak keluar
**Solusi**: Pastikan menjalankan `MainExceptionDemo.java`, bukan file lain

### Exception tidak tertangkap
**Solusi**: Pastikan menggunakan try-catch dengan tipe exception yang benar

## 📚 Konsep yang Dipelajari

### 1. Custom Exception
- Membuat exception sendiri dengan extends `Exception`
- Memberikan pesan error yang informatif
- Exception hierarchy

### 2. Try-Catch-Finally
- Try: kode yang berisiko
- Catch: menangani exception
- Finally: cleanup (selalu dieksekusi)

### 3. Multiple Catch
- Menangani berbagai jenis exception
- Urutan catch dari spesifik ke general

### 4. Singleton Pattern
- Hanya satu instance
- Private constructor
- Static getInstance() method

### 5. Backward Compatibility
- Method lama masih berfungsi
- Wrapper method untuk handling exception

## 📖 Referensi

- [Oracle Java Exception Tutorial](https://docs.oracle.com/javase/tutorial/essential/exceptions/)
- [Java Design Patterns](https://www.baeldung.com/java-singleton)
- Modul Praktikum Week 9

## 💡 Tips

1. **Selalu validate input** sebelum proses data
2. **Gunakan exception yang spesifik** daripada generic
3. **Finally block** untuk cleanup resources
4. **Error message** harus informatif dan membantu user
5. **Test semua edge cases** (negatif, 0, melebihi stok, dll)

## 🎓 Penilaian

Kriteria penilaian:
- ✅ Custom exception minimal 2 (dibuat 3) - 20%
- ✅ Try-catch-finally implementation - 20%
- ✅ Integrasi di shopping cart - 25%
- ✅ Backward compatibility Week 7 - 10%
- ✅ Screenshot hasil eksekusi - 10%
- ✅ Laporan lengkap - 10%
- ✅ Commit & push benar - 5%



---

