import javax.swing.JOptionPane;

public class Oblig1 {

	public static void main(String[] args) {
		/*
		 * telefon.start("instagram"); 
		 * instagram.loggInn(brukernavn, passord);
		 * instagram.taBilde(); 
		 * bilde.leggTilTags(String[] tags);
		 * instagram.post(bilde);
		 * 
		 */

		System.out.println("Dette er mitt første program.");
		System.out.print("Dette er mitt \nførste program. \n");

		JOptionPane.showMessageDialog(null, "Dette er mitt første program");
		JOptionPane.showInputDialog("Hva heter du?");

		System.out.println("Dette skal komme til skjermen");
		JOptionPane.showMessageDialog(null, "Dette skal komme som dialogbokser");
		System.out.println("En ny linje som kommer til skjermen");
		System.out.println("En siste linje som kommer til skjermen");
		System.out.println("En aller siste linje til skjermen");

		String navn = JOptionPane.showInputDialog("Hva heter du?");
		JOptionPane.showMessageDialog(null, "Du heter " + navn + "!");
	}
}
