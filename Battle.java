import java.util.ArrayDeque;
import java.util.Iterator;

/**
 * https://www.youtube.com/playlist?list=PLC8FFF63342488EA2
 * http://nintendo.wikia.com/wiki/Find_Mii_(3DS)
 * http://www.gamefaqs.com/3ds/625899-streetpass-mii-plaza/faqs/68668
 * 
 * Enums: http://stackoverflow.com/questions/3978654/best-way-to-create-enum-of-strings
 */

public class Battle {

	static ArrayDeque<Mii> heroes = new ArrayDeque<Mii>();
	static ArrayDeque<Level> levels = new ArrayDeque<Level>();

	public static void main(String[] args) {

		/** Initialize levels and heroes **/
		levels.add(new Level());
		heroes.add(new Mii("Chaz", 5, Mii.Color.BLUE));
		heroes.add(new Mii("Jeff", 3, Mii.Color.RED));
		boolean gameLoop = true;
		
		/** Input scanner **/
		char command = 'A';
		
		while (gameLoop) {
			Mii mii = heroes.peekFirst();
			Level currentLevel = levels.peekFirst();

			//reusable variables
			int target = 0;
			Mii.Color buff = null;
			
			//attack
			if (command == 'a' || command == 'A') {
				target = pickTarget(currentLevel);
				currentLevel.attack(mii, target);
			}
			//magic
			else if (command == 'm' || command == 'M') {
				buff = currentLevel.magic(mii);
			}
			
			//handle hero buffs
			if (buff != null) {
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
						break;
					case YELLOW:
						break;
					case GREEN:
						if (!heroes.isEmpty()) {
							heroes.peekFirst().doubleLevel();
						}
						break;	
					case BROWN:
						//Need a create a random Mii function
						Mii temporaryHero = new Mii("Wandering hero", 5, Mii.Color.PINK);
						break;
				}
			}
			
			if (currentLevel.victory()) {
				System.out.println("Level cleared!");
				levels.removeFirst();
			}
			else {
				//still on level
				System.out.println(mii.getName() + " gets tired and turns back.");
				heroes.removeFirst();
			}
			
			if (levels.isEmpty()) {
				System.out.println("All levels beaten!");
				System.out.println("Game won!");
				gameLoop = false;
			}
			
			if (heroes.isEmpty()) {
				System.out.println("No more heroes.");
				System.out.println("Game lost!");
				gameLoop = false;
			}
		}
	}
	
	//target picking interface
	//used mostly for attack command
	private static int pickTarget(Level currentLevel) {
		int targets = currentLevel.getChoices();
		/** Use scanner to get target **/
		int target = 0;
		return target;
	}
	
}
