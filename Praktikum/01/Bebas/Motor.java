package Bebas;

public class Motor extends Kendaraan{
    String type; 

    public Motor(String brand, String brandType, String color) {
        super(brand, brandType, color);
        this.type = "Motor";

    }

    public void GetInfoKendaraan() {
        System.err.println("Merk        :"+this.brand);
        System.err.println("Tipe        :"+this.brandType);
        System.err.println("Warna       :"+this.color);
        System.err.println("Kendaraan   :"+this.type);
    }
}
