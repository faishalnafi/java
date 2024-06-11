import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class FormPilihanLuas extends JFrame implements ActionListener
{
	//Deklarasi Komponen
	private Container container = new Container();
	
	private JCheckBox LuasPersegipanjang, LuasSegitiga;
	ButtonGroup buttonGroup = new ButtonGroup();

	private JLabel kosong1 = new JLabel ();
	private JLabel kosong2 = new JLabel ();
	private JLabel kosong3 = new JLabel ();
	private JLabel kosong4 = new JLabel ();
	private JLabel menu = new JLabel (" Menu Pilihan ");

	private JButton ok = new JButton (" OK ");

	public FormPilihanLuas()
	{
		setTitle ("MENU PERHITUNGAN BANGUN DATAR ");
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocation (250,100);
		setSize (600,200);
		setResizable (true);
		container = getContentPane();
		container.setLayout (new FlowLayout());
		container.setLayout (new GridLayout (3,3));
		container.add (menu);
		
		//Cara Menggunakan CheckBox / Pilihan Hanya Satu
		LuasPersegipanjang = new JCheckBox(" Hitung Luas Persegi Panjang ");
        LuasSegitiga = new JCheckBox(" Hitung Luas Segitiga");
		buttonGroup.add(LuasPersegipanjang);
		buttonGroup.add(LuasSegitiga);
		
		//Menambhkan komponen ke dalam Frame/Container
		container.add (LuasPersegipanjang);
		container.add (kosong1);
		container.add (kosong2);
		container.add (LuasSegitiga);
		container.add (kosong3);
		container.add (kosong3);
		container.add (kosong4);
		container.add (kosong4);
		container.add (ok);
		ok.addActionListener(this);
		ok.setBackground(Color.BLUE);
		ok.setForeground(Color.WHITE);
		show();
	}
	public void actionPerformed(ActionEvent e)
	{
		Object user = e.getSource();
		
		//proses perpindahan Frame Awal ke Frame Perhitungan Luas Persegi Panjang
		if (LuasPersegipanjang.isSelected())
		{
			if (user == ok)
			{
				new LuasPersegiPanjang();
				//dispose berfungsi untuk menutup jendela Frame yang sebelumnya
				dispose();
			}
		}
		//proses perpindahan Frame Awal ke Frame Perhitungan Luas Segitiga
		else if (LuasSegitiga.isSelected())
		{
			if (user == ok)
			{
				new LuasSegitiga();
				//dispose berfungsi untuk menutup jendela Frame yang sebelumnya
				dispose();
			}
		}
	}
}

class LuasPersegiPanjang extends JFrame implements ActionListener
{
	//Deklarasi Komponen
	private Container containerpp = new Container();

	private JLabel Panjang = new JLabel(" Masukkan Panjang");
	private JLabel Lebar = new JLabel(" Masukkan Lebar");

	private JTextField inputpanjang = new JTextField();
	private JTextField inputlebar = new JTextField();

	private JLabel hasil = new JLabel(" Hasil Luas");
	private JLabel outputhasil = new JLabel();

	private JButton display = new JButton("Display");
	private JButton kembali = new JButton("Kembali");
	
	//Deklarasi Variabel untuk perhitungan										
	int A,B,C;

	public LuasPersegiPanjang()
	{
		setTitle ("Luas Persegi Panjang ");
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocation (250,100);
		setSize (600,200);
		setResizable (true);
		containerpp = getContentPane();
		containerpp.setLayout (new FlowLayout());
		containerpp.setLayout (new GridLayout (4,2));
		containerpp.add(Panjang);
		containerpp.add(inputpanjang);
		containerpp.add(Lebar);
		containerpp.add(inputlebar);
		containerpp.add(hasil);
		containerpp.add(outputhasil);
		containerpp.add(display);
		containerpp.add(kembali);
		display.addActionListener(this);
		display.setBackground(Color.BLUE);
		display.setForeground(Color.WHITE);
		kembali.addActionListener(this);
		kembali.setBackground(Color.BLACK);
		kembali.setForeground(Color.WHITE);
		show();
	}

	public void actionPerformed(ActionEvent e)
	{
		Object user = e.getSource();

		//proses perhitungan dan menampilkan hasil
		if (user == display)
		{
			//cara menggunakan rumus di program GUI
			A = Integer.parseInt(inputpanjang.getText());
			B = Integer.parseInt(inputlebar.getText());
			C = A * B;
			String hasil = Integer.toString(C);
			outputhasil.setText(hasil);
		}
		//proses perpindahan ketika user/kita menekan button 'kembali'
		if (user == kembali)
		{
			new FormPilihanLuas();
			dispose();
		}
	}
}

class LuasSegitiga extends JFrame implements ActionListener
{
	private Container containersegitiga = new Container();

	private JLabel Alas = new JLabel(" Masukkan Alas");
	private JLabel Tinggi = new JLabel(" Masukkan Tingi");

	private JTextField inputalas = new JTextField();
	private JTextField inputtinggi = new JTextField();

	private JLabel hasil = new JLabel(" Hasil Luas");
	private JLabel outputhasil = new JLabel();

	private JButton display = new JButton("Display");
	private JButton kembali = new JButton("Kembali");
													 
	int A,B,C;

	public LuasSegitiga()
	{
		setTitle ("Luas Segitiga ");
		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setLocation (250,100);
		setSize (600,200);
		setResizable (true);
		containersegitiga = getContentPane();
		containersegitiga.setLayout (new FlowLayout());
		containersegitiga.setLayout (new GridLayout (4,2));
		containersegitiga.add(Alas);
		containersegitiga.add(inputalas);
		containersegitiga.add(Tinggi);
		containersegitiga.add(inputtinggi);
		containersegitiga.add(hasil);
		containersegitiga.add(outputhasil);
		containersegitiga.add(display);
		containersegitiga.add(kembali);
		display.addActionListener(this);
		display.setBackground(Color.BLUE);
		display.setForeground(Color.WHITE);
		kembali.addActionListener(this);
		kembali.setBackground(Color.BLACK);
		kembali.setForeground(Color.WHITE);
		show();
	}

	public void actionPerformed(ActionEvent e)
	{
		Object user = e.getSource();
		
		//proses perhitungan dan menampilkan hasil
		if (user == display)
		{
			//cara menggunakan rumus di program GUI
			A = Integer.parseInt(inputalas.getText());
			B = Integer.parseInt(inputtinggi.getText());
			C = (A * B)/2;
			String hasil = Integer.toString(C);
			outputhasil.setText(hasil);
		}
		//proses perpindahan ketika user/kita menekan button 'kembali'
		if (user == kembali)
		{
			new FormPilihanLuas();
			dispose();
		}
	}
}

public class TwoFrameExample
{
	public static void main(String[] args) 
	{
		new FormPilihanLuas();
	}
}
