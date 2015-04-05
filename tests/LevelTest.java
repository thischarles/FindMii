package tests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import main.Level;
import main.Mii;

import org.junit.Test;

// Turning System.out.println() into a String: http://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
// Setting standard output back: http://stackoverflow.com/questions/5339499/resetting-standard-output-stream

public class LevelTest {

	@Test
	/**
	 * Similar to testInvigorated() in TestMii. This version verifies the bonus attack happens in Level's attack().
	 */
	public void testInvigorated() {
		Level testLevel = new Level();
		Mii normal = new Mii("Normal", 1, Mii.Color.RED);
		Mii invigorated = new Mii("Invigorated", 1, Mii.Color.BLACK);

		invigorated.makeInvigorated();
		
		assertEquals(invigorated.getName(), "Invigorated");
		assertTrue(invigorated.isInvigorated());
		assertEquals(invigorated.boostBlurb(), Mii.Color.ORANGE);
		
		PrintStream defaultStream = System.out; // Change streams
		final ByteArrayOutputStream dialog = new ByteArrayOutputStream();
		System.setOut(new PrintStream(dialog));

		testLevel.attack(invigorated, 0);				
		assertTrue(dialog.toString().toLowerCase().contains("bonus")); // Checking to see if bonus attack happens

		System.setOut(defaultStream); // Back to standard output

		invigorated.makeNormal();
		
		assertEquals(invigorated.getName(), "Invigorated");
		assertEquals(invigorated.getLevel(), 1);
		assertEquals(invigorated.getCriticalChance(), normal.getCriticalChance());
		assertEquals(invigorated.getAccuracy(), normal.getAccuracy());
		assertNull(invigorated.boostBlurb());
		
	}
}
