# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: Collections dan Implementasi Keranjang Belanja

## Identitas
- Nama  : Wisnu Wibowo Saputro
- NIM   : 240320565
- Kelas : 3DSRA

---

## Tujuan Pembelajaran
Setelah menyelesaikan praktikum ini, mahasiswa mampu:
1. Menjelaskan konsep collection dalam Java (List, Map, Set)
2. Menggunakan ArrayList untuk menyimpan dan mengelola objek
3. Mengimplementasikan Map atau Set sesuai kebutuhan pengelolaan data
4. Melakukan operasi dasar pada collection: tambah, hapus, dan hitung total
5. Menganalisis efisiensi penggunaan collection dalam konteks sistem Agri-POS-

## Dasar Teori
1. Collections Framework
Java Collections Framework adalah arsitektur untuk merepresentasikan dan memanipulasi koleksi objek. Framework ini menyediakan interface dan implementasi untuk berbagai struktur data.
Tiga Struktur Utama:
A. List Interface
   Implementasi: ArrayList, LinkedList, Vector
   Karakteristik:
   1. Terurut (ordered) berdasarkan urutan penambahan
   2. Dapat menyimpan elemen duplikat
   3. Akses elemen menggunakan index (0, 1, 2, ...)
   4. Cocok untuk: daftar item sederhana, history, queue
B. Map Interface
   Implementasi: HashMap, TreeMap, LinkedHashMap
   Karakteristik:
   1. Menyimpan pasangan key-value
   2. Key harus unik, value boleh duplikat
   3. Akses cepat berdasarkan key (O(1) untuk HashMap)
   4. Cocok untuk: inventory dengan quantity, cache, lookup table
C. Set Interface
   Implementasi: HashSet, TreeSet, LinkedHashSet
   Karakteristik:
   1. Tidak menerima elemen duplikat
   2. Tidak mempertahankan urutan (kecuali LinkedHashSet/TreeSet)
   3. Cocok untuk: unique items, membership testing

## Studi Kasus
Dalam sistem Point of Sale (POS) pertanian, keranjang belanja memiliki kebutuhan:
1. Menambahkan produk ke keranjang
2. Menghapus produk dari keranjang
3. Menampilkan isi keranjang
4. Menghitung total nilai transaksi
5. Mengelola quantity untuk produk yang sama
Dua pendekatan implementasi:
1. ArrayList: Simpel, setiap penambahan produk yang sama = item baru
2. HashMap: Efisien, produk yang sama digabung dengan quantity

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Langkah-langkah yang dilakukan (setup, coding, run).  
2. File/kode yang dibuat.  
3. Commit message yang digunakan.)

---

## Kode Program
1. Class Product
javapackage com.upb.agripos;

public class Product {
    private final String code;
    private final String name;
    private final double price;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
Penjelasan:

Menggunakan final untuk immutability (data tidak bisa diubah)
Constructor untuk inisialisasi data produk
Getter methods untuk akses data

2. Shopping Cart dengan ArrayList
javapackage com.upb.agripos;

import java.util.ArrayList;

public class ShoppingCart {
    private final ArrayList<Product> items = new ArrayList<>();

    public void addProduct(Product p) { 
        items.add(p); 
    }
    
    public void removeProduct(Product p) { 
        items.remove(p); 
    }

    public double getTotal() {
        double sum = 0;
        for (Product p : items) {
            sum += p.getPrice();
        }
        return sum;
    }

    public void printCart() {
        System.out.println("Isi Keranjang:");
        for (Product p : items) {
            System.out.println("- " + p.getCode() + " " + 
                              p.getName() + " = " + p.getPrice());
        }
        System.out.println("Total: " + getTotal());
    }
}
Kelebihan:

Implementasi sangat sederhana
Mudah dipahami untuk pemula
Mempertahankan urutan penambahan

Kekurangan:

Produk sama dihitung sebagai item terpisah
Tidak ada konsep quantity
Kurang efisien untuk produk duplikat

3. Shopping Cart dengan HashMap
javapackage com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartMap {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p) { 
        items.put(p, items.getOrDefault(p, 0) + 1); 
    }

    public void removeProduct(Product p) {
        if (!items.containsKey(p)) return;
        int qty = items.get(p);
        if (qty > 1) items.put(p, qty - 1);
        else items.remove(p);
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void printCart() {
        System.out.println("Isi Keranjang (Map):");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            System.out.println("- " + e.getKey().getCode() + " " + 
                              e.getKey().getName() + " x" + e.getValue());
        }
        System.out.println("Total: " + getTotal());
    }
}
Kelebihan:
1. Menangani quantity secara otomatis
2. Lebih efisien untuk produk duplikat
3. Representasi data lebih realistis untuk POS
4. Akses produk lebih cepat O(1)

