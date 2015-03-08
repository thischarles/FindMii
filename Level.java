import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * A representation of a Level in the Find Mii game. The Level holds a list of enemies, the room's stauts, 
 * and a Random object for random number generation. Attack damage calculations happen here.
 * @author Charles Hwang
 * @version March 7, 2015
 */

public class Level {
	
	/** Levels can dark or bright **/
	public static enum Status {
		DARK,
		BRIGHT;
	}
	
	/** The seed for the random number generator **/
	private final int CHANCE = 10;
	
	/** Enemies of this Level **/
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	/** The level is dark, bright, or not **/
	private Status levelStatus;
	/** Random number generator for damage calculations and if status effects have worn off **/
	private Random rng = new Random();
	
	/**
	 * Creates Enemies and put them in the list of Enemies
	 */
	public Level() {
		//read file
		//add enemies
		enemies.add(new Enemy(Enemy.Type.GHOST));
	}

//TODO: Do Mii's attack the next available Enemy when it kills an Enemy?
	/**
	 * Player chooses to attack
	 * @param hero Mii doing the attacking
	 * @param choice Targeting Enemy at position in the list
	 */
	public void attack(Mii hero, int choice) {
		//Mii's attack three times
		System.out.println(hero.getName() + " attacks.");
		enemies.get(choice).damage(damageCalculator(hero));
		checkEnemies();
		System.out.println();

		if (!enemies.isEmpty()) {
			System.out.println("And attacks again.");
			enemies.get(choice).damage(damageCalculator(hero));		
			checkEnemies();
			System.out.println();
		}
		
		if (!enemies.isEmpty()) {
			System.out.println("And attacks once more.");
			enemies.get(choice).damage(damageCalculator(hero));
			checkEnemies();
			System.out.println();
		}
		
		//Handle orange/invigorated
		if (!enemies.isEmpty()) {
			if (hero.getBoost() == Mii.Color.ORANGE) {
				System.out.println("Your hero gets a bonus attack!");
				enemies.get(choice).damage(damageCalculator(hero));
				checkEnemies();
				System.out.println();
			}
		}
	}
	
	/**
	 * Player chooses to cast a spell
	 * @param hero Mii casting the spell
	 * @return The Color of any buff spells so that they can be handled by the GameManager
	 */
	public Mii.Color magic(Mii hero) {
		System.out.println(hero.getName() + " uses " + hero.getColor() + " magic.");
		Iterator<Enemy> itr = enemies.iterator();
		//Each case implicitly knows it's a Mii.Color! That's crazy!
		switch(hero.getColor()) {
			//Attacks
			case RED:
				damageEnemiesWithMagic(hero);
				break;
			case BLUE:
				damageEnemiesWithMagic(hero);
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
			
			//Level effects
			case WHITE:
				if (levelStatus == Status.DARK) {
					levelStatus = null;
				}
				break;
			case BLACK:
				if (levelStatus == Status.BRIGHT) {
					levelStatus = null;
				}
				break;
		}
		return null;
	}
	
	/**
	 * Returns the size of the Enemies list
	 * @return size of the Enemies list
	 */
	public int getChoices() {
		return enemies.size();
	}
	
	/**
	 * Returns the Enemies list
	 * @return Enemies list
	 */
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	/**
	 * Changes the Level's status
	 * @param newStatus the status that Level changes to
	 */
	public void changeStatus(Status newStatus) {
		levelStatus = newStatus;
	}

	/**
	 * Victory condition for a level
	 * @return the status of the level being cleared or not
	 */
	public boolean victory() {
		return enemies.size() == 0;
	}
	
//TODO:	 Mii doesn't have to turn away or run if the enemy is frozen or asleep
	/**
	 * 
	 * @return
	 */
	public boolean canStay() {
		//if enemies are asleep or frozen return true
		return false;
	}
	
	/** Helper methods **/
	
	/**
	 * Checks the status of the enemies. Used after each time a Mii attacks.
	 */
	private void checkEnemies() {
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isDead()) {
				System.out.println(enemies.get(i).getName() + " is defeated.");
				enemies.remove(i);
			}
//TODO: Status checks happen only once at the end of a turn?
			if (enemies.get(i).getStatus() == Enemy.Status.POISONED) {
				System.out.println(enemies.get(i).getName() + " is poisoned.");
				enemies.get(i).poisonDamage();
			}
			if (enemies.get(i).getStatus() == Enemy.Status.FROZEN) {
				if (rng.nextInt(CHANCE) % 2 == 0) {
					System.out.println(enemies.get(i).getName() + " is able to move again.");
					enemies.get(i).changeStatus(null);
				}
				else {
					System.out.println(enemies.get(i).getName() + " is frozen and cannot move.");
				}
			}
			if (enemies.get(i).getStatus() == Enemy.Status.ASLEEP) {
				if (rng.nextInt(CHANCE) % 2 == 0) {
					System.out.println(enemies.get(i).getName() + " woke up.");
					enemies.get(i).changeStatus(null);
				}
				else {
					System.out.println(enemies.get(i).getName() + " is still asleep.");
				}
			}
		}
//
	}
	
//TODO: Should check enemies after damage?
	/**
	 * For handling the two spells that only damage enemies, Blue and Red.
	 * @param hero Mii dealing the damage
	 */
	private void damageEnemiesWithMagic(Mii hero) {
		if (hero.getColor() == Mii.Color.RED) {
			if (enemies.size() == 1) {
				System.out.println("Burning flames engulf the " + enemies.get(0).getName());
			}
			else {
				System.out.println("Burning flames engulf the enemies.");
			}
		}
		if (hero.getColor() == Mii.Color.BLUE) {
			if (enemies.size() == 1) {
				System.out.println("BLUE MAGIC text here " + enemies.get(0).getName());
			}
			else {
				System.out.println("BLUE MAGIC text here");
			}
		}
		
		Iterator<Enemy> itr = enemies.iterator();
		while (itr.hasNext()) {
			itr.next().magicDamage(hero.getLevel(), hero.getColor());
		}
	}
	
//TODO: Feels funning doing attack calculation here while magic calculation is done in Enemy.
	/**
	 * Damage calculator for normal attack. Mii damage is equal to its level.
	 * That is subtracted from the Enemy. A critical hit does double damage.
	 * An attack that misses because of inaccuracy does no damage.
	 * @param hero Mii doing the damage
	 * @return the amount of damage the hero has done
	 */
	private int damageCalculator(Mii hero) {
		if (rng.nextInt(CHANCE) <= hero.getAccuracy()) {
			if (rng.nextInt(CHANCE) <= hero.getCriticalChance()) {
				System.out.println("CRITICAL HIT!");
				System.out.println("Attack does " + hero.getLevel() * 2 + " damage.");
				return hero.getLevel() * 2;
			}
			System.out.println("Attack does " + hero.getLevel() + " damage.");
			return hero.getLevel(); 
		}
		System.out.println("Attack misses!");
		return 0;
	}

}
