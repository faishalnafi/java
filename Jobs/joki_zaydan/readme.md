# Kalkulator Java dengan UI menggunakan Swing

Program kalkulator ini dibuat menggunakan bahasa pemrograman Java dengan menggunakan library Swing untuk membangun User Interface (UI). Kalkulator ini memiliki fungsi dasar seperti penjumlahan, pengurangan, perkalian, pembagian, dan operasi matematika lainnya seperti modulus. Kalkulator ini juga memiliki fitur untuk menyimpan riwayat perhitungan.

## Struktur Program dan Penjelasan Fungsi Setiap Kelas

### 1. **main.java**
File ini adalah titik masuk utama (entry point) untuk aplikasi kalkulator. Fungsi `main` di dalam kelas ini akan menjalankan aplikasi dengan memanggil `SwingUtilities.invokeLater` untuk memulai UI kalkulator di thread yang tepat.

### 2. **CalculatorUI.java**
Kelas ini bertanggung jawab untuk membuat dan mengelola tampilan antarmuka pengguna (UI) kalkulator. Beberapa komponen penting dari kelas ini adalah:
- `JTextField inputField`: Menampilkan input atau hasil perhitungan.
- `JTextArea historyArea`: Menampilkan riwayat perhitungan.
- `JButton button`: Tombol untuk angka dan operasi matematika.
- `JPanel panel`: Panel utama yang menampung input, tombol, dan riwayat perhitungan.

Fungsi utama dalam kelas ini adalah `initUI()`, yang menginisialisasi semua komponen UI, menambahkan tombol kalkulator ke dalam panel, dan menetapkan aksi untuk setiap tombol melalui `ButtonClickListener`.

### 3. **CalculatorInput.java**
Kelas ini menangani pengelolaan input dari pengguna. Fungsi-fungsi penting dalam kelas ini adalah:
- `append(String value)`: Menambahkan karakter atau angka ke input.
- `deleteLast()`: Menghapus karakter terakhir dari input.
- `clear()`: Menghapus seluruh input.
- `getCurrentInput()`: Mengambil input saat ini yang sudah dimasukkan oleh pengguna.

### 4. **CalculatorLogic.java**
Kelas ini berfungsi untuk menangani logika perhitungan. Kelas ini mengandalkan kelas `CalculatorOperations` untuk menghitung hasil dari ekspresi yang dimasukkan pengguna. Metode utama dalam kelas ini adalah `evaluateExpression(String expression)`, yang menerima ekspresi sebagai string dan mengembalikan hasil perhitungan dalam bentuk string.

### 5. **CalculatorOperations.java**
Kelas ini bertanggung jawab untuk melakukan evaluasi ekspresi matematika. Kelas ini menggunakan `ExpressionParser` untuk memparsing ekspresi dan menghitung hasilnya berdasarkan urutan operator dan prioritas.

### 6. **ExpressionParser.java**
Kelas ini digunakan untuk memparsing ekspresi matematika dan menilai hasil berdasarkan prioritas operator (seperti *, /, %, +, -). Ekspresi dipecah menjadi token dan dihitung dengan menggunakan dua stack: satu untuk angka dan satu untuk operator. Metode `evaluateExpressionWithPriority` menangani logika perhitungan dengan memperhatikan prioritas operator.

## Relasi Antar Kelas

### Komposisi:
- **CalculatorUI** menggunakan **CalculatorInput** untuk menangkap input pengguna.
- **CalculatorLogic** mengandalkan **CalculatorOperations** untuk menghitung ekspresi.

### Asosiasi:
- **ExpressionParser** diakses oleh **CalculatorOperations**.

## Cara Menggunakan Program
1. Jalankan aplikasi dengan menjalankan kelas `main.java` di IDE atau command line.
2. Tampilan kalkulator akan muncul dengan panel input dan tombol-tombol operasi matematika.
3. Gunakan tombol angka dan operator untuk memasukkan ekspresi matematika.
4. Tekan tombol "=" untuk menghitung hasil ekspresi yang dimasukkan.
5. Riwayat perhitungan akan ditampilkan di bagian bawah layar, dan hasil akan ditampilkan di input field.
6. Gunakan tombol "C" untuk membersihkan input dan riwayat, atau "DEL" untuk menghapus karakter terakhir.

## Contoh Penggunaan
1. **Masukkan**: `2 + 3 * 4`
2. **Hasil**: `2 + 3 * 4 = 14`

## Fitur Tambahan
- Riwayat perhitungan disimpan dan ditampilkan setelah setiap operasi.
- Menangani berbagai operasi matematika dasar: penjumlahan, pengurangan, perkalian, pembagian, dan modulus.

## Persyaratan
- Java 8 atau versi lebih tinggi.
- IDE atau compiler Java untuk menjalankan program.
