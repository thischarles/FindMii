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
	};
		
	private int level;
	private String name;
	private Color color;
	
	public Mii(String name, int level, Color color) {
		this.name = name;
		this.level = level;
		this.color = color;
	}

	public int attack(Enemy enemy) {
		System.out.println(name + " attacked.");
		return level;
	}
	
	public void magic() {
		System.out.println(name + " uses " + color.text);
	}

}
