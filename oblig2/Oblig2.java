import javax.swing.JOptionPane;

public class Oblig2 {

	private static String password;
	private static int weeks, hours;

	public static void oppg1() {
		String correctPassword = "password";
		boolean grantedAccess = false;

		while (!grantedAccess) {
			password = JOptionPane.showInputDialog(null, "Tast inn passordet ditt");
			JOptionPane.showMessageDialog(null, "Du har tastet inn " + password);

			if (password.equals(correctPassword)) {
				grantedAccess = true;
				JOptionPane.showMessageDialog(null, "Access Granted");
			} else {
				JOptionPane.showMessageDialog(null, "Access Denied");
			}
		}
	}

	public static void oppg2() {
		weeks = Integer.parseInt(JOptionPane.showInputDialog(null, "Skriv inn antall uker boken er lånt"));

		if (weeks > 4) {
			JOptionPane.showMessageDialog(null, "Lever boken tilbake");
		} else if (weeks < 4) {
			JOptionPane.showMessageDialog(null, "Du kan låne boken lengre");
		} else if (weeks == 4) {
			JOptionPane.showMessageDialog(null, "Du må levere boken tilbake i dag");
		}
	}

	public static void oppg3() {
		hours = Integer.parseInt(JOptionPane.showInputDialog(null, "Hvor lenge bruker du på sosiale medier daglig?"));

		if (hours > 2) {
			JOptionPane.showMessageDialog(null, "For mye");
		} else if (hours < 2) {
			JOptionPane.showMessageDialog(null, "For lite");
		} else if (hours == 2) {
			JOptionPane.showMessageDialog(null, "Akkurat passe");
		}
	}

	public static void main(String[] args) {
		oppg1();
		oppg2();
		oppg3();
	}
}
