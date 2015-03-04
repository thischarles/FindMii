import java.util.ArrayList;

public class Level {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public Level() {
		//read file
		//add enemies
		enemies.add(new Enemy(Enemy.Type.GHOST, 7));
	}
	
	public int getChoices() {
		return enemies.size();
	}

	public void attack(Mii hero, int choice) {
		int heroDamage = hero.attack(enemies.get(choice));
		enemies.get(choice).damage(heroDamage);
	}
	
	public void magic(Mii hero, int choice) {
		
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public boolean victory() {
		return enemies.size() == 0;
	}
}
