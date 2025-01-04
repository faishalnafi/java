package nafi;
//Mata Kuliah Pemrograman Berbasis Objek;
//Dibuat 5 April 2024 FaishalNafi';

public class pesanMakan {
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