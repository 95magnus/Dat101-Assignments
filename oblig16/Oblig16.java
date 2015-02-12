package oblig16;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.swing.*;

public class Oblig16 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static final String EXCHANGE_RATE_URL = "https://www.dnb.no/portalfront/datafiles/miscellaneous/csv/kursliste_ws.xml";

	private JTextField input;
	private JTextField result = new JTextField();
	private JComboBox<String> fromCurrencies = new JComboBox<String>();
	private JComboBox<String> toCurrencies = new JComboBox<String>();

	private JPanel buttomPane = new JPanel();

	public Oblig16() {
		super("Oblig15 - Valutakonverterer enhetstest");
		initFrame();

		addCurrency("NOK", "EUR", "USD", "JPY");
	}

	private void initFrame() {
		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(3, 3));

		buttomPane.setLayout(new BorderLayout());

		input = new JFormattedTextField();
		input.setSize(200, 30);

		result.setEditable(false);
		result.setSize(200, 30);

		add(new JLabel("Fra"));
		add(input);
		add(fromCurrencies);
		add(new JLabel("To"));
		add(toCurrencies);
		add(result);
		addButton(buttomPane, "Konverter", "convert");

		add(buttomPane, BorderLayout.PAGE_END);

		setVisible(true);
	}

	public static double getRate(String toCode, String fromCode) {
		try {
			URL url = new URL("http://quote.yahoo.com/d/quotes.csv?f=l1&s=" + fromCode + toCode + "=X");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String line = reader.readLine();
			if (line.length() > 0) {
				return Double.parseDouble(line);
			}
			reader.close();
		} catch (IOException | NumberFormatException e) {
			System.out.println("Could't load exchange rates");
			System.out.println(e.getMessage());
		}

		return 0;
	}

	public static double konverterTilEuro(double value, String fromCode) {
		return value * getRate("EUR", fromCode);
	}

	public static double konverterFraEuro(double value, String toCode) {
		return value * getRate(toCode, "EUR");
	}

	public static double konverter(double value, String toCode, String fromCode) {
		return konverterFraEuro(konverterTilEuro(value, fromCode), toCode);
	}

	private void addCurrency(String... codes) {
		for (String code : codes) {
			fromCurrencies.addItem(code);
			toCurrencies.addItem(code);
		}
	}

	private void addButton(JComponent comp, String name, String id) {
		JButton button = new JButton(name);

		button.setName(id);
		button.addActionListener(this);

		comp.add(button);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id = ((JComponent) e.getSource()).getName();

		switch (id) {
			case "convert":
				String from = String.valueOf(fromCurrencies.getSelectedItem());
				String to = String.valueOf(toCurrencies.getSelectedItem());

				double inputValue = 0;
				try {
					inputValue = Double.parseDouble(input.getText());
				} catch (NumberFormatException nfe) {
					System.out.println("Invalid input");
				}
				double convertedValue = konverter(inputValue, to, from);

				result.setText(String.valueOf(convertedValue == 0.0 ? "???" : String.format("%.2f", convertedValue)) + " " + to);
				break;
		}
	}

	public static void main(String[] args) {
		new Oblig16();
	}

}
