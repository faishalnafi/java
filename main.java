package Bebas;

public class Main {
    public static void main(String[] args) {
        Mobil mobil = new Mobil("Toyota", "Supra", "Merah");
        mobil.GetInfoKendaraan();

        Motor motor = new Motor("Honda", "Nmax", "Putih");
        motor.GetInfoKendaraan();
    }
}
