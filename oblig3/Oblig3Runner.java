import javax.swing.JOptionPane;

public class Oblig3Runner {

	public static float kroner, euro, yen;
	public static String valuta;
	
	public static void main(String[] args){
		kroner = Float.parseFloat(JOptionPane.showInputDialog(null, "Skriv inn kroneverdi"));
		euro = Oblig3Funksjoner.kronerTilEuro(kroner);
		yen = Oblig3Funksjoner.kronerTilYen(kroner);
		
		JOptionPane.showMessageDialog(null, (int)kroner + " kroner tilsvarer " + (int)euro + " euro");
		JOptionPane.showMessageDialog(null, (int)kroner + " kroner tilsvarer " + (int)yen + " yen");
		
		JOptionPane.showMessageDialog(null, (int)yen + " yen tilsvarer " + (int)kroner + " kroner");
		
		kroner = Float.parseFloat(JOptionPane.showInputDialog(null, "Skriv inn kroneverdi"));
		valuta = JOptionPane.showInputDialog(null, "Skriv inn valuta");
		Oblig3Funksjoner.kronerTilValuta(kroner, valuta);
	}
}
