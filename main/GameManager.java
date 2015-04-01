package main;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Random;
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
 * @version March 31, 2015
 */

public class GameManager {

	/** A queue of Mii objects who are the heroes of Find Mii **/
	static ArrayDeque<Mii> heroes = new ArrayDeque<Mii>();
	/** A queue of Level objects which are the Levels of Find Mii **/
	static ArrayDeque<Level> levels = new ArrayDeque<Level>();

	/** The seed for the random number generator **/
	static final int CHANCE = 10;

	/** Random number generator for damage calculations and if status effects have worn off **/
	static Random rng = new Random();
	
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

			/** Hero, their status, enemy status **/
			System.out.println(mii.getName() + " is up.");
			mii.boostBlurb(); //Will say something if Mii has a boost
			System.out.println();
			currentLevel.printEnemies();
			
			boolean tryAgain = true;

			/** Player command entry loop **/
			while (tryAgain) {
				System.out.println("(A)ttack or (M)agic or (N)ext Hero: ");
				char command = playerInput.next().charAt(0);
				/** Attack **/
				if (command == 'a' || command == 'A') {
					target = pickTarget(currentLevel);
					//Proceed with attack. -1 means attack was cancelled.
					if (target != -1) {
						currentLevel.attack(mii, target);
						tryAgain = false;
					}
				}
				/** Magic **/
				else if (command == 'm' || command == 'M') {
					buff = currentLevel.magic(mii);
					tryAgain = false;
				}
				/** Next Hero **/
				else if (command == 'n' || command == 'N') {
					System.out.println(mii.getName() + " goes to the end of the line.");
					heroes.removeFirst();
					heroes.addLast(mii);
					mii = heroes.peekFirst();
					System.out.println(mii.getName() + " is up.");
				}
				else {
					System.out.println("Invalid command.");
					System.out.println("(A)ttack or (M)agic or (N)ext Hero: ");
					command = playerInput.next().charAt(0);
				}
			}
			
			/** Handle hero boost from magic spell **/
			if (buff != null) {
				boostHandler(buff);
			}
			
			System.out.println();
			
			currentLevel.checkEnemyStatus();
			currentLevel.enemyGarbageCollection();
			
			/** Current level cleared. **/
			if (currentLevel.victory()) {
				System.out.println("Level cleared!");
				levels.removeFirst();
			}
			/** All enemies are frozen or asleep so the hero cant stay for the next turn **/
			else if (currentLevel.canStay()) {
//TODO: Current hero doesn't leave if enemies are asleep or frozen. Is the dialogue right?
				System.out.println(mii.getName() + " stands and fights.");
			}
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
	
	/**
	 * Helper method for the player to choose a target for their attack.
	 * @param currentLevel Used to get the list of enemies in the current level
	 * @return the Index of the Enemy that will be attacked in Level. -1 if player is cancelling attack.
	 */
	private static int pickTarget(Level currentLevel) {
		int targets = currentLevel.getChoices();
		Scanner playerInput = new Scanner(System.in);

		System.out.println("Attack which enemy? Enter 0 to cancel:");

		boolean tryAgain = true;
		int target = -1;
		while (tryAgain) {
			target = playerInput.nextInt();
			if (target == 0) {
				tryAgain = false;
				System.out.println("Attack cancelled.");
			}
			else if (target >= 1 && target <= targets) {
				tryAgain = false;
			}
			else {
				System.out.println("Invalid target. Attack which enemy?:");
			}
		}
		
		return target - 1;
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
						itr.next().makeInvigorated();
					}
				}
				break;
			case YELLOW:
//TODO: YELLOW MAGIC
				break;
			case GREEN:
				if (!heroes.isEmpty()) {
					heroes.peekFirst().makeStrengthened();
				}
				break;	
			case BROWN:
				Mii summonedHero = new Mii();
				heroes.addFirst(summonedHero);
				break;
			default:
				break;
		}
	}	
}
