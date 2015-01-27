package oblig13;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;

import javax.swing.*;

public class Oblig13GUI extends JFrame implements ActionListener, WindowListener {
	private static final long serialVersionUID = 1L;

	private DefaultListModel<Oblig13Vare> model = new DefaultListModel<Oblig13Vare>();
	private JList<Oblig13Vare> list = new JList<Oblig13Vare>(model);
	private JScrollPane listPane = new JScrollPane(list);
	private JPanel buttonPane = new JPanel();

	public Oblig13GUI() {
		super("Oblig13");

		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		addWindowListener(this);

		add(listPane, BorderLayout.CENTER);

		addButton("Lagre beholdning", "save");
		addButton("Åpne beholdning", "open");
		addButton("Legg til vare", "add");
		addButton("Selg vare", "sell");
		add(buttonPane, BorderLayout.PAGE_END);

		setVisible(true);

		deserialize();
	}

	private void addButton(String name, String id) {
		JButton button = new JButton(name);

		button.setName(id);
		button.addActionListener(this);
		buttonPane.add(button);
	}

	private void serialize() {
		try {
			FileOutputStream fileOut = new FileOutputStream("varebeholdning.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(model.toArray());
			out.close();
			fileOut.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private void deserialize() {
		Object[] temp = null;

		try {
			FileInputStream fileIn = new FileInputStream("varebeholdning.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			temp = (Object[]) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			System.out.println("Could't find");
		}

		model.removeAllElements();

		for (Object vare : temp)
			model.addElement((Oblig13Vare) vare);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonID = ((JButton) e.getSource()).getName();

		switch (buttonID) {
			case "save":
				serialize();
				break;

			case "open":
				deserialize();
				break;

			case "add":
				JTextField name = new JTextField();
				JTextField price = new JTextField();
				Object[] content = { "Vare navn:", name, "Pris:", price };

				int option = JOptionPane.showConfirmDialog(null, content, "Legg til ny vare", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					try {
						model.addElement(new Oblig13VareCustom(name.getText(), Float.parseFloat(price.getText().replace(",", "."))));
					} catch (NumberFormatException nfe) {
						model.addElement(new Oblig13VareCustom(name.getText(), 0));
					}
				}
				break;

			case "sell":
				model.removeElement(list.getSelectedValue());
				break;
		}
	}

	public static void main(String[] args) {
		new Oblig13GUI();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		serialize();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}
}
