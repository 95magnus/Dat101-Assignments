import javax.swing.Timer;

public class Oblig5 {
	public static void main(String[] args) {
		Game game = new Game();
		game.setSize(800, 500);
		game.setLocationRelativeTo(null);

		Character background = new Character();
		Character hero = new Character();
		Character princess = new Character();

		FlyttPrinsesse fp = new FlyttPrinsesse(princess);
		Timer timer = new Timer(1000, fp);
		timer.start();

		background.setImage("http://hugoware.net/resource/images/misc/game-background.jpg");
		hero.setImage("http://upload.wikimedia.org/wikipedia/en/9/99/MarioSMBW.png");
		princess.setImage("http://upload.wikimedia.org/wikipedia/en/d/d5/Peach_(Super_Mario_3D_World).png");

		game.addCharacter(background, "background");
		game.addCharacter(hero, "hero");
		game.addCharacter(princess, "princess");

		hero.x = 100;
		hero.y = 350;
		hero.resize(0.5);

		princess.x = 450;
		princess.y = 225;
		princess.resize(0.35);

		for (int i = 0; i < princess.y - 100; i++) {
			hero.x++;
			game.drawStuff();
		}

		while (princess.y > 0) {
			princess.y--;
			game.drawStuff();
		}

		for (int i = 0; i < 10; i++) {
			hero.jump();
			game.drawStuff();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
