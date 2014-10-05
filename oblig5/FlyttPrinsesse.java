import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class FlyttPrinsesse implements ActionListener {
	private Character character;
	private Random rand = new Random();

	public FlyttPrinsesse(Character character) {
		this.character = character;
	}

	private void flyttPrinsesen() {
		character.x += (rand.nextInt(200) - 100);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		flyttPrinsesen();
	}
}
