package oblig14;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.*;

public class Oblig14 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private DefaultListModel<Integer> model1 = new DefaultListModel<Integer>();
	private DefaultListModel<Integer> model2 = new DefaultListModel<Integer>();
	private DefaultListModel<Integer> model3 = new DefaultListModel<Integer>();

	private JList<Integer> list1 = new JList<Integer>();
	private JList<Integer> list2 = new JList<Integer>();
	private JList<Integer> list3 = new JList<Integer>();

	private ArrayList<Integer> numbers = new ArrayList<Integer>();
	private HashSet<Integer> uniqueNumbers = new HashSet<Integer>();
	private TreeSet<Integer> sortedUniqueNumbers = new TreeSet<Integer>();

	private JPanel descriptions = new JPanel();
	private JPanel numberPanes = new JPanel();
	private JPanel buttons = new JPanel();

	private Random rand = new Random();

	public Oblig14() {
		super("Oblig14 - Lotto");

		setSize(320, 320);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		descriptions.setLayout(new GridLayout());
		numberPanes.setLayout(new GridLayout());
		buttons.setLayout(new FlowLayout());

		list1.setModel(model1);
		list2.setModel(model2);
		list3.setModel(model3);

		descriptions.add(new JLabel("Trukket lottotall"));
		descriptions.add(new JLabel("Unike lottotall"));
		descriptions.add(new JLabel("Sorterte lottotall"));

		numberPanes.add(new JScrollPane(list1));
		numberPanes.add(new JScrollPane(list2));
		numberPanes.add(new JScrollPane(list3));

		addButton("Trekk tall", "draw");
		addButton("Sjekk tall", "won");

		add(descriptions, BorderLayout.PAGE_START);
		add(numberPanes, BorderLayout.CENTER);
		add(buttons, BorderLayout.PAGE_END);

		setVisible(true);
		drawNumbers();
		
	}
	
	private void drawNumbers(){
		numbers.clear();
		model1.removeAllElements();

		uniqueNumbers.clear();
		model2.removeAllElements();

		sortedUniqueNumbers.clear();
		model3.removeAllElements();

		for (int n = 0; n < 10; n++) {
			int num = rand.nextInt(20) + 1;

			numbers.add(num);
			model1.addElement(num);
		}

		uniqueNumbers.addAll(numbers);
		sortedUniqueNumbers.addAll(uniqueNumbers);

		for (Integer n : uniqueNumbers)
			model2.addElement(n);

		for (Integer n : sortedUniqueNumbers)
			model3.addElement(n);
	}

	private boolean checkNumbers(ArrayList<Integer> nums) {
		HashSet<Integer> temp = new HashSet<Integer>(nums);

		if (temp.isEmpty() || uniqueNumbers.isEmpty())
			return false;

		return uniqueNumbers.hashCode() == temp.hashCode();
	}

	private void addButton(String name, String id) {
		JButton button = new JButton(name);

		button.setName(id);
		button.addActionListener(this);

		buttons.add(button);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonID = ((JButton) e.getSource()).getName();

		switch (buttonID) {
			case "draw":
					drawNumbers();
				break;

			case "won":
				JTextField input = new JTextField(); 	

				Object[] content = { "Skriv inn dine lottotall (separert med mellomrom):", input };

				int option = JOptionPane.showConfirmDialog(null, content, "Sjekk resultat", JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					String[] inputs = input.getText().split(" ");
					ArrayList<Integer> numList = new ArrayList<Integer>();

					for (String str : inputs) {
						int n = -1;

						try {
							n = Integer.parseInt(str);
						} catch (NumberFormatException nfe) {

						}

						if (n != -1)
							numList.add(n);
					}

					JOptionPane.showMessageDialog(null, checkNumbers(numList) ? "Gratulerer! Du vant!" : "Beklager, du vant ikke.");
				}
				break;
		}
	}

	public static void main(String[] args) {
		new Oblig14();
	}
}
