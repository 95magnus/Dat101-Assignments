package oblig9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Oblig9SnakeGame extends JFrame implements KeyListener, ActionListener {
	private static final long serialVersionUID = 1L;

	public static final int SNAKE_SIZE = 20;

	private ArrayList<Oblig9SnakeElement> snake = new ArrayList<Oblig9SnakeElement>();
	private Eple eple = new Eple(100, 200, 20, 20, new Color(0xff0000));

	private Timer timer;

	public Oblig9SnakeGame() {
		super("Snake Game");

		setVisible(true);
		setSize(640, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);

		snake.add(new Oblig9SnakeElement(getWidth() / 2, getHeight() / 2, SNAKE_SIZE, SNAKE_SIZE, new Color(0x00ff00)));

		timer = new Timer(10000, this);
		timer.start();

		drawStuff();
		repaint();
	}

	public void drawStuff() {
		Graphics g = this.getGraphics();
		g.setColor(new Color(0));
		g.fillRect(0, 0, getWidth(), getHeight());

		if (snake.get(0).bounds.intersects(eple.bounds)) {
			System.out.println("Snake spiste eple");

			snake.add(new Oblig9SnakeElement(snake.get(0).x, snake.get(0).y, 20, 20, new Color(0x00ff00)));
			eple.moveRandom(getWidth(), getHeight());
			timer.restart();
		}

		for (Oblig9SnakeElement se : snake) {
			se.drawMe(g);
		}

		eple.drawMe(g);
	}

	public void moveSnake(int dx, int dy) {
		snake.get(0).move(dx, dy);
		
		for (int i = 1; i < snake.size(); i++){
			snake.get(i).moveTo(snake.get(i-1));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		eple.moveRandom(getWidth(), getHeight());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			case KeyEvent.VK_UP:
				moveSnake(0, -SNAKE_SIZE);
				break;
			case KeyEvent.VK_DOWN:
				moveSnake(0, SNAKE_SIZE);
				break;
			case KeyEvent.VK_LEFT:
				moveSnake(-SNAKE_SIZE, 0);
				break;
			case KeyEvent.VK_RIGHT:
				moveSnake(SNAKE_SIZE, 0);
				break;
		}

		drawStuff();
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}