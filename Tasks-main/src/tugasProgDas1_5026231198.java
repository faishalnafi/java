import java.util.Scanner;

public class tugasProgDas1_5026231198 {

    public static void main(String[] args) {
        int uang;

        Scanner input = new Scanner(System.in);

        System.out.print("Masukkan jumlah uang (1.000-20.000): ");
        uang = input.nextInt();

        if (uang<=5000) {
            System.out.println("Anda Pelit!");
        }
        else if (uang<=10000) {
            System.out.println("Anda Standar!");
        }
        else if (uang>10000) {
            System.out.println("Anda Sultan!");
        }
    }
}
