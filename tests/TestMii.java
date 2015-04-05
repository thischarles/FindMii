package tests;
import static org.junit.Assert.*;
import main.Mii;

import org.junit.Test;

// JUnit: https://courses.cs.washington.edu/courses/cse143/11wi/eclipse-tutorial/junit.shtml

public class TestMii {

	@Test
	/**
	 * Creating 100 random Mii's to make sure they're levels are between 1 and 7 and that their colors are valid.
	 */
	public void create100RandomMiis() {
		for(int i = 0; i < 100; i++) {
			Mii mii = new Mii();
//			System.out.println("Name: " + mii.getName());
//			System.out.println("Level: " + mii.getLevel());
//			System.out.println("Color: " + mii.getColor());
			assertEquals(mii.getName(), ("A wandering hero"));
			assertTrue(mii.getLevel() <= 7 && mii.getLevel() >= 1);
			assertTrue(mii.getColor() instanceof Mii.Color);
			assertNotNull(mii.getColor());
		}
	}
	
	@Test
	/**
	 * Testing a Mii affected by Pink magic (Daring buff) and returning to normal.
	 * Mii will have max critical chance (10) and minimal accuracy (1).
	 */
	public void testDaring() {
		Mii normal = new Mii("Normal", 1, Mii.Color.RED);
		Mii daring = new Mii("Daring", 2, Mii.Color.GREEN);
		
		daring.makeDaring();
		
		assertEquals(daring.getName(), "Daring");
		assertEquals(daring.getLevel(), 2);
		assertEquals(daring.getCriticalChance(), 10);
		assertEquals(daring.getAccuracy(), 1);
		assertEquals(daring.boostBlurb(), Mii.Color.PINK);
		
		daring.makeNormal();
		
		assertEquals(daring.getName(), "Daring");
		assertEquals(daring.getLevel(), 2);
		assertEquals(daring.getCriticalChance(), normal.getCriticalChance());
		assertEquals(daring.getAccuracy(), normal.getAccuracy());
		assertNull(daring.boostBlurb());
	}

	@Test
	/**
	 * Testing a Mii affected by Green magic (Strength buff) and returning to normal.
	 * Mii will have double the effective level with a maximum level of 7.
	 */
	public void testStrengthened() {
		Mii normal = new Mii("Normal", 1, Mii.Color.RED);
		Mii strengthened = new Mii("Strong", 3, Mii.Color.BLUE);

		strengthened.makeStrengthened();
		
		assertEquals(strengthened.getName(), "Strong");
		assertEquals(strengthened.getLevel(), 6);
		assertEquals(strengthened.getCriticalChance(), normal.getCriticalChance());
		assertEquals(strengthened.getAccuracy(), normal.getAccuracy());	
		assertEquals(strengthened.boostBlurb(), Mii.Color.GREEN);
		
		strengthened.makeNormal();
		
		assertEquals(strengthened.getName(), "Strong");
		assertEquals(strengthened.getLevel(), 3);
		assertEquals(strengthened.getCriticalChance(), normal.getCriticalChance());
		assertEquals(strengthened.getAccuracy(), normal.getAccuracy());
		assertNull(strengthened.boostBlurb());
	}
	
	@Test
	/**
	 * Testing a Mii affected by Orange magic (Invigorated buff) and returning to normal.
	 * Mii will have an extra attack.
	 * See LevelTest for a more complete test.
	 */
	public void testInvigorated() {
		Mii normal = new Mii("Normal", 1, Mii.Color.RED);
		Mii invigorated = new Mii("Invigorated", 4, Mii.Color.BLACK);

		invigorated.makeInvigorated();
		
		assertEquals(invigorated.getName(), "Invigorated");
		assertTrue(invigorated.isInvigorated());
		assertEquals(invigorated.boostBlurb(), Mii.Color.ORANGE);
		
		invigorated.makeNormal();
		
		assertEquals(invigorated.getName(), "Invigorated");
		assertEquals(invigorated.getLevel(), 4);
		assertEquals(invigorated.getCriticalChance(), normal.getCriticalChance());
		assertEquals(invigorated.getAccuracy(), normal.getAccuracy());
		assertNull(invigorated.boostBlurb());
	}
}
