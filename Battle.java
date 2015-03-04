import java.util.ArrayDeque;

public class Battle {

	public static void main(String[] args) {
		ArrayDeque<Mii> heroes = new ArrayDeque<Mii>();
		ArrayDeque<Level> levels = new ArrayDeque<Level>();

		Level levelOne = new Level();
		
		heroes.add(new Mii("Chaz", 5, Mii.Color.BLUE));
		
		char command = 'A';
		boolean loop = true;
		
		while (loop) {
			Mii mii = heroes.removeFirst();
			//attack
			if (command == 'a' || command == 'A') {
				attack(mii, levelOne);
			}
			//magic
			else if (command == 'm' || command == 'M') {
				
			}
			
			if (levelOne.victory()) {
				System.out.println("Victory!");
			}
			
			if (levels.isEmpty()) {
				System.out.println("All levels beaten!");
				loop = false;
			}
			
			if (heroes.isEmpty()) {
				System.out.println("No more heroes.");
				loop = false;
			}
		}
	}
	
	public static void attack(Mii currentHero, Level currentLevel) {
		int targets = currentLevel.getChoices();
		//pick 0 ... targets
		int choice = 0;
		currentLevel.attack(currentHero, choice);
	}
}
