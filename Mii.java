import java.util.ArrayDeque;
import java.util.Iterator;

public class Mii {
	
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
		
	private int level;
	private String name;
	private Color color;
	private Color boost;
	
	private double accuracy;
	private double criticalChance;
	
	//booleans?
	//private boolean extraAttack; //orange
	//private boolean invigorated; //pink
	//private boolean sandstorm; //yellow
	//private boolean doubled; //green
	
	public Mii(String name, int level, Color color) {
		this.name = name;
		this.level = level;
		this.color = color;
		criticalChance = 0.33;
		accuracy = 0.75;
	}
		
	public int getLevel() {
		return level;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public double getCriticalChance() {
		return criticalChance;
	}
	
	public void makeNormal() {
		criticalChance = 0.33;
		accuracy = 0.75;
	}
	
	//pink
	public void makeDaring() {
		criticalChance = 1.0;
		accuracy = 0.5;
	}
	
	//green
	public void doubleLevel() {
		level = level * 2;
		if (level > 7) {
 			level = 7;
		}
	}
		
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Color getBoost() {
		return boost;
	}
}
