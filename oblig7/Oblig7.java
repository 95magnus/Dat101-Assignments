package oblig7;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class Oblig7 extends JFrame implements MouseMotionListener {
	private static final long serialVersionUID = 1L;

	private final int FRAME_WIDTH = 640, FRAME_HEIGHT = 480, BREDDE = 10, HOYDE = 10;

	private ArrayList<Point> punkter = new ArrayList<Point>();
	private ArrayList<Point[]> lines = new ArrayList<Point[]>();

	private Random rand = new Random();

	public Oblig7() {
		super("Oblig7");

		setVisible(true);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocationRelativeTo(null);
		addMouseMotionListener(this);

		for (int i = 0; i < 4; i++) {
			punkter.add(new Point(rand.nextInt(FRAME_WIDTH), rand.nextInt(FRAME_HEIGHT)));
		}
	}

	private void calculatePath() {
		Point current = punkter.get(0), nearest = null;
		ArrayList<Point> remaining = new ArrayList<Point>(punkter);
		remaining.remove(current);

		lines.clear();

		while (!remaining.isEmpty()) {
			double minDist = -1;

			for (Point p : remaining) {
				if (minDist == -1 || current.distance(p) < minDist) {
					minDist = current.distance(p);
					nearest = p;
				}
			}

			lines.add(new Point[] { current, nearest });
			remaining.remove(current);
			current = nearest;
		}
	}

	private void drawLine(Graphics g, Point p1, Point p2) {
		g.setColor(new Color(0));
		g.drawLine(p1.x + (BREDDE / 2), p1.y + (HOYDE / 2), p2.x + (BREDDE / 2), p2.y + (HOYDE / 2));
	}

	private void addPoint(int x, int y) {
		punkter.add(new Point(x, y));
		calculatePath();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (Point p : punkter) {
			g.setColor(new Color(rand.nextInt(0xffffff)));
			g.fillOval(p.x, p.y, BREDDE, HOYDE);
		}

		for (Point[] line : lines) {
			drawLine(g, line[0], line[1]);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		addPoint(e.getX(), e.getY());
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}
}
