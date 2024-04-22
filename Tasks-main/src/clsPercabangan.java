import java.util.Scanner;

public class clsPercabangan {

    public static void main (String[] args) {
        int nilai;
        Scanner input = new Scanner(System.in);

        System.out.println("Masukkan nilai ujian: ");
        nilai = input.nextInt();

        if (nilai>50) {
            System.out.println("Selamat anda lulus!");
        }
        else {
            System.out.println("Selamat Mencoba Lain Waktu!");
        }
    }
}
