package elevatortests;

import elevator.Elevator;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Some sample Elevator class test cases!
 * 
 * @author dplante
 *
 */

public class TestElevator_DP 
{
	private Elevator myElevator;
	
	/**
	 * An elevator with 5 floors will be set up new before running 
	 * each test
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		myElevator = new Elevator(5);
	}

	/**
	 * Test when picking up when elevator moves up from first floor
	 * but "up" buttons pushed out of order
	 */
	@Test
	public void testUpOutOfOrder() 
	{
		assertTrue(myElevator.pushUp(1));
		assertTrue(myElevator.pushUp(4));
		assertTrue(myElevator.pushDown(4));
		assertTrue(myElevator.pushUp(3));
		assertEquals("Move should place elevator on first floor", myElevator.move(), 1);
		assertTrue(myElevator.pushIn(5));
		assertEquals("Move should place elevator on third floor", myElevator.move(), 3);
		assertEquals("Move should place elevator on fourth floor", myElevator.move(), 4);
		assertEquals("Move should place elevator on fifth floor", myElevator.move(), 5);
		assertEquals("Move should keep elevator on fifth floor", myElevator.move(), 4);	
		assertEquals("Move should keep elevator on fifth floor", myElevator.move(), 4);	
	}

	/**
	 * Test direction
	 */
	@Test
	public void testDirection() 
	{
		assertEquals("Direction should not be set yet", myElevator.getDirection(), Elevator.NOT_SET);
		assertTrue(myElevator.pushUp(3));
		assertEquals("Move should place elevator on third floor", myElevator.move(), 3);
		assertEquals("Direction should be set to UP", myElevator.getDirection(), Elevator.UP);
		assertTrue(myElevator.pushIn(5));
		assertTrue(myElevator.pushDown(4));
		assertEquals("Move should place elevator on fifth floor", myElevator.move(), 5);
		//assertEquals("Direction should be set to DOWN", myElevator.getDirection(), Elevator.DOWN);
		assertEquals("Move should place elevator on fourth floor", myElevator.move(), 4);
		assertTrue(myElevator.pushIn(3));
		assertEquals("Move should place elevator on third floor", myElevator.move(), 3);
		assertEquals("Direction should not be set yet", myElevator.getDirection(), Elevator.NOT_SET);
		assertEquals("Move should keep elevator on third floor", myElevator.move(), 3);
	}
}
