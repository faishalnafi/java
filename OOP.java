// package nafi;
//Mata Kuliah Pemrograman Berbasis Objek;
//Dibuat 4 April 2024 FaishalNafi';

class mesinKereta {

    String brand;
    Integer ccMesin;
    Integer tahun;
    String seri;

    public mesinKereta(String brand, Integer ccMesin, Integer tahun, String seri) {
        this.brand = brand;
        this.ccMesin = ccMesin;
        this.tahun = tahun;
        this.seri = seri;
    }

    public void info() {
        System.out.println("Brand Mesin    : "+this.brand);
        System.out.println("Jenis Mesin    : "+this.ccMesin);
        System.out.println("Tahun Pembuatan: "+this.tahun);
        System.out.println("Seri Mesin     : "+this.seri);
    }
}

class disel extends mesinKereta {

    Integer roda;
    String tipe;

    public disel(Integer roda, String brand, Integer ccMesin, Integer tahun, String seri) {
        super(brand,ccMesin,tahun,seri);
        this.roda = roda;
        this.tipe = "disel";
    }

    public void info() {
        System.out.println("Brand Mesin    : "+this.brand);
        System.out.println("Jenis Mesin    : "+this.ccMesin);
        System.out.println("Tahun Pembuatan: "+this.tahun);
        System.out.println("Seri Mesin     : "+this.seri);
        System.out.println("Tipe Mesin     : "+this.tipe);
        System.out.println("Jumlah Roda    : "+this.roda);
    }
}

class uap extends mesinKereta {

    Integer roda;
    String tipe;

    public uap(Integer roda, String brand, Integer ccMesin, Integer tahun, String seri) {
        super(brand,ccMesin,tahun,seri);
        this.roda = roda;
        this.tipe = "uap";
    }

    public void info() {
        System.out.println("Brand Mesin    : "+this.brand);
        System.out.println("Jenis Mesin    : "+this.ccMesin);
        System.out.println("Tahun Pembuatan: "+this.tahun);
        System.out.println("Seri Mesin     : "+this.seri);
        System.out.println("Tipe Mesin     : "+this.tipe);
        System.out.println("Jumlah Roda    : "+this.roda);
    }
}

public class OOP {
    public static void main(String[] args) {
        mesinKereta kereta206 = new disel(24, "general electric", 206, 2013, "CC");
        System.out.println("kereta206");
        kereta206.info();
        System.out.println("====================");
        System.out.println("                    ");

        mesinKereta kereta205 = new uap(18, "maryland", 205, 1977, "BB");
        System.out.println("kereta205");
        kereta205.info();
        System.out.println("====================");
        System.out.println("                    ");
    }
}