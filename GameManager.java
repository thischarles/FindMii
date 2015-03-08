import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Scanner;

/* 
 * NOTES TO SELF:
 * https://www.youtube.com/playlist?list=PLC8FFF63342488EA2
 * http://nintendo.wikia.com/wiki/Find_Mii_(3DS)
 * http://www.gamefaqs.com/3ds/625899-streetpass-mii-plaza/faqs/68668
 * 
 * Fancy enums: http://stackoverflow.com/questions/3978654/best-way-to-create-enum-of-strings
 */

/**
 * The GameManager for Find Mii.
 * Find Mii is a game on the Nintendo 3DS that is part of the Mii Plaza. It's a mini RPG where the Mii's
 * a player collects using StreetPass are the heroes of the game. Each Mii attacks using a sword or magic.
 * The type of magic spell is determined the Mii's t-shirt color. The Mii also has levels based on
 * how many times that Mii is collected using StreetPass. This is a rudimentary Java implementation.
 * 
 * The game loop takes place here. In the game loop, the game takes input from the player.
 * The player can choose to do a standard attack, use their magic spell, or move to the next hero in the queue.
 * 
 * The game will check the if the current Level has been cleared or if the next Mii (if available) is up to fight
 * if the level is not cleared. The Level is considered clear when all Enemies are defeated.
 * The game will also check if the game has been won. The game is won if all levels are cleared. The game is
 * lost if there are no more heroes left to fight.
 * @author Charles Hwang
 * @version March 7, 2015
 */

public class GameManager {

	/** A queue of Mii objects who are the heroes of Find Mii **/
	static ArrayDeque<Mii> heroes = new ArrayDeque<Mii>();
	/** A queue of Level objects which are the Levels of Find Mii **/
	static ArrayDeque<Level> levels = new ArrayDeque<Level>();

	public static void main(String[] args) {

		/** Initialize levels and heroes **/
		levels.add(new Level());
		heroes.add(new Mii("Chaz", 1, Mii.Color.BLUE));
		heroes.add(new Mii("Fred", 1, Mii.Color.RED));
		boolean gameLoop = true;
		
		/** Input setup **/
		Scanner playerInput = new Scanner(System.in);
//		char command = 'A';	//DEBUG for auto-attacking

		while (gameLoop) {
			//reusable variables
			int target = 0;
			Mii.Color buff = null;

			Mii mii = heroes.peekFirst();
			Level currentLevel = levels.peekFirst();

			/** Hero, their status, enemy status, player entry **/
			System.out.println(mii.getName() + " is up.");
			boostBlurb(mii);
			System.out.println();
			getEnemyStatus(currentLevel);
			System.out.println("(A)ttack or (M)agic or (N)ext Hero: ");
			char command = playerInput.next().charAt(0);
						
//TODO: Add loop so that player can cancel from Attack and Magic and choose again
			/** Attack **/
			if (command == 'a' || command == 'A') {
				target = pickTarget(currentLevel);
				currentLevel.attack(mii, target);
			}
			/** Magic **/
			else if (command == 'm' || command == 'M') {
				buff = currentLevel.magic(mii);
			}
//TODO: next hero
			
			/** Handle hero boost from magic spell **/
			if (buff != null) {
				boostHandler(buff);
			}
			
			System.out.println();
			
			/** Curernt level cleared. **/
			if (currentLevel.victory()) {
				System.out.println("Level cleared!");
				levels.removeFirst();
			}
//TODO: Current hero doesn't leave if enemies are asleep or frozen
			/** Level not cleared. Current hero leaves. Next hero up. **/
			else {
				System.out.println(mii.getName() + " gets tired and turns back.");
				System.out.println();
				heroes.removeFirst();
			}
			
			/** All levels have been cleared. Game won! **/
			if (levels.isEmpty()) {
				System.out.println("All levels beaten!");
				System.out.println("Game won!");
				gameLoop = false;
			}

			/** Ran out of heroes. Game lost! **/
			if (heroes.isEmpty()) {
				System.out.println("No more heroes.");
				System.out.println("Game lost!");
				gameLoop = false;
			}
		}
	}
	
//TODO: change currentLevel to just straight Enemies list?
//TODO: add option for player to be able to cancel attack
	/**
	 * Helper method for the player to choose a target for their attack.
	 * @param currentLevel Used to get the list of enemies in the current level
	 * @return the Index of the Enemy that will be attacked in Level.
	 */
	private static int pickTarget(Level currentLevel) {
		int targets = currentLevel.getChoices();
		Scanner playerInput = new Scanner(System.in);
		
		System.out.println("Attack which enemy?:");

		boolean tryAgain = true;
		int target = -1;
		while (tryAgain) {
			target = playerInput.nextInt();
			if (target >= 1 && target <= targets) {
				tryAgain = false;
			}
			else {
				System.out.println("Invalid target. Attack which enemy?:");
			}
		}
		
		return target - 1;
	}
	
//TODO: Probably can be in Level class.
	/**
	 * Helper method for creating a print out of the current Enemies and their HP
	 * @param currentLevel the current Level
	 */
	private static void getEnemyStatus(Level currentLevel) {
		for (int i = 1; i <= currentLevel.getChoices(); i++) {
			System.out.println(i + ": " + currentLevel.getEnemies().get(i - 1));
		}
	}
	
//TODO: Probably can be in Mii class.
	/**
	 * Helper method for getting a description of the current hero's buff
	 * @param hero
	 */
	private static void boostBlurb(Mii hero) {
		if (hero.getBoost() != null) {
			switch (hero.getBoost()) {
				case PINK:
					System.out.println("PINK description");
					break;
				case ORANGE:
					System.out.println(hero.getName() + " has very high morale.");
					break;
				case YELLOW:
					System.out.println("YELLOW description");
					break;
				case GREEN:
					System.out.println("GREEN description");
					break;
			}
		}
	}
	
	/**
	 * Helper method for handling any boosts from magic spells.
	 * @param buff Spell color. Precondition: buff is not null
	 */
	private static void boostHandler(Mii.Color buff) {
		switch(buff) {
			case PINK:
				if (!heroes.isEmpty()) {
					Iterator<Mii> itr = heroes.iterator();
					while (itr.hasNext()) {
						itr.next().makeDaring();
					}
				}
				break;
			case ORANGE:
				if (!heroes.isEmpty()) {
					Iterator<Mii> itr = heroes.iterator();
					while (itr.hasNext()) {
						itr.next().invigorate();
					}
				}
				break;
			case YELLOW:
				
				break;
			case GREEN:
				if (!heroes.isEmpty()) {
					heroes.peekFirst().makeStrengthened();
				}
				break;	
			case BROWN:
				//Need a create a random Mii function
				Mii summonedHero = new Mii("Wandering hero", 5, Mii.Color.PINK);
				heroes.addFirst(summonedHero);
				break;
		}
	}
}
