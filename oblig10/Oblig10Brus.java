package oblig10;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Oblig10Brus implements Comparable<Oblig10Brus> {

	private double poengsum;
	private String brusmerke;

	Component[] scoreInputDialog = new Component[4];

	public Oblig10Brus() {
		brusmerke = JOptionPane.showInputDialog(null, "Skriv inn brusmerke", "Legg til ny brus", JOptionPane.INFORMATION_MESSAGE);

		if (brusmerke == null)
			throw new IllegalArgumentException();

		promptScore();
	}

	public Oblig10Brus(String brusmerke, double poengsum) {
		this.brusmerke = brusmerke;
		this.poengsum = poengsum;
	}

	private void promptScore() throws IllegalArgumentException {
		scoreInputDialog[0] = new JLabel(
				"<html>Skriv inn opptil tre forskjellige poengsummer. <br><br>Gjennomsnittet av poengsummene du har skrevet <br>inn avgjør brusens poengsum.<html>");

		for (int i = 1; i < scoreInputDialog.length; i++) {
			scoreInputDialog[i] = new JTextField();
		}

		int valg = JOptionPane.showConfirmDialog(null, scoreInputDialog, "Gi brusen poengsummer", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

		if (valg == JOptionPane.OK_OPTION) {
			int inputs = 0;
			double sum = 0;

			for (Component component : scoreInputDialog) {
				if (!(component instanceof JTextField))
					continue;

				String fieldText = ((JTextField) component).getText();

				if (fieldText.isEmpty())
					continue;

				try {
					sum += Double.parseDouble(fieldText.replaceAll(",", "."));
				} catch (NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "\"" + fieldText + "\" er ikke et gyldig tall!");
					promptScore();
				} finally {
					inputs++;
				}
			}

			if (inputs < 1) {
				JOptionPane.showMessageDialog(null, "Du må skrive inn minst ett gyldig tall");
				promptScore();
			}

			poengsum = Double.parseDouble(Oblig10.df.format(sum / (double) inputs).replace(',', '.'));
		} else {
			throw new IllegalArgumentException();
		}

	}

	@Override
	public String toString() {
		return getText();
	}

	public String getText() {
		return brusmerke + " - " + poengsum + " poeng";
	}

	public String getBrand() {
		return brusmerke;
	}

	public Double getScore() {
		return poengsum;
	}

	@Override
	public int compareTo(Oblig10Brus b) {
		return b.getScore().compareTo(getScore());
	}
}
