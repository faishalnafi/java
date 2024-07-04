// TA Mata Kuliah Pemrograman Berbasis Objek;
//Dibuat 20 2024 FaishalNafi';

// LIBRARY
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Kelas Utama
public class Kalkulator extends JFrame implements ActionListener {

    // Kelas Induk
    static class KalkulatorDasar extends JFrame {
        //DEKLARASI VARIABEL
        protected JTextField bidangInput;
        protected JButton[] tombolAngka = new JButton[10];
        protected JButton[] tombolFungsi = new JButton[8];
        protected JButton tombolTambah, tombolKurang, tombolKali, tombolBagi;
        protected JButton tombolDesimal, tombolSamaDengan, tombolHapus, tombolBersih;
        protected JPanel panel;
        protected Font huruf = new Font("Arial", Font.PLAIN, 18);

        //KONSTRUKTOR KALKULATOR
        public KalkulatorDasar() {
            setTitle("Kalkulator By Nafi'");
            setSize(420, 550);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(null);

            bidangInput = new JTextField();
            bidangInput.setBounds(50, 25, 300, 50);
            bidangInput.setFont(huruf);
            bidangInput.setEditable(false);
            add(bidangInput);

            //INISIALISASI TOMBOL
            tombolTambah = new JButton("+");
            tombolKurang = new JButton("-");
            tombolKali = new JButton("*");
            tombolBagi = new JButton("/");
            tombolDesimal = new JButton(".");
            tombolSamaDengan = new JButton("=");
            tombolHapus = new JButton("Hapus");
            tombolBersih = new JButton("Bersih");

            tombolFungsi[0] = tombolTambah;
            tombolFungsi[1] = tombolKurang;
            tombolFungsi[2] = tombolKali;
            tombolFungsi[3] = tombolBagi;
            tombolFungsi[4] = tombolDesimal;
            tombolFungsi[5] = tombolSamaDengan;
            tombolFungsi[6] = tombolHapus;
            tombolFungsi[7] = tombolBersih;

            for (int i = 0; i < 8; i++) {
                tombolFungsi[i].setFont(huruf);
                tombolFungsi[i].setFocusable(false);
            }

            for (int i = 0; i < 10; i++) {
                tombolAngka[i] = new JButton(String.valueOf(i));
                tombolAngka[i].setFont(huruf);
                tombolAngka[i].setFocusable(false);
            }

            tombolHapus.setBounds(50, 430, 145, 50);
            tombolBersih.setBounds(205, 430, 145, 50);
            add(tombolHapus);
            add(tombolBersih);

            // PANEL TOMBOL
            panel = new JPanel();
            panel.setBounds(50, 100, 300, 300);
            panel.setLayout(new GridLayout(4, 4, 10, 10));

            panel.add(tombolAngka[1]);
            panel.add(tombolAngka[2]);
            panel.add(tombolAngka[3]);
            panel.add(tombolTambah);
            panel.add(tombolAngka[4]);
            panel.add(tombolAngka[5]);
            panel.add(tombolAngka[6]);
            panel.add(tombolKurang);
            panel.add(tombolAngka[7]);
            panel.add(tombolAngka[8]);
            panel.add(tombolAngka[9]);
            panel.add(tombolKali);
            panel.add(tombolDesimal);
            panel.add(tombolAngka[0]);
            panel.add(tombolSamaDengan);
            panel.add(tombolBagi);

            add(panel);

            setVisible(true);
        }
    }

    // Kelas Turunan
    private double angka1 = 0, angka2 = 0, hasil = 0;
    private char operator;

    public Kalkulator() {
        super();
        KalkulatorDasar kalkulatorDasar = new KalkulatorDasar();
        kalkulatorDasar.setTitle("Kalkulator By Nafi'");
        kalkulatorDasar.setSize(420, 550);
        kalkulatorDasar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        kalkulatorDasar.setLayout(null);

        for (int i = 0; i < 8; i++) {
            kalkulatorDasar.tombolFungsi[i].addActionListener(this);
        }

        for (int i = 0; i < 10; i++) {
            kalkulatorDasar.tombolAngka[i].addActionListener(this);
        }
    }

    // KELAS UTAMA RUNNING
    public static void main(String[] args) {
        new Kalkulator();
    }

    //PENANGANAN EVENT
    @Override
    public void actionPerformed(ActionEvent e) {
        KalkulatorDasar kalkulatorDasar = new KalkulatorDasar();
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == kalkulatorDasar.tombolAngka[i]) {
                kalkulatorDasar.bidangInput.setText(kalkulatorDasar.bidangInput.getText().concat(String.valueOf(i)));
            }
        }
        if (e.getSource() == kalkulatorDasar.tombolDesimal) {
            kalkulatorDasar.bidangInput.setText(kalkulatorDasar.bidangInput.getText().concat("."));
        }
        if (e.getSource() == kalkulatorDasar.tombolTambah) {
            angka1 = Double.parseDouble(kalkulatorDasar.bidangInput.getText());
            operator = '+';
            kalkulatorDasar.bidangInput.setText("");
        }
        if (e.getSource() == kalkulatorDasar.tombolKurang) {
            angka1 = Double.parseDouble(kalkulatorDasar.bidangInput.getText());
            operator = '-';
            kalkulatorDasar.bidangInput.setText("");
        }
        if (e.getSource() == kalkulatorDasar.tombolKali) {
            angka1 = Double.parseDouble(kalkulatorDasar.bidangInput.getText());
            operator = '*';
            kalkulatorDasar.bidangInput.setText("");
        }
        if (e.getSource() == kalkulatorDasar.tombolBagi) {
            angka1 = Double.parseDouble(kalkulatorDasar.bidangInput.getText());
            operator = '/';
            kalkulatorDasar.bidangInput.setText("");
        }
        if (e.getSource() == kalkulatorDasar.tombolSamaDengan) {
            angka2 = Double.parseDouble(kalkulatorDasar.bidangInput.getText());

            switch (operator) {
                case '+':
                    hasil = angka1 + angka2;
                    break;
                case '-':
                    hasil = angka1 - angka2;
                    break;
                case '*':
                    hasil = angka1 * angka2;
                    break;
                case '/':
                    hasil = angka1 / angka2;
                    break;
            }
            kalkulatorDasar.bidangInput.setText(String.valueOf(hasil));
            angka1 = hasil;
        }
        if (e.getSource() == kalkulatorDasar.tombolBersih) {
            kalkulatorDasar.bidangInput.setText("");
        }
        if (e.getSource() == kalkulatorDasar.tombolHapus) {
            String string = kalkulatorDasar.bidangInput.getText();
            kalkulatorDasar.bidangInput.setText("");
            for (int i = 0; i < string.length() - 1; i++) {
                kalkulatorDasar.bidangInput.setText(kalkulatorDasar.bidangInput.getText() + string.charAt(i));
            }
        }
    }
}