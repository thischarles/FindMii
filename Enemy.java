public class Enemy {
	
	public static enum Type {
		GHOST("Ghost"),
		BLUEGHOST("Blue Ghost"),
		BLOODGHOST("Blood Ghost"),
		AGHOST("Armored Ghost"),
		ADEMON("Armored Demon"),
		AFIEND("Armored Fiend"),
		AAFIEND("Armored Archfiend"),
		YSLIME("Yellow Slime"),
		BSLIME("Blue Slime"),
		PSLIME("Purple Slime"),
		GSLIME("Green Slime");
		
		private final String text;
		
		private Type(final String text) {
			this.text = text;
		}
	};

	private Type type;
	private int hp;
	
	public Enemy(Type type, int hp) {
		this.type = type;
		this.hp = hp;
	}
	
	public void damage(int damage) {
		System.out.println(type.text + " took " + damage + " HP of damage.");
		hp = hp - damage;
	}

	public boolean isDead() {
		return hp <= 0;
	}
}