Kekurangan:
1. Sedikit lebih kompleks untuk dipahami
2. Product harus implement equals() dan hashCode() untuk bekerja optimal



## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/hasil.png)
)
---

## Analisis
1. Analisis ArrayList vs HashMap
Aspek       	ArrayList	HashMap
Kompleksitas	Sederhana	Lebih kompleks
Quantity	      Manual	Otomatis
Duplikasi	   Boleh duplikat	Key unik
Akses	Index    O(n)	Key O(1)
Use Case	Cart  sederhana	POS profesional


2. Operasi Collection yang Digunakan
ArrayList:
add(E e) - Menambah elemen ke akhir list
remove(Object o) - Menghapus elemen pertama yang cocok
get(int index) - Mengambil elemen di index tertentu
size() - Mendapat jumlah elemen

HashMap:
put(K key, V value) - Menyimpan pasangan key-value
get(Object key) - Mengambil value berdasarkan key
getOrDefault(K key, V defaultValue) - Get dengan default jika tidak ada
containsKey(Object key) - Cek keberadaan key
entrySet() - Mendapat set semua entry untuk iterasi

3. Efisiensi dalam Konteks Agri-POS
Skenario 1: Customer membeli 10 karung Beras
Dengan ArrayList:
[Beras, Beras, Beras, Beras, Beras, ...]  // 10 objects
Memory: 10 × Product object
Dengan HashMap:
{Beras: 10}  // 1 entry
Memory: 1 × Product object + 1 × Integer
Kesimpulan: HashMap lebih efisien untuk produk berulang.
Skenario 2: Cari apakah Pupuk ada di cart
ArrayList: O(n) - harus cek satu-satu
HashMap: O(1) - langsung lookup by key
Kesimpulan: HashMap lebih cepat untuk pencarian.
---

## Kesimpulan
Dari praktikum Week 7 ini dapat disimpulkan:

1. Collections Framework menyediakan struktur data yang powerful untuk mengelola objek dalam Java dengan interface List, Map, dan Set yang masing-masing memiliki karakteristik dan use case berbeda.
2. ArrayList cocok untuk implementasi keranjang belanja sederhana karena kemudahan penggunaan, mempertahankan urutan, dan cukup untuk kebutuhan dasar tanpa quantity management.
3. HashMap lebih efisien untuk keranjang belanja profesional karena dapat menangani quantity otomatis, mencegah duplikasi key, dan memberikan akses O(1) yang sangat cepat.
4. Pemilihan struktur data yang tepat bergantung pada kebutuhan spesifik aplikasi:
   1. List untuk urutan dan duplikat
   2. Map untuk key-value dan lookup cepat
   3. Set untuk unique items
5. Dalam konteks Agri-POS, HashMap lebih realistis untuk sistem POS nyata karena:
6. Customer sering beli produk sama dengan quantity berbeda
   1. Efisiensi memory dan performance lebih baik
   2. User experience lebih baik (tampilan quantity)
7. Kedua pendekatan (ArrayList dan HashMap) valid tergantung requirement bisnis dan kompleksitas sistem yang dibangun.
---

## Quiz
## Quiz 1: Jelaskan perbedaan mendasar antara List, Map, dan Set
Jawaban:
List:
   Interface untuk koleksi terurut yang boleh duplikat
Elemen diakses menggunakan index (0, 1, 2, ...)
Mempertahankan urutan penambahan
Contoh implementasi: ArrayList, LinkedList
Use case: Daftar pesanan, history transaksi

Map:
   Interface untuk koleksi pasangan key-value
Key harus unik, value boleh duplikat
Tidak menggunakan index, akses via key
Contoh implementasi: HashMap, TreeMap
Use case: Inventory dengan quantity, dictionary, cache

Set:
   Interface untuk koleksi yang tidak menerima duplikat
Tidak ada index, tidak ada urutan (kecuali TreeSet/LinkedHashSet)
Otomatis menolak elemen yang sudah ada
Contoh implementasi: HashSet, TreeSet
Use case: Unique items, membership testing 

## Quiz 2: Mengapa ArrayList cocok digunakan untuk keranjang belanja sederhana?
Jawaban:
ArrayList cocok untuk keranjang belanja sederhana karena:
1. Kemudahan Penggunaan
   API sangat sederhana: add(), remove(), get()
   Tidak perlu memahami konsep key-value atau hashing
   Cocok untuk pembelajaran pemula

