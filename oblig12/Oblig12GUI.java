package oblig12;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Oblig12GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public enum Difficulty {
		VELDIG_LETT(1.0f), LETT(0.6f), MEDIUM(0.4f), VANSKELIG(0.2f);

		private float factor;

		private Difficulty(float factor) {
			this.factor = factor;
		}

		public float getFactor() {
			return factor;
		}

		@Override
		public String toString() {
			String lower = name().toLowerCase();

			return lower.substring(0, 1).toUpperCase() + lower.substring(1).replaceAll("_", " ");
		}
	}

	public ArrayList<JTextField> sudokuBoard = new ArrayList<JTextField>();
	public Difficulty difficulty = Difficulty.LETT;

	private JMenuBar menuBar = new JMenuBar();
	private JLabel difficultyLabel = new JLabel("Vansklighetsgrad: " + difficulty);
	private JMenu menu = new JMenu("Fil");
	private JMenu difficultyMenu = new JMenu("Vansklighetsgrad");

	private Random rand = new Random();

	public Oblig12GUI() {
		super("Sudoku");

		setSize(640, 640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(9, 9));

		for (int i = 0; i < 9 * 9; i++) {
			JFormattedTextField newField = null;

			try {
				MaskFormatter mask = new MaskFormatter("#");
				mask.setInvalidCharacters("0");
				newField = new JFormattedTextField(mask);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}

			newField.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {
					JTextField field = (JTextField) e.getComponent();
					field.setCaretPosition(0);
				}

				@Override
				public void mouseExited(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {}

				@Override
				public void mouseClicked(MouseEvent e) {}
			});

			newField.setFont(new Font("SansSerif", Font.BOLD, 20));
			newField.setHorizontalAlignment(JTextField.CENTER);
			newField.setText("");

			sudokuBoard.add(newField);
			add(newField);
		}

		menu.add(newMenuItem("Nytt spil", "new"));
		menu.add(newMenuItem("Slett", "delete"));
		initDifficultyMenu();
		menu.add(difficultyMenu);

		menuBar.add(menu);
		menuBar.add(difficultyLabel);

		setJMenuBar(menuBar);
		setVisible(true);

		newGame();
	}

	private void initDifficultyMenu() {
		for (final Difficulty dif : Difficulty.values()) {
			JMenuItem item = new JMenuItem(dif.toString());

			item.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (dif == difficulty)
						return;

					difficulty = dif;
					difficultyLabel.setText("Vansklighetsgrad: " + difficulty);

					newGame();
				}
			});

			difficultyMenu.add(item);
		}
	}

	private void newGame() {
		for (JTextField tf : sudokuBoard) {
			tf.setText("");
			tf.setEditable(true);
		}

		int fieldCount = 0;
		ArrayList<JTextField> initalizedFields = new ArrayList<JTextField>();
		ArrayList<Integer> unusedValues = new ArrayList<Integer>();

		for (int i = 0; i < 9; i++) {
			for (int n = 1; n <= 9; n++) {
				unusedValues.add(n);
			}
		}

		JTextField[][] boardArray2D = getBoard2DArray();

		while (fieldCount < sudokuBoard.size() * difficulty.getFactor()) {
			JTextField randField = sudokuBoard.get(rand.nextInt(sudokuBoard.size()));

			if (!initalizedFields.contains(randField)) {
				int randValueIndex = rand.nextInt(unusedValues.size());
				int randValue = unusedValues.get(randValueIndex);

				for (int x = 0; x < 9; x++) {
					for (int y = 0; y < 9; y++) {
						int value = 0;

						try {
							value = Integer.parseInt(boardArray2D[x][y].getText());

							if (value == 0)
								throw new NumberFormatException();
						} catch (NumberFormatException nfe) {
							continue;
						}

						if (x == randValueIndex % 9 && randValue == value) {
							randValue = rand.nextInt(9) + 1;
							// x = 0;
						}
					}
				}

				randField.setText(randValue + "");
				randField.setEditable(false);

				unusedValues.remove(randValueIndex);
				initalizedFields.add(randField);
				fieldCount++;
			}
		}
	}

	private JTextField[][] getBoard2DArray() {
		Object[] array1D = sudokuBoard.toArray();
		JTextField[][] array2D = new JTextField[9][9];

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				array2D[x][y] = (JTextField) array1D[x + (y * 9)];
			}
		}

		return array2D;
	}

	private JMenuItem newMenuItem(String text, String id) {
		JMenuItem item = new JMenuItem(text);

		item.addActionListener(this);
		item.setName(id);

		return item;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String menuItemID = ((JMenuItem) e.getSource()).getName();

		switch (menuItemID) {
			case "new":
				newGame();
				break;

			case "delete":
				for (JTextField tf : sudokuBoard) {
					if (tf.isEditable()) {
						tf.setText("");
					}
				}
				break;
		}
	}

	public static void main(String[] args) {
		new Oblig12GUI();
	}
}
