// package nafi;
//Mata Kuliah Pemrograman Berbasis Objek;
//Dibuat 5 April 2024 FaishalNafi';

class menuResto {
    String nama;
    double harga;
    int jumlah;

    public menuResto(String nama, double harga, int jumlah) {
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
    }
}

class menuMinum extends menuResto{
    String tipe;

    public menuMinum(String nama, double harga, int jumlah) {
        super(nama, harga, jumlah);
        this.tipe = " Gelas";
    }

    // @Override
    public void info() {
        System.err.println("Nama Minum        : "+nama);
        System.err.println("Harga Minum       : "+harga);
        System.err.println("Jumlah Minum      : "+jumlah+tipe);
        System.err.println("subTotal Harga    : "+subtotal()+"\n");
    }

    public double subtotal() {
        return harga*jumlah;
    }

    public void subharga() {
        System.out.println(subtotal());
    }
}

class menuMakan extends menuResto{
    String tipe;

    public menuMakan(String nama, double harga, int jumlah) {
        super(nama, harga, jumlah);
        this.tipe = " Porsi";
    }

    // @Override
    public void info() {
        System.err.println("Nama Makanan      : "+nama);
        System.err.println("Harga Makanan     : "+harga);
        System.err.println("Jumlah Makanan    : "+jumlah+tipe);
        System.err.println("subTotal Harga    : "+subtotal()+"\n");
    }

    public double subtotal() {
        return harga*jumlah;
    }
}

public class Main {
    public static void main(String[] args) {
        menuMakan SupAyam = new menuMakan("Sup Ayam", 15000, 3);
        SupAyam.info();

        menuMinum esTeh = new menuMinum("Es Teh", 3000, 5);
        esTeh.info();

        menuMakan Bakso = new menuMakan("Bakso", 8000, 4);
        Bakso.info();

        menuMinum esJeruk = new menuMinum("Es Jeruk", 5000, 2);
        esJeruk.info();
    }
}