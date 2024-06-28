import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FormExample extends JFrame implements ActionListener{
	//Deklarasi Komponen
	JTextField username = new JTextField();
	JPasswordField password = new JPasswordField();
	JTextArea comments = new JTextArea(2,15);

	JPanel PanelAtas = new JPanel();
	JPanel PanelTengah = new JPanel ();
	JPanel PanelBawah = new JPanel ();
	Container container = new Container();

	JLabel outputusername = new JLabel();
	JLabel outputpassword = new JLabel();
	JTextArea outputcomments = new JTextArea(2,15);

	JLabel displayusernameLabel = new JLabel(" Username  ");
	JLabel displaypasswordLabel = new JLabel(" Password  ");
	JLabel displaycommentsLabel = new JLabel(" Comments  ");

	JButton display = new JButton("Display");
	JButton delete = new JButton("Delete");

	JLabel usernameLabel = new JLabel(" Username  ");
	JLabel passwordLabel = new JLabel(" Password  ");
	JLabel commentsLabel = new JLabel(" Comments  ");

	public FormExample() {
		//Mengatur nama title
		setTitle("Feedback Form");

		//Berfungsi untuk menangani teks yang melebihi lebar komponen dengan cara melompat baris dan memastikan pemisahan kata yang benar
		comments.setLineWrap(true);
		comments.setWrapStyleWord(true);
		outputcomments.setLineWrap(true);
		//outputcomments.setWrapStyleWord(true);
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		//Mengatur letak dan size sebuah tampilan
		setLocation (250,100);
		setSize(570,270);
		setResizable (true);
		container = getContentPane();
		
		//Menyusun PanelAtas dengan GridLayout 3 Baris 2 Kolom
		PanelAtas.setLayout(new GridLayout(3,2));
		PanelAtas.add (usernameLabel);
		PanelAtas.add (username);
		PanelAtas.add (passwordLabel);
		PanelAtas.add (password);
		PanelAtas.add (commentsLabel);
		PanelAtas.add (comments);
		
		//Menyusun PanelAtas dengan FlowLayout
		PanelTengah.setLayout(new FlowLayout(FlowLayout.CENTER));
		PanelTengah.add (display);
		PanelTengah.add (delete);
		//Menambahkan Action ke sebuah button
		display.addActionListener(this);
		//mengganti warna backgroudcolor pada button
		display.setBackground(Color.BLUE);
		//mengganti warna font pada button
		display.setForeground(Color.WHITE);
		delete.addActionListener(this);
		delete.setBackground(Color.BLUE);
		delete.setForeground(Color.WHITE);

		//Menyusun PanelAtas dengan GridLayout 3 Baris 2 Kolom
		PanelBawah.setLayout(new GridLayout(3,2));
		PanelBawah.add (displayusernameLabel);
		PanelBawah.add (outputusername);
		PanelBawah.add (displaypasswordLabel);
		PanelBawah.add (outputpassword);
		PanelBawah.add (displaycommentsLabel);
		PanelBawah.add (outputcomments);

		//Menambahkan Panel - Panel yang sudah disusun kedalam container
		container.setLayout(new BorderLayout());
        container.add(PanelAtas, BorderLayout.NORTH);
        container.add(PanelTengah, BorderLayout.CENTER);
        container.add(PanelBawah, BorderLayout.SOUTH);
		display.addActionListener(this);
		delete.addActionListener(this);
		setContentPane(container);
		show();
	}
	public void actionPerformed (ActionEvent e)
	{
		Object user = e.getSource();
		
		if (user == display)	//Mengatur sebuah aksi button display ketika di click
		{
			//Proses/cara mengambil data dari TextField yang sudah kita inputkan
			outputusername.setText(username.getText());
			outputpassword.setText(password.getText());
			outputcomments.setText(comments.getText());
		}
		if (user == delete)		//Mengatur sebuah aksi button delete ketika di click
		{
			//proses/cara ketika button di click, inputan yang sudah kita masukkan akan di hilangkan/reset semua
			username.setText(null);
			password.setText(null);
			comments.setText(null);
			outputusername.setText(null);
			outputpassword.setText(null);
			outputcomments.setText(null);
		}

	}

   public static void main(String[] args) {
        FormExample input = new FormExample();
   }
}