package oblig9;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Oblig9GrafikkElement {

	protected int x, y, width, height;
	protected Rectangle bounds;
	private Color color;

	public Oblig9GrafikkElement(int x, int y, int width, int height, Color color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;

		setBounds();
	}

	public void drawMe(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}

	public void moveRandom(int maxWidth, int maxHeight) {
		Random rand = new Random();

		int posx = rand.nextInt(maxWidth);
		int posy = rand.nextInt(maxHeight);

		setPos(posx - (posx % width), posy - (posy % height));
	}

	public void move(int dx, int dy) {
		x += dx;
		y += dy;

		setBounds();
	}

	public void setPos(int x, int y) {
		this.x = x;
		this.y = y;

		setBounds();
	}

	protected void setBounds() {
		bounds = new Rectangle(x, y, width, height);
	}
}
