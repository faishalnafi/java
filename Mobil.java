package Bebas;

public class Mobil extends Kendaraan{
    String type; 

    public Mobil(String brand, String brandType, String color) {
        super(brand, brandType, color);
        this.type = "Mobil";
    }

    public void GetInfoKendaraan() {
        System.err.println("Merk        :"+this.brand);
        System.err.println("Tipe        :"+this.brandType);
        System.err.println("Warna       :"+this.color);
        System.err.println("Kendaraan   :"+this.type);
    }
}
