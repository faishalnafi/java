package Bebas;

public class Mobil extends Kendaraan{
    String type; 
    Integer roda;

    public Mobil(String brand, String brandType, String color,Integer roda) {
        super(brand, brandType, color);
        this.type = "Mobil";
        this.roda = roda;
    }

    public void GetInfoKendaraan() {
        System.err.println("Merk        :"+this.brand);
        System.err.println("Tipe        :"+this.brandType);
        System.err.println("Warna       :"+this.color);
        System.err.println("Kendaraan   :"+this.type);
        System.out.println("Roda        :"+this.roda);
    }
}
