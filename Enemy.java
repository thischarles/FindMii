/**
 * A representation of an Enemy in the Find Mii game. They have a type, an HP counter, a weakness, and a status.
 * HP and status is modified using methods that affect damage. Any status changes that may occur is also stored 
 * and changed through this class. Spell damage is calculated here.
 * @author Charles Hwang
 * @version March 18, 2015
 */

public class Enemy {
	
	/** Enemy type is (text, hp, weakness).
	 *  Decided to use enum because there's only a handful of types. Saw no advantage
	 *  in using subclasses for each enemy. 
	 **/
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
	
	/** Status effects for Enemy **/
	public static enum Status {
		ASLEEP,
		FROZEN,
		POISONED;
	}

	/** Enemy's type which holds its info. **/
	private Type type;
	/** HP counter **/
	private int hp;
	/** A weakness for Enemies that have one. **/
	private Mii.Color weakness;
	/** Status for when the Enemy is asleep, frozen, or poisoned. **/
	private Status status;
	
	/**
	 * An Enemy constructor.
	 * @param type The Type from enum determines the Enemy's attributes.
	 */
	public Enemy(Type type) {
		this.type = type;
		this.hp = type.hp;
		this.weakness = type.weakness;
	}
	
	/**
	 * Damage is decremented for normal attacks.
	 * @param damage The amount of damage the Enemy took which is also the Mii's level.
	 */
	public void damage(int damage) {
		//damage
		if (damage != 0) {
			System.out.println(type.text + " took " + damage + " HP of damage.");
	
			hp = hp - damage;
		}
		//zero damage from missed attacks
		else {
			System.out.println(type.text + " took NO DAMAGE.");
		}
	}
	
	/**
	 * Damage is decremented for magic spell attacks. If the Enemy is hit with its weakness,
	 * damage is tripled. If the Enemy is unaffected, no damage is taken. Spells normally do
	 * double the damage of the Mii's level.
	 * @param damage The amount of damage which is also the Mii's level.
	 * @param color The color of the spell.
	 */
	public void magicDamage(int damage, Mii.Color color) {
		//weakness (3x)
		if (weakness != null && weakness == color) {
			System.out.println("CRITICAL HIT!");
			System.out.println(color + " magic did " + damage * 3 + " HP of damage.");
			hp = hp - (damage * 3);
		}
		//immunity (0)
		else if (weakness != null && weakness == Mii.Color.RED && color == Mii.Color.RED ||
				weakness != null && weakness == Mii.Color.BLUE && color == Mii.Color.BLUE) {
			System.out.println(type.text + " is unaffected by " + color + " spells.");
		}
		//normal damage (2x)
		else {
			System.out.println(color + " magic did " + damage * 2 + " HP of damage.");
			hp = hp - (damage * 2);
		}
	}
	
	/**
	 * Status check
	 * Used in conjunction with Level's checkEnemies().
	 * @return current Status of the Enemy
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * When the Enemy is poisoned, the Enemy will take poison damage.
	 * Used in conjunction with Level's checkEnemies().
	 */
	public void poisonDamage() {
		hp = hp - 1;
		System.out.println(type.text + " takes poison damage.");
	}
	
	/**
	 * Change the Enemy's status.
	 * Used in conjunction with Level's magic().
	 * @param newStatus The new status
	 */
	public void changeStatus(Status newStatus) {
		status = newStatus;
	}

	/**
	 * If HP is 0 or less, the Enemy is dead.
	 * @return Dead or alive?
	 */
	public boolean isDead() {
		return hp <= 0;
	}
	
	/**
	 * Enemy's name
	 * @return the Enemy's name
	 */
	public String getName() {
		return type.text;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return type.text + " - " + hp + " HP.";
	}
}
