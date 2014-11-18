import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import com.gtranslate.Audio;
import com.gtranslate.Language;
import com.gtranslate.Translator;

/**
 * Norsk bokmål til engelsk oversetter
 * 
 * Google Text to Speech API
 * https://code.google.com/p/java-google-translate-text-to-speech/
 * 
 * @author Magnus Solhaug
 * 
 */

public class Oblig11 extends JFrame implements DocumentListener, ActionListener {
	private static final long serialVersionUID = 1L;

	private static final Translator translator = Translator.getInstance();
	private static final Audio audio = Audio.getInstance();

	private JTextPane inputField = new JTextPane();
	private JTextPane translatedField = new JTextPane();
	private JButton play = new JButton("Spill av oversettet tekst");

	public Oblig11() {
		super("Norsk - Engelsk oversetter");

		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		inputField.getDocument().addDocumentListener(this);
		translatedField.setContentType("text/html");

		play.setName("play");
		play.addActionListener(this);

		JPanel textPane = new JPanel();
		textPane.setLayout(new GridLayout(1, 2));
		textPane.add(new JLabel("Norsk bokmål"));
		textPane.add(new JLabel("English"));

		JPanel inputPane = new JPanel();
		inputPane.setLayout(new GridLayout(1, 2));
		inputField.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		translatedField.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		translatedField.setEditable(false);
		inputPane.add(inputField);
		inputPane.add(translatedField);

		add(textPane, BorderLayout.PAGE_START);
		add(inputPane, BorderLayout.CENTER);
		add(play, BorderLayout.PAGE_END);

		setVisible(true);
	}

	private void updateTextField(DocumentEvent e) {
		Document doc = e.getDocument();

		String text = "";

		try {
			text = doc.getText(0, doc.getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		translatedField.setText(translatedText(text));
	}

	private String translatedText(String text) {
		String[] wordsInText = text.split(" ");
		StringBuilder textBuffer = new StringBuilder();

		for (String word : wordsInText) {
			String wordToAppend = translator.translate(word, Language.NORWEGIAN, Language.ENGLISH);

			/*
			 * translator.detect() is bugged in v1.0 of Google Text to speech API
			 * Temporary non-ideal solution
			 */
			if (word.equals(wordToAppend)){
				textBuffer.append("<font color=\"red\">" + wordToAppend + "</font> ");
			} else {
				textBuffer.append(wordToAppend + " ");
			}
		}

		return textBuffer.toString();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonName = ((JButton) e.getSource()).getName();

		switch (buttonName) {
			case "play":
				String fieldText = getPlainText(translatedField.getText());

				if (fieldText.isEmpty()) {
					System.out.println("Failed to play audio: Empty translation field");
					break;
				}

				try {
					audio.play(audio.getAudio(fieldText, Language.ENGLISH));
				} catch (Exception e1) {
					System.out.println("Failed to play audio: " + e1.getMessage());
				}
				break;
		}
	}

	private String getPlainText(String htmlText) {
		String[] allWords = htmlText.replaceAll("<[^>]+>", "").replaceAll("\\s+", " ").split(" ");
		StringBuilder plainText = new StringBuilder();

		for (String word : allWords)
			plainText.append(word + " ");

		return plainText.toString();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateTextField(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateTextField(e);
	}
}
