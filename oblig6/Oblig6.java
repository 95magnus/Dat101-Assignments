package oblig6;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.*;

public class Oblig6 extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	public Oblig6() {
		super("Julekalender");
		init();
	}

	private void init() {
		setSize(220, 450);

		try {
			setContentPane(new JLabel(new ImageIcon(new URL("http://bgcsonoma.org/home/boysgirlsclub/images/events/christmastree.png"))));
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(new FlowLayout(FlowLayout.CENTER));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		add(new JLabel("Velkommen til årets julekalender"));

		for (int i = 1; i <= 24; i++) {
			JButton button = new JButton("Luke: " + i);
			
			add(button);
			button.addActionListener(this);
			button.setName(i + "");
		}

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonNum = ((JButton) e.getSource()).getName();

		JOptionPane.showMessageDialog(rootPane, "Nå er det " + (24 - Integer.parseInt(buttonNum)) + " dager igjen til jul");
	}
}
