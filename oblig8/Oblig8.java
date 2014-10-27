package oblig8;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Oblig8 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	private boolean computer = false;

	private int[] playerSelections = new int[] { -1, -1 };
	private int[] playerWins = new int[2];

	private String[] options = new String[] { "stein", "saks", "papir" };

	private ArrayList<JButton> buttons = new ArrayList<JButton>();

	private Random rand = new Random();

	public Oblig8() {
		super("Stein-saks-papir spill");

		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(2, 2));

		add(new JLabel("Spiller 1"));
		add(new JLabel("Spiller 2"));

		int modeSelection = JOptionPane.showOptionDialog(null, "Vil du spille mot en annen person eller datamaskinen?", "Velg motspiller", JOptionPane.OK_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, new String[] { "2 spillere", "Datamaskin" }, 0);

		switch (modeSelection) {
			case 0:
				addButton(1);
				addButton(2);
				buttons.get(1).setVisible(false);
				break;

			case 1:
				computer = true;
				addButton(1);
				break;

			default:
				System.exit(0);
				break;
		}

		setVisible(true);

	}

	private int getWinner(int p1Selection, int p2Selection) {
		if (p1Selection == p2Selection)
			return 0; // Draw
		else if (p2Selection == (p1Selection + 1) % 3)
			return 1; // P1 winner
		return 2; // P2 winner
	}

	private void resetPlayerSelection() {
		for (int i = 0; i < playerSelections.length; i++) {
			playerSelections[i] = -1;
		}
	}

	private void addButton(int player) {
		JButton button = new JButton("Valg spiller " + player);
		buttons.add(button);

		add(button);
		button.addActionListener(this);
		button.setName(player + "");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int player = Integer.parseInt(((JButton) e.getSource()).getName());
		int playerIndex = player - 1;

		JOptionPane.showMessageDialog(null, "Spiller " + player + " har trykket", "Knapp ble trykket", JOptionPane.INFORMATION_MESSAGE);

		playerSelections[playerIndex] = JOptionPane.showOptionDialog(null, "Velg stein saks eller papir", "Valg spiller " + player, JOptionPane.OK_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, new String[] { "Stein", "Saks", "Papir" }, 0);

		if (playerSelections[playerIndex] != -1) {
			if (computer) {
				playerSelections[1] = rand.nextInt(3);
				JOptionPane.showMessageDialog(null, "Datamaskinen valgte " + options[playerSelections[1]], "Datamaskin valg", JOptionPane.INFORMATION_MESSAGE);
			} else {
				for (JButton b : buttons) {
					b.setVisible(!b.isVisible());
				}
			}
		}

		if (playerSelections[0] != -1 && playerSelections[1] != -1) {
			if (getWinner(playerSelections[0], playerSelections[1]) == 0) {
				JOptionPane.showMessageDialog(null, "Uavgjort");
			} else {
				playerWins[getWinner(playerSelections[0], playerSelections[1]) - 1]++;

				String winner = (getWinner(playerSelections[0], playerSelections[1]) == 1) ? "Spiller 1" : (buttons.size() == 1) ? "Datamaskinen" : "Spiller 2";
				JOptionPane.showMessageDialog(null, winner + " vant!", "Resultat", JOptionPane.INFORMATION_MESSAGE);
				JOptionPane.showMessageDialog(null, "Statistikk:\n\nSpiller 1:    " + playerWins[0] + " seier(e)\n" + ((computer) ? "Datamaskin" : "Spiller 2") + ":    "
						+ playerWins[1] + " seier(e)", "Spill statistikk", JOptionPane.INFORMATION_MESSAGE);
			}

			resetPlayerSelection();
		}
	}
}
