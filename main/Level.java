package main;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * A representation of a Level in the Find Mii game. The Level holds a list of enemies, the room's stauts, 
 * and a Random object for random number generation. Attack damage calculations happen here.
 * @author Charles Hwang
 * @version March 31, 2015
 */

public class Level {
	
	/** Levels can dark or bright **/
	public static enum Status {
		DARK,
		BRIGHT;
	}
		
	/** Enemies of this Level **/
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	/** The level is dark, bright, or not **/
	private Status levelStatus;
	
	/**
	 * Creates Enemies and put them in the list of Enemies
	 */
	public Level() {
		//read file
		//add enemies
		enemies.add(new Enemy(Enemy.Type.GHOST));
	}

	/**
	 * Player chooses to attack.
	 * @param hero Mii doing the attacking
	 * @param choice Targeting Enemy at position in the list
	 */
	public void attack(Mii hero, int choice) {
		//Mii's attack three times
		System.out.println(hero.getName() + " attacks.");
		enemies.get(choice).attackDamage(hero);
		System.out.println();

		if (!enemies.get(choice).isDead()) {
			System.out.println("And attacks again.");
			enemies.get(choice).attackDamage(hero);
			System.out.println();

			if (!enemies.get(choice).isDead()) {
				System.out.println("And attacks once more.");
				enemies.get(choice).attackDamage(hero);
				System.out.println();
				
				//Handle orange/invigorated
				if (!enemies.get(choice).isDead()) {
					if (hero.isInvigorated()) {
						System.out.println("Your hero gets a bonus attack!");
						enemies.get(choice).attackDamage(hero);
						System.out.println();
					}
				}
			}
		}
		
		if (enemies.get(choice).isDead()) {
			System.out.println(enemies.get(choice).getName() + " is defeated.");
		}
	}
	
	/**
	 * Player chooses to cast a magic spell
	 * @param hero Mii casting the magic
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
	
	/**
	 * If enemies are asleep or frozen, Mii can keep fighting
	 * @return false if any enemy is not frozen or asleep, true otherwise
	 */
	public boolean canStay() {
		Iterator<Enemy> iterator = enemies.iterator();
		while (iterator.hasNext()) {
			Enemy enemy = iterator.next();
			if (enemy.getStatus() != Enemy.Status.FROZEN || enemy.getStatus() != Enemy.Status.FROZEN) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks enemy status effects at the end of turn.
	 */
	public void checkEnemyStatus() {
		Iterator<Enemy> iterator = enemies.iterator();
		while (iterator.hasNext()) {
			Enemy enemy = iterator.next();
			if (enemy.getStatus() == Enemy.Status.POISONED) {
				System.out.println(enemy.getName() + " is poisoned.");
				enemy.poisonDamage();
				if (enemy.isDead()) {
					System.out.println(enemy.getName() + " is defeated.");
				}
			}
			if (enemy.getStatus() == Enemy.Status.FROZEN) {
				if (GameManager.rng.nextInt(GameManager.CHANCE) % 2 == 0) {
					System.out.println(enemy.getName() + " is able to move again.");
					enemy.changeStatus(null);
				}
				else {
					System.out.println(enemy.getName() + " is frozen and cannot move.");
				}
			}
			if (enemy.getStatus() == Enemy.Status.ASLEEP) {
				if (GameManager.rng.nextInt(GameManager.CHANCE) % 2 == 0) {
					System.out.println(enemy.getName() + " woke up.");
					enemy.changeStatus(null);
				}
				else {
					System.out.println(enemy.getName() + " is still asleep.");
				}
			}
		}
	}
	
	/**
	 * Create a print out of the current Enemies and their HP
	 */
	public void printEnemies() {
		for (int i = 1; i <= enemies.size(); i++) {
			System.out.println(i + ": " + enemies.get(i - 1));
		}
	}
	
	/**
	 * Removes Enemies that are considered dead (isDead()). Called at the end of turns.
	 */
	public void enemyGarbageCollection() {
		Iterator<Enemy> iterator = enemies.iterator();
		while(iterator.hasNext()) {
			Enemy enemy = iterator.next();
			if (enemy.isDead()) {
				iterator.remove();
			}
		}
	}
	
	/**
	 *
	 * --- Helper methods ---
	 *
	 **/
	
	/**
	 * For handling the two magic spells that only damage enemies, Blue and Red.
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
				System.out.println("BLUE MAGIC TEXT HERE " + enemies.get(0).getName());
			}
			else {
				System.out.println("BLUE MAGIC TEXT HERE");
			}
		}
//TODO: Enemy dead dialog needs to be verified
		Iterator<Enemy> itr = enemies.iterator();
		while (itr.hasNext()) {
			Enemy enemy = itr.next();
			enemy.magicDamage(hero.getLevel(), hero.getColor());
			if (enemy.isDead()) {
				System.out.println(enemy.getName() + " is defeated.");
			}
		}
	}	
}
