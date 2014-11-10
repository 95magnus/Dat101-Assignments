package oblig10;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class Oblig10 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public static final DecimalFormat df = new DecimalFormat("0.0");
	public static final Random rand = new Random();

	private final String IMAGE_URL = "http://us.cdn4.123rf.com/168nwm/irochka/irochka0908/irochka090800180/5403222-ice-cube-droped-in-cola-glass-and-cola-splashing.jpg";

	private DefaultListModel<Oblig10Brus> model = new DefaultListModel<Oblig10Brus>();
	private JList<Oblig10Brus> list = new JList<Oblig10Brus>(model);
	private JLabel besteBrus = new JLabel();
	private JPanel buttonPanel = new JPanel();
	private JPanel sidePanel = new JPanel();

	public Oblig10() {
		super("Julebrus rangering");

		setSize(380, 540);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
		buttonPanel.setLayout(new GridLayout(3, 2));

		/*
		 * for (int i = 0; i < 30; i++) { addToList("Test " + i,
		 * rand.nextInt(10) + rand.nextDouble()); }
		 */

		try {
			sidePanel.add(new JLabel(new ImageIcon(new URL(IMAGE_URL))), BorderLayout.WEST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		sidePanel.add(besteBrus);
		add(new JScrollPane(list), BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.PAGE_END);
		add(sidePanel, BorderLayout.LINE_START);

		addButton("Legg til ny brus", "add");
		addButton("Slett fra listen", "delete");
		addButton("Søk etter brus", "search");
		addButton("Finn beste brus", "best");
		addButton("Sorter listen", "sort");
		addButton("Lagre listen som csv-fil", "save");

		findBest();

		setVisible(true);
	}

	private void addButton(String buttonText, String buttonID) {
		final JButton button = new JButton(buttonText);
		button.setName(buttonID);
		button.addActionListener(this);
		buttonPanel.add(button);
	}

	private void addToList(String name, double score) {
		double formattedScore = Double.parseDouble(df.format(score).replace(',', '.'));

		model.addElement(new Oblig10Brus(name, formattedScore));
		sort();
		findBest();
	}

	private void sort() {
		List<Oblig10Brus> sortList = new ArrayList<Oblig10Brus>();

		for (Object obj : model.toArray()) {
			sortList.add((Oblig10Brus) obj);
		}

		Collections.sort(sortList);
		model.removeAllElements();

		for (Oblig10Brus br : sortList) {
			model.addElement(br);
		}
	}

	private void findBest() {
		Oblig10Brus best = null;

		for (int i = 0; i < model.getSize(); i++) {
			if (best == null || best.getScore() <= model.get(i).getScore()) {
				best = model.get(i);
			}
		}

		if (best == null) {
			besteBrus.setText("<html><h1>Beste brus:</h1><h3 style=\"width:100px\">Listen er tom<br>Trykk på Legg til ny brus knappen for å legge til brus</h3></html>");
		} else {
			besteBrus.setText("<html><h1>Beste brus:</h1><h3 style=\"width:100px\">" + best.getBrand() + "</h3><h3 style=\"color:#008080\">" + best.getScore()
					+ " poeng</h3></html>");
		}
	}

	public void writeListToFile() {
		/*
		 * String directory, fileName; final JFileChooser fileChooser = new
		 * JFileChooser(new File("."));
		 * 
		 * fileChooser.setDialogTitle("Select folder to \"save\" file");
		 * fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		 * fileChooser.setAcceptAllFileFilterUsed(false);
		 * 
		 * if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		 * { directory = fileChooser.getCurrentDirectory().getAbsolutePath();
		 * fileName = fileChooser.getSelectedFile().getName();
		 * 
		 * System.out.println("Saving file to: " + directory + "\\" + fileName +
		 * ".csv"); } else { System.out.println("Failed to save"); return; }
		 */

		try {
			// FileWriter writer = new FileWriter(directory + "\\" + fileName +
			// ".csv");
			FileWriter writer = new FileWriter("Brus_rangering.csv");

			writer.append("Brusnavn;Poengsum\n");

			for (Object brusTekst : model.toArray()) {
				String[] values = brusTekst.toString().split(" - ");
				writer.append(values[0] + ";" + values[1].replace('.', ',').replace(" poeng", "") + "\n");
			}

			writer.flush();
			writer.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Kunne ikke skrive til fil, årsak: " + e.getMessage(), "Feilmelding", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonName = ((JButton) e.getSource()).getName();

		switch (buttonName) {
			case "add":
				try {
					model.addElement(new Oblig10Brus());
				} catch (Exception c) {}

				sort();
				findBest();
				break;

			case "delete":
				for (Object element : list.getSelectedValuesList())
					model.removeElement(element);

				sort();
				findBest();
				break;

			case "save":
				writeListToFile();
				break;

			case "sort":
				sort();
				break;

			case "search":
				String searchFor = JOptionPane.showInputDialog(null, "Skriv inn navnet på brusen du ønsker å finne", "Søk etter brus", JOptionPane.INFORMATION_MESSAGE);

				for (Object obj : model.toArray()) {
					Oblig10Brus br = (Oblig10Brus) obj;

					if (br.getBrand().equalsIgnoreCase(searchFor)) {
						JOptionPane.showMessageDialog(null, "Resultat av søket: \n" + br, "Søk resultat", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}

				if (searchFor != null)
					JOptionPane.showMessageDialog(null, "Fant ikke \"" + searchFor + "\" i listen", "Søk resultat", JOptionPane.ERROR_MESSAGE);
				break;

			case "best":
				findBest();
				break;
		}
	}
}
