package main;
/**
 * A representation of a Mii in the Find Mii game. They have a name, level, color, a boost, 
 * an accuracy rating, and a critical chance rating.
 * @author Charles Hwang
 * @version March 31, 2015
 */

public class Mii {
	
	/** All available Mii Colors. Holds a text version of colors too. **/
	public static enum Color {
		RED("Red"),
		PINK("Pink"),
		ORANGE("Orange"),
		YELLOW("Yellow"),
		LBLUE("Light Blue"),
		BLUE("Blue"),
		LGREEN("Light Green"),
		GREEN("Green"),
		PURPLE("Purple"),
		BROWN("Brown"),
		WHITE("White"),
		BLACK("Black");
		
		private final String text;
		
		private Color(final String text) {
			this.text = text;
		}
		
		public String getText() {
			return text;
		}
	};
	
	/** A Mii's default level and also their damage amount **/
	private int level;
	/** A Mii's current level. Only affected for Green spells. Max level is 7. **/	
	private int currentLevel;
	/** A Mii's name. **/
	private String name;
	/** A Mii's t-shirt color and its spell type. **/
	private Color color;
	/** Any boosts a Mii may have received. **/
	private Color boost;
	
	//Damage calculation ratings
	/** A Mii's attack accuracy **/
	private int accuracy;
	/** A Mii's critical attack chance **/
	private int criticalChance;
	
	/**
	 * A Mii constructor for creating a random hero. Also doubles as the default constructor!
	 */
	public Mii() {
		//if I make colorSpin() static, it can be done
		//this("A wandering hero", GameManager.rng.nextInt(7) + 1, colorSpin(GameManager.rng.nextInt(12)));
		int colorRoll = GameManager.rng.nextInt(12);
		Color randomColor = colorSpin(colorRoll);
		
		name = "A wandering hero";
		level = GameManager.rng.nextInt(7) + 1;
		currentLevel = level;
		color = randomColor;
		boost = null;
		criticalChance = 3;
		accuracy = 7;
	}
	
	/**
	 * A Mii constructor. Has critical chance of 3 and accuracy of 7 by default.
	 * @param name Mii's name
	 * @param level Mii's level
	 * @param color Mii's t-shirt color
	 */
	public Mii(String name, int level, Color color) {
		this.name = name;
		this.level = level;
		currentLevel = level;
		this.color = color;
		this.boost = null;
		criticalChance = 3;
		accuracy = 7;
	}
			
	/**
	 * Current level of the Mii (which is modifiable and the effective level after boosts)
	 * @return Mii's level
	 */
	public int getLevel() {
		return currentLevel;
	}

	/**
	 * Accuracy rating of a Mii for damage calculation
	 * @return Mii's accuracy
	 */
	public int getAccuracy() {
		return accuracy;
	}

	/**
	 * Critical chance rating of a Mii for damage calculation
	 * @return Mii's critical rating
	 */
	public int getCriticalChance() {
		return criticalChance;
	}
	
	/**
	 * Return a Mii to its default critical chance and accuracy.
	 */
	public void makeNormal() {
		criticalChance = 3;
		accuracy = 7;
		currentLevel = level;
		boost = null;
	}
	
	/**
	 * The effect of a Pink spell boost. Makes a Mii daring.
	 * Critical chance is at max. Accuracy is at its lowest.
	 */
	public void makeDaring() {
		criticalChance = 10;
		accuracy = 1;
		boost = Color.PINK;
	}
	
	/**
	 * The effect of a Green spell boost. Makes a Mii strengthened.
	 * Doubles a Mii's level
	 */
	public void makeStrengthened() {
		currentLevel = level * 2;
		if (currentLevel > 7)
			currentLevel = 7;
		boost = Color.GREEN;
	}
	
	/**
	 * The effect of a Orange spell boost. Makes a Mii invigorated.
	 * Gives a Mii an extra attack.
	 */
	public void makeInvigorated() {
		boost = Color.ORANGE;
	}
	
	/**
	 * Checks the Mii's invigorated status
	 * @return true if the Mii is invigorated, false otherwise
	 */
	public boolean isInvigorated() {
		return boost == Color.ORANGE;
	}

	//TOOD: Boost dialogue
	/**
	 * Prints the description of the current hero's buff
	 */
	public void boostBlurb() {
		if (boost != null) {
			switch (boost) {
				case PINK:
					System.out.println("PINK description");
					break;
				case ORANGE:
					System.out.println(name + " has very high morale.");
					break;
				case YELLOW:
					System.out.println("YELLOW description");
					break;
				case GREEN:
					System.out.println("GREEN description");
					break;
				default:
					break;
			}
		}
	}

	/**
	 * Mii's name
	 * @return Mii's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Mii's t-shirt color
	 * @return Mii's t-shirt color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Helper method for determining a color when given a random number
	 * @param value the random number
	 * @return the Color associated with that number
	 */
	private Color colorSpin(int value) {
		switch (value) {
			case 0:
				return Color.RED;
			case 1:
				return Color.PINK;
			case 2:
				return Color.ORANGE;
			case 3:
				return Color.YELLOW;
			case 4:
				return Color.LBLUE;
			case 5:
				return Color.BLUE;
			case 6:
				return Color.LGREEN;
			case 7:
				return Color.GREEN;
			case 8:
				return Color.PURPLE;
			case 9:
				return Color.BROWN;
			case 10:
				return Color.WHITE;
			case 11:
				return Color.BLACK;
			default:
				return null;
		}
	}
}
