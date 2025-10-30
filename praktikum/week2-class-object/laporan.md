# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Tuliskan judul topik, misalnya "Class dan Object"]

## Identitas
- Nama  : [Wisnu wibowo saputro]
- NIM   : [240320565]
- Kelas : [3DSRA]

---

## Tujuan
Mahasiswa mampu menjelaskan konsep class, object, atribut, dan method dalam OOP.
Mahasiswa mampu menerapkan access modifier dan enkapsulasi dalam pembuatan class.
Mahasiswa mampu mengimplementasikan class Produk pertanian dengan atribut dan method yang sesuai.
Mahasiswa mampu mendemonstrasikan instansiasi object serta menampilkan data produk pertanian di console.
Mahasiswa mampu menyusun laporan praktikum dengan bukti kode, hasil eksekusi, dan analisis sederhana.

---

## Dasar Teori

1. Class dan Objek: Class adalah blueprint atau cetak biru dari sebuah objek, sedangkan objek merupakan instansiasi dari class yang berisi atribut (data) dan method (perilaku).  
2. Enkapsulasi dan Access Modifier: Enkapsulasi dilakukan dengan menyembunyikan data menggunakan access modifier seperti public, private, dan protected, kemudian menyediakan akses melalui getter dan setter.
3. Representasi Produk Pertanian: Dalam konteks Agri-POS, produk pertanian seperti benih, pupuk, dan alat pertanian dapat direpresentasikan sebagai objek dengan atribut nama, harga, dan stok.
4. Manajemen Objek Terstruktur: Dengan menggunakan class, setiap produk dapat dibuat, dikelola, dan dimanipulasi secara lebih terstruktur, memudahkan pengelolaan data dalam sistem.

---

## Langkah Praktikum

1. Membuat Class Produk
   a. Buat file Produk.java pada package model.
   b. Tambahkan atribut: kode, nama, harga, dan stok.
   c. Gunakan enkapsulasi dengan menjadikan atribut bersifat private dan membuat getter serta setter untuk masing-masing atribut.
2. Membuat Class CreditBy
   a. Buat file CreditBy.java pada package util.
   b. Isi class dengan method statis untuk menampilkan identitas mahasiswa di akhir output: credit by: <240320565> - <Wisnu Wibowo Saputro>.
3. Membuat Objek Produk dan Menampilkan Credit
   a. Buat file MainProduk.java.
   b. Instansiasi minimal tiga objek produk, misalnya "Benih Padi", "Pupuk Urea", dan satu produk alat pertanian.
   c. Tampilkan informasi produk melalui method getter.
   d. Panggil CreditBy.print("<240320565>", "<Wisnu Wibowo Saputro>") di akhir main untuk menampilkan identitas.
4. Commit dan Push

Commit dengan pesan: week2-class-object.

---

## Kode Program
(Tuliskan kode utama yang dibuat, contoh:  

```java
// Contoh
Produk p1 = new Produk("BNH-001", "Benih Padi", 25000, 100);
System.out.println(p1.getNama());
```
)
---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/hasil.png)
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan.  
- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.  
- Kendala yang dihadapi dan cara mengatasinya.  
)
---

## Kesimpulan
(Tuliskan kesimpulan dari praktikum minggu ini.  
Contoh: *Dengan menggunakan class dan object, program menjadi lebih terstruktur dan mudah dikembangkan.*)

---

## Quiz
(1. [Tuliskan kembali pertanyaan 1 dari panduan]  
   **Jawaban:** …  

2. [Tuliskan kembali pertanyaan 2 dari panduan]  
   **Jawaban:** …  

3. [Tuliskan kembali pertanyaan 3 dari panduan]  
   **Jawaban:** …  )
