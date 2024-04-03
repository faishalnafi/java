package Bebas;

public class Kendaraan {
    String brand;
    String brandType;
    String color;

    public Kendaraan(String brand, String brandType, String color) {
        this.brand = brand;
        this.brandType = brandType;
        this.color = color;
    }

    public void GetInfoKendaraan() {
        System.err.println("Merk    :"+this.brand);
        System.err.println("Tipe    :"+this.brandType);
        System.err.println("Warna   :"+this.color);
    }
}
