import javax.swing.JOptionPane;

public class Oblig3Funksjoner {

	public static float kronerTilEuro(float kroner) {
		return kroner * 0.12f;
	}

	public static float kronerTilYen(float kroner) {
		return kroner * 17;
	}

	public static float euroTilYen(float euro) {
		return (euro / 0.12f) * 17;
	}

	public static float yenTilKroner(float yen) {
		return yen / 17;
	}

	public static void kronerTilValuta(float kroner, String valuta) {
		switch (valuta.toLowerCase()) {
		case "euro":
			JOptionPane.showMessageDialog(null, (int) kroner + " kroner tilsvarer " + (int) kronerTilEuro(kroner) + " euro");
			break;
		case "yen":
			JOptionPane.showMessageDialog(null, (int) kroner + " kroner tilsvarer " + (int) kronerTilYen(kroner) + " yen");
			break;
		default:
			JOptionPane.showMessageDialog(null, "Ukjent valuta");
			break;
		}
	}
}
