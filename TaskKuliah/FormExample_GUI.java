import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class FormExample_GUI extends JFrame implements ActionListener {
    // Deklarasi Komponen
    private JTextField username = new JTextField();
    private JPasswordField password = new JPasswordField();
    private JTextArea comments = new JTextArea(2, 15);

    private JPanel panelAtas = new JPanel();
    private JPanel panelTengah = new JPanel();
    private JPanel panelBawah = new JPanel();

    private JLabel outputusername = new JLabel();
    private JLabel outputpassword = new JLabel();
    private JTextArea outputcomments = new JTextArea(2, 15);

    private JLabel usernameLabel = new JLabel("Username");
    private JLabel passwordLabel = new JLabel("Password");
    private JLabel commentsLabel = new JLabel("Comments");

    private JButton display = new JButton("Display");
    private JButton delete = new JButton("Delete");

    public FormExample_GUI() {
        // Mengatur nama title
        setTitle("Feedback Form");

        // Pengaturan TextArea
        comments.setLineWrap(true);
        comments.setWrapStyleWord(true);
        outputcomments.setLineWrap(true);
        outputcomments.setWrapStyleWord(true);
        outputcomments.setEditable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(250, 100);
        setSize(570, 270);
        setResizable(true);

        // Menyusun panel atas
        panelAtas.setLayout(new GridLayout(3, 2));
        panelAtas.add(usernameLabel);
        panelAtas.add(username);
        panelAtas.add(passwordLabel);
        panelAtas.add(password);
        panelAtas.add(commentsLabel);
        panelAtas.add(comments);

        // Menyusun panel tengah
        panelTengah.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTengah.add(display);
        panelTengah.add(delete);
        display.setBackground(Color.BLUE);
        display.setForeground(Color.WHITE);
        delete.setBackground(Color.BLUE);
        delete.setForeground(Color.WHITE);

        // Menyusun panel bawah
        panelBawah.setLayout(new GridLayout(3, 2));
        panelBawah.add(new JLabel("Username"));
        panelBawah.add(outputusername);
        panelBawah.add(new JLabel("Password"));
        panelBawah.add(outputpassword);
        panelBawah.add(new JLabel("Comments"));
        panelBawah.add(outputcomments);

        // Menambahkan ActionListener
        display.addActionListener(this);
        delete.addActionListener(this);

        // Menambahkan panel ke container
        Container container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(panelAtas, BorderLayout.NORTH);
        container.add(panelTengah, BorderLayout.CENTER);
        container.add(panelBawah, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == display) {
            outputusername.setText(username.getText());
            outputpassword.setText(new String(password.getPassword()));
            outputcomments.setText(comments.getText());
        } else if (source == delete) {
            username.setText("");
            password.setText("");
            comments.setText("");
            outputusername.setText("");
            outputpassword.setText("");
            outputcomments.setText("");
        }
    }

    public static void main(String[] args) {
        new FormExample_GUI();
    }
}
