package nafi;
//Mata Kuliah Pemrograman Berbasis Objek;
//Dibuat 5 April 2024 FaishalNafi';

public class menuMinum extends menuResto{
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