import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

public class Oblig4 {

	public static int correctAnswer, userGuess, guesses, highScore;

	public static Random rand = new Random();

	public static void init() {
		correctAnswer = rand.nextInt(10);
		guesses = 0;
		highScore = getHighScore();
	}

	public static void setHighScore(int n) {
		BufferedWriter out;

		try {
			out = new BufferedWriter(new FileWriter("highscore.txt"));

			out.write(Integer.toString(n));

			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getHighScore() {
		File file = new File("highscore.txt");
		BufferedReader in;
		String read = "";

		try {
			if (!file.exists()) {
				file.createNewFile();
				setHighScore(0);
			}

			in = new BufferedReader(new FileReader(file));

			read = in.readLine();

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return Integer.parseInt(read);
	}

	public static void main(String[] args) {
		init();

		while (true) {
			guesses++;

			userGuess = Integer.parseInt(JOptionPane.showInputDialog("Gjett et tall mellom 1 og 10."));
			if (userGuess != correctAnswer) {
				JOptionPane.showMessageDialog(null, "Du har gjettet feil.");

				if (userGuess < correctAnswer) {
					JOptionPane.showMessageDialog(null, "Riktig tall er høyere.");
				} else {
					JOptionPane.showMessageDialog(null, "Riktig tall er lavere.");
				}
			} else {
				if (highScore == 0 || guesses < highScore) {
					highScore = guesses;
					JOptionPane.showMessageDialog(null, "Du har gjettet riktig. Du gjettet " + guesses + " ganger. Ny high score!");
					setHighScore(highScore);
				} else {
					JOptionPane.showMessageDialog(null, "Du har gjettet riktig. Du gjettet " + guesses + " ganger. High score: " + highScore);
				}

				init();
			}

		}

	}
}
