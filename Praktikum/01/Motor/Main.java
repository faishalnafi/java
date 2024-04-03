package Motor;
class Kendaraan {

    String brand;
    String type;
    String color;

    public Kendaraan(String brand, String type, String color) {
        this.brand = brand;
        this.type = type;
        this.color = color;
    }

    public void GetInfoKendaraan() {
        System.err.println("Merk    :"+this.brand);
        System.err.println("Tipe    :"+this.type);
        System.err.println("Warna   :"+this.color);
    }
}

public class Main {
    public static void main(String[] args) {
        Kendaraan motora = new Kendaraan("Honda", "PCX", "Merah");
        System.out.println("Motor A");
        motora.GetInfoKendaraan();
        
        System.out.println("Motor B");
        Kendaraan motorb = new Kendaraan("Yamaha", "Nmax", "Putih");
        motorb.GetInfoKendaraan();
    }
}