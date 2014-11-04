package oblig9;

import java.awt.Color;

public class Oblig9SnakeElement extends Oblig9GrafikkElement{
	
	public Oblig9SnakeElement(int x, int y, int width, int height, Color color){
		super(x, y, width, height, color);
	}
	
	public void moveTo(Oblig9SnakeElement se){
		setPos(se.x, se.y);
	}
}
