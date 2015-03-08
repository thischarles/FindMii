/**
 * A representation of a Mii in the Find Mii game. They have a name, level, color, a boost, 
 * an accuracy rating, and a critical chance rating.
 * @author Charles Hwang
 * @version March 7, 2015
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
	 * Level of the Mii
	 * @return Mii's level
	 */
	public int getLevel() {
		return level;
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
	}
	
	/**
	 * The effect of a Pink spell boost. Makes a Mii daring.
	 * Critical chance is at max. Accuracy is at its lowest.
	 */
	public void makeDaring() {
		criticalChance = 10;
		accuracy = 1;
	}
	
	/**
	 * The effect of a Green spell boost. Makes a Mii strengthened.
	 * Doubles a Mii's level
	 */
	public void makeStrengthened() {
		currentLevel = level * 2;
		if (currentLevel > 7)
			currentLevel = 7;
	}
	
	/**
	 * The effect of a Green spell boost. Makes a Mii strengthened.
	 * Critical chance is at max. Accuracy is at its lowest.
	 */
	public void invigorate() {
		boost = Mii.Color.ORANGE;
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
	 * Mii's boost
	 * @return Mii's boost
	 */
	public Color getBoost() {
		return boost;
	}
}
