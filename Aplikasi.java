//Mata Kuliah Pemrograman Berbasis Objek;
//Dibuat 8 Juni 2024 FaishalNafi';

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Kelas Utama
public class Aplikasi extends JFrame {

    public static void main(String[] args) {
        new LoginWindow();
    }
}

// Kelas Login
class LoginWindow extends JFrame implements ActionListener {

    private JTextField bidangUsername;
    private JPasswordField bidangPassword;
    private JButton tombolSubmit;

    public LoginWindow() {
        setTitle("Halaman Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JLabel labelUsername = new JLabel("Username:");
        JLabel labelPassword = new JLabel("Password:");

        bidangUsername = new JTextField();
        bidangPassword = new JPasswordField();
        tombolSubmit = new JButton("Lanjut Coy");

        tombolSubmit.addActionListener(this);

        add(labelUsername);
        add(bidangUsername);
        add(labelPassword);
        add(bidangPassword);
        add(new JLabel()); // Placeholder
        add(tombolSubmit);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = bidangUsername.getText();
        String password = new String(bidangPassword.getPassword());

        if (username.equals("nafi") && password.equals("nafi")) {
            new KalkulatorLuasWindow();
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Username atau Password salah Sayang.");
        }
    }
}

// Kelas Kalkulator Luas
class KalkulatorLuasWindow extends JFrame implements ActionListener {

    private JTextField bidangPanjang, bidangLebar, bidangHasil;
    private JButton tombolHitung;

    public KalkulatorLuasWindow() {
        setTitle("Kalkulator Luas Persegi Panjang");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        JLabel labelPanjang = new JLabel("Panjang:");
        JLabel labelLebar = new JLabel("Lebar:");
        JLabel labelHasil = new JLabel("Luas:");

        bidangPanjang = new JTextField();
        bidangLebar = new JTextField();
        bidangHasil = new JTextField();
        bidangHasil.setEditable(false);

        tombolHitung = new JButton("Hitung");
        tombolHitung.addActionListener(this);

        add(labelPanjang);
        add(bidangPanjang);
        add(labelLebar);
        add(bidangLebar);
        add(labelHasil);
        add(bidangHasil);
        add(new JLabel()); // Placeholder
        add(tombolHitung);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double panjang = Double.parseDouble(bidangPanjang.getText());
            double lebar = Double.parseDouble(bidangLebar.getText());
            double luas = panjang * lebar;
            bidangHasil.setText(String.valueOf(luas));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Input tidak valid. Masukkan angka yang benar.");
        }
    }
}