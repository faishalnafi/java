package nafi;
//Mata Kuliah Pemrograman Berbasis Objek;
//Dibuat 5 April 2024 FaishalNafi';

public class menuMakan extends menuResto{
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