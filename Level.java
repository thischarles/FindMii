import java.util.ArrayList;
import java.util.Iterator;

public class Level {
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	public static enum Status {
		NEUTRAL,
		DARK,
		BRIGHT;
	}
	
	private Status roomStatus;
	
	public Level() {
		//read file
		//add enemies
		enemies.add(new Enemy(Enemy.Type.GHOST));

		roomStatus = Status.NEUTRAL;
	}
	
	public int getChoices() {
		return enemies.size();
	}

	public void attack(Mii hero, int choice) {
		System.out.println(hero.getName() + " attacks.");
		System.out.println("And attacks again.");
		System.out.println("And attacks once more.");
		//handle orange/invigorate
		System.out.println("Your hero gets a bonus attack!");
		
		System.out.println("Critical hit");
		
		enemies.get(choice).damage(hero.getLevel());
		
		checkEnemies();
	}
	
	public Mii.Color magic(Mii hero) {
		System.out.println(hero.getName() + " uses " + hero.getClass() + " magic.");
		Iterator<Enemy> itr = enemies.iterator();
		//Each case implicitly knows it's a Mii.Color! That's crazy!
		switch(hero.getColor()) {
			//Attacks
			case RED:
				damageEnemies(itr, hero);
				break;
			case BLUE:
				damageEnemies(itr, hero);
				break;
			
			//Hero effects
			case PINK:
				return Mii.Color.PINK;
			case ORANGE:
				return Mii.Color.ORANGE;
			case YELLOW:
				return Mii.Color.YELLOW;
			case GREEN:
				return Mii.Color.GREEN;
			case BROWN:
				return Mii.Color.BROWN;
			
			//Enemy effects
			case LBLUE:
				while (itr.hasNext()) {
					itr.next().changeStatus(Enemy.Status.FROZEN);
				}
				break;
			case LGREEN:
				while (itr.hasNext()) {
					itr.next().changeStatus(Enemy.Status.ASLEEP);
				}
				break;
			case PURPLE:
				while (itr.hasNext()) {
					itr.next().changeStatus(Enemy.Status.POISONED);
				}
				break;
			
			//Room effects
			case WHITE:
				if (roomStatus == Status.DARK) {
					roomStatus = Status.NEUTRAL;
				}
				break;
			case BLACK:
				if (roomStatus == Status.BRIGHT) {
					roomStatus = Status.NEUTRAL;
				}
				break;
		}
		return null;
	}
		
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
		
	public void changeStatus(Status newStatus) {
		roomStatus = newStatus;
	}

	public boolean victory() {
		return enemies.size() == 0;
	}
	
	public boolean canStay() {
		//if enemies are asleep or frozen return true
		return false;
	}
	
	/** Helper methods **/
	
	// Checks status of enemies after an attack
	private void checkEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getStatus() == Enemy.Status.POISONED) {
				enemies.get(i).poisonDamage();
			}
			if (enemies.get(i).isDead()) {
				enemies.remove(i);
			}
		}
	}
	
	private void damageEnemies(Iterator<Enemy> itr, Mii hero) {
		while (itr.hasNext()) {
			itr.next().magicDamage(hero.getLevel() * 2, hero.getColor());
		}
	}

}
