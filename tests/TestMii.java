package tests;
import static org.junit.Assert.*;
import main.Mii;

import org.junit.Test;


public class TestMii {

	@Test
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
	public void testDaring() {
		Mii normal = new Mii("Normal", 1, Mii.Color.RED);
		Mii daring = new Mii("Daring", 2, Mii.Color.GREEN);
		
		daring.makeDaring();
		
		assertEquals(daring.getName(), "Daring");
		assertEquals(daring.getLevel(), 2);
		assertEquals(daring.getCriticalChance(), 10);
		assertEquals(daring.getAccuracy(), 1);
		//check boostBlurb() dialogues?
		
		daring.makeNormal();
		
		assertEquals(daring.getName(), "Daring");
		assertEquals(daring.getLevel(), 2);
		assertEquals(daring.getCriticalChance(), normal.getCriticalChance());
		assertEquals(daring.getAccuracy(), normal.getAccuracy());
		//check boostBlurb() dialogues?		
	}

	@Test
	public void testStrengthened() {
		Mii normal = new Mii("Normal", 1, Mii.Color.RED);
		Mii strengthened = new Mii("Strong", 3, Mii.Color.BLUE);

		strengthened.makeStrengthened();
		
		assertEquals(strengthened.getName(), "Strong");
		assertEquals(strengthened.getLevel(), 6);
		assertEquals(strengthened.getCriticalChance(), normal.getCriticalChance());
		assertEquals(strengthened.getAccuracy(), normal.getAccuracy());		
		//check boostBlurb() dialogues?
		
		strengthened.makeNormal();
		
		assertEquals(strengthened.getName(), "Strong");
		assertEquals(strengthened.getLevel(), 3);
		assertEquals(strengthened.getCriticalChance(), normal.getCriticalChance());
		assertEquals(strengthened.getAccuracy(), normal.getAccuracy());
		//check boostBlurb() dialogues?
	}
	
	@Test
	public void testInvigorated() {
		Mii normal = new Mii("Normal", 1, Mii.Color.RED);
		Mii invigorated = new Mii("Invigorated", 4, Mii.Color.BLACK);

		invigorated.makeInvigorated();
		
		assertEquals(invigorated.getName(), "Invigorated");
		assertTrue(invigorated.isInvigorated());
		//check boostBlurb() dialogues?
		//check attack() dialogues?
		
		invigorated.makeNormal();
		
		assertEquals(invigorated.getName(), "Invigorated");
		assertEquals(invigorated.getLevel(), 4);
		assertEquals(invigorated.getCriticalChance(), normal.getCriticalChance());
		assertEquals(invigorated.getAccuracy(), normal.getAccuracy());
		//check boostBlurb() dialogues?
		//check attack() dialogues?
	}
}
