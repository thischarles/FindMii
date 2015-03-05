public class Enemy {
	
	public static enum Type {
		GHOST("Ghost", 7, null),
		BLUEGHOST("Blue Ghost", 25, Mii.Color.RED),
		BLOODGHOST("Blood Ghost", 50, Mii.Color.BLUE),
		AGHOST("Armored Ghost", 10, null),
		ADEMON("Armored Demon", 25, null),
		AFIEND("Armored Fiend", 60, null);
		
		private final String text;
		private final int hp;
		private final Mii.Color weakness;
		
		private Type(final String text, final int hp, Mii.Color weakness) {
			this.text = text;
			this.hp = hp;
			this.weakness = weakness;
		}
	};
	
	public static enum Status {
		NEUTRAL,
		ASLEEP,
		FROZEN,
		POISONED;
	}

	private Type type;
	private int hp;
	private Mii.Color weakness;
	private Status status;
	
	public Enemy(Type type) {
		this.type = type;
		this.hp = type.hp;
		this.weakness = type.weakness;
		status = Status.NEUTRAL;
	}
	
	public void damage(int damage) {
		System.out.println(type.text + " took " + damage + " HP of damage.");

		hp = hp - damage;
	}
	
	public void magicDamage(int damage, Mii.Color color) {
		if (weakness != null && weakness == color) {
			damage(damage * 2);
		}
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void poisonDamage() {
		hp = hp - 1;
		System.out.println(type.text + " takes poison damage.");
	}
	
	public void changeStatus(Status newStatus) {
		status = newStatus;
	}

	public boolean isDead() {
		return hp <= 0;
	}	
}
