package oblig13;

import java.io.Serializable;

public abstract class Oblig13Vare implements Serializable {
	private static final long serialVersionUID = 1L;

	protected String name;
	protected float price;

	public Oblig13Vare(String name, float price) {
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public float getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return name + " - " + price + " NOK";
	}
}