2. Mempertahankan Urutan
   Produk ditampilkan sesuai urutan dimasukkan
   Penting untuk user experience (customer tahu apa yang baru dimasukkan)
   Misal: "Beras → Pupuk → Bibit" tetap urut

3. Akses Sequential Cepat
   Iterasi untuk menampilkan atau hitung total sangat efisien
   Tidak perlu random access yang kompleks

4. Tidak Ada Overhead
   Tidak perlu implement equals() atau hashCode()
   Tidak perlu kelola key-value relationship

5. Sesuai Kebutuhan Sederhana
   Jika tidak perlu tracking quantity per produk
   Jika setiap item dianggap terpisah (scan barcode per item)
Contoh Real-world:
Minimarket kecil yang scan barcode satu per satu:
Scan: Beras → add ke cart
Scan: Beras lagi → add lagi (item ke-2)
Kapan TIDAK cocok:

Perlu quantity management
Perlu prevent duplikat
Cart besar dengan banyak item sama
Butuh akses random yang cepat

## Quiz 3: Bagaimana struktur Set mencegah duplikasi data?
Jawaban:
Set mencegah duplikasi menggunakan mekanisme hashing dan equals comparison

## Quiz 4: Kapan sebaiknya menggunakan Map dibandingkan List? Jelaskan dengan contoh
Jawaban:
Gunakan MAP ketika:
1. Butuh Quantity/Counter
java// ❌ ArrayList - Tidak efisien
List<Product> cart = new ArrayList<>();
cart.add(beras);  // Item 1
cart.add(beras);  // Item 2
cart.add(beras);  // Item 3
// Sulit tahu ada berapa beras!

// ✅ HashMap - Efisien
Map<Product, Integer> cart = new HashMap<>();
cart.put(beras, 3);  // Langsung quantity 3
2. Butuh Lookup Cepat by Key
java// ❌ ArrayList - O(n)
for (Product p : list) {
    if (p.getCode().equals("P01")) {
        return p;  // Harus loop semua
    }
}

// ✅ HashMap - O(1)
Product p = productMap.get("P01");  // Instant!
3. Butuh Relationship/Association
java// Contoh: Student → Grade
Map<Student, Integer> grades = new HashMap<>();
grades.put(student1, 85);
grades.put(student2, 90);

// Contoh: Username → User object
Map<String, User> users = new HashMap<>();
users.put("john123", johnObject);
4. Prevent Duplicate Keys
java// Contoh: Vote system
Map<String, Candidate> votes = new HashMap<>();
votes.put(userId, candidate);  // 1 user = 1 vote
// User yang sama vote lagi → overwrite, bukan duplikat
Gunakan LIST ketika:
1. Urutan Penting
java// History transaksi harus urut waktu
List<Transaction> history = new ArrayList<>();
history.add(trans1);  // 10:00
history.add(trans2);  // 10:05
history.add(trans3);  // 10:10
2. Boleh Ada Duplikat
java// Daftar pesanan pizza
List<String> orders = new ArrayList<>();
orders.add("Margherita");
orders.add("Pepperoni");
orders.add("Margherita");  // OK, customer pesan 2!
3. Akses by Index/Position
java// Top 10 leaderboard
List<Player> leaderboard = new ArrayList<>();
Player champion = leaderboard.get(0);  // Rank 1
Player second = leaderboard.get(1);    // Rank 2
4. Implementasi Queue/Stack
java// Antrian pelanggan
List<Customer> queue = new ArrayList<>();
queue.add(customer);  // Masuk antrian
queue.remove(0);      // Dilayani (FIFO)
Contoh Real-world di Agri-POS:
Scenario A: Customer scan produk satu-satu (barcode scanner)
→ Gunakan List
java// Setiap scan = item baru
cart.add(product);  // Scan 1
cart.add(product);  // Scan 2 (produk sama OK)
Scenario B: Customer masukkan quantity di form
→ Gunakan Map
java// Form: Beras (qty: 5)
cart.put(beras, 5);  // Langsung quantity
Scenario C: Inventory management
→ Gunakan Map
javaMap<String, Integer> stock = new HashMap<>();
stock.put("P01", 100);  // Produk P01: stock 100
stock.put("P02", 50);   // Produk P02: stock 50
Scenario D: Order history
→ Gunakan List
javaList<Order> history = new ArrayList<>();
// Urutan kronologis penting!
Kesimpulan:

Map → Ada asosiasi key-value, butuh lookup cepat, prevent dup key
List → Butuh urutan, boleh duplikat, akses by position