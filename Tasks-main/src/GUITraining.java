import javax.swing.JOptionPane;

public class GUITraining {

	public static void main(String[] args) {
		String month = JOptionPane.showInputDialog("Enter a month");
		int date = Integer.parseInt(JOptionPane.showInputDialog("Enter a date"));
		
		JOptionPane.showMessageDialog(null, "The date that you have entered is " + month + " " + date);
	}

}
