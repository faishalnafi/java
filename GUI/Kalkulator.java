//Mata Kuliah Pemrograman Berbasis Objek;
//Dibuat 26 Juni 2024 FaishalNafi';

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Kelas Utama yang menjalankan program
public class Kalkulator extends KalkulatorDasar {
    // Variabel untuk operasi kalkulator
    private double angka1 = 0, angka2 = 0, hasil = 0;
    private char operator;

    // Konstruktor utama
    public Kalkulator() {
        super();
        setTitle("Kalkulator Dasar");
        setSize(420, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Menambahkan action listener ke semua tombol fungsi dan angka
        for (int i = 0; i < 8; i++) {
            tombolFungsi[i].addActionListener(this);
        }

        for (int i = 0; i < 10; i++) {
            tombolAngka[i].addActionListener(this);
        }

        setVisible(true);
    }

    // Kelas Utama yang menjalankan program
    public static void main(String[] args) {
        new Kalkulator();
    }

    // Penanganan event untuk kalkulator
    @Override
    public void actionPerformed(ActionEvent e) {
        // Menangani input angka
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == tombolAngka[i]) {
                bidangInput.setText(bidangInput.getText().concat(String.valueOf(i)));
            }
        }

        // Menangani input desimal
        if (e.getSource() == tombolDesimal) {
            bidangInput.setText(bidangInput.getText().concat("."));
        }

        // Menangani operasi tambah
        if (e.getSource() == tombolTambah) {
            angka1 = Double.parseDouble(bidangInput.getText());
            operator = '+';
            bidangInput.setText("");
        }

        // Menangani operasi kurang
        if (e.getSource() == tombolKurang) {
            angka1 = Double.parseDouble(bidangInput.getText());
            operator = '-';
            bidangInput.setText("");
        }

        // Menangani operasi kali
        if (e.getSource() == tombolKali) {
            angka1 = Double.parseDouble(bidangInput.getText());
            operator = '*';
            bidangInput.setText("");
        }

        // Menangani operasi bagi
        if (e.getSource() == tombolBagi) {
            angka1 = Double.parseDouble(bidangInput.getText());
            operator = '/';
            bidangInput.setText("");
        }

        // Menangani operasi sama dengan
        if (e.getSource() == tombolSamaDengan) {
            angka2 = Double.parseDouble(bidangInput.getText());

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
            bidangInput.setText(String.valueOf(hasil));
            angka1 = hasil;
        }

        // Menangani operasi bersih
        if (e.getSource() == tombolBersih) {
            bidangInput.setText("");
        }

        // Menangani operasi hapus
        if (e.getSource() == tombolHapus) {
            String string = bidangInput.getText();
            bidangInput.setText("");
            for (int i = 0; i < string.length() - 1; i++) {
                bidangInput.setText(bidangInput.getText() + string.charAt(i));
            }
        }
    }
}

// Kelas Induk yang mengatur antarmuka dasar kalkulator
class KalkulatorDasar extends JFrame implements ActionListener {
    // Deklarasi variabel
    protected JTextField bidangInput;
    protected JButton[] tombolAngka = new JButton[10];
    protected JButton[] tombolFungsi = new JButton[8];
    protected JButton tombolTambah, tombolKurang, tombolKali, tombolBagi;
    protected JButton tombolDesimal, tombolSamaDengan, tombolHapus, tombolBersih;
    protected JPanel panel;
    protected Font huruf = new Font("Arial", Font.PLAIN, 18);

    // Konstruktor untuk kelas induk
    public KalkulatorDasar() {
        setTitle("Kalkulator Dasar");
        setSize(420, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        bidangInput = new JTextField();
        bidangInput.setBounds(50, 25, 300, 50);
        bidangInput.setFont(huruf);
        bidangInput.setEditable(false);
        add(bidangInput);

        // Inisialisasi tombol
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

        // Panel untuk tombol
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // Implementasi action listener untuk kelas dasar bisa ditambahkan di sini
    }
}
