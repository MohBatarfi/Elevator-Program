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

public class TestElevator_DP2 
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
	public void testUp() 
	{
		assertTrue(myElevator.pushUp(1));
		assertTrue(myElevator.pushUp(3));
		assertEquals("Move should place elevator on first floor", myElevator.move(), 1);
		assertTrue(myElevator.pushIn(2));
		assertTrue(myElevator.pushIn(5));
		assertEquals("Move should place elevator on second floor", myElevator.move(), 2);
		assertEquals("Move should place elevator on third floor", myElevator.move(), 3);
		assertTrue(myElevator.pushIn(4));
		assertEquals("Move should place elevator on fourth floor", myElevator.move(), 4);
		assertEquals("Move should place elevator on fifth floor", myElevator.move(), 5);
	}

	/**
	 * Test when picking up when elevator moves down then up from second floor
	 * but down buttons are preferred floors first
	 */
	@Test
	public void testUpDownRepeat() 
	{
		assertTrue(myElevator.pushUp(2));
		assertEquals("Move should place elevator on second floor", myElevator.move(), 2);
		assertTrue(myElevator.pushDown(4));
		assertEquals("Move should place elevator on forth floor", myElevator.move(), 4);
		assertTrue(myElevator.pushUp(2));
		assertEquals("Move should place elevator on second floor", myElevator.move(), 2);
		assertTrue(myElevator.pushDown(4));
		assertEquals("Move should place elevator on forth floor", myElevator.move(), 4);
	}

	/**
	 * Test elevator stays at floor if nothing else pushed.
	 */
	@Test
	public void testStay() 
	{
		assertTrue(myElevator.pushUp(3));
		assertEquals("Move should place elevator on third floor", myElevator.move(), 3);
		assertEquals("Move should place elevator on third floor", myElevator.move(), 3);
	}

	/**
	 * Test elevator moves down
	 */
	@Test
	public void testDown() 
	{
		assertTrue(myElevator.pushDown(4));
		assertEquals("Move should place elevator on forth floor", myElevator.move(), 4);
		assertTrue(myElevator.pushIn(1));
		assertTrue(myElevator.pushIn(2));
		assertTrue(myElevator.pushIn(3));
		assertEquals("Move should place elevator on third floor", myElevator.move(), 3);
		assertEquals("Move should place elevator on second floor", myElevator.move(), 2);
		assertEquals("Move should place elevator on first floor", myElevator.move(), 1);
	}

	/**
	 * Test buttons that should not move elevator
	 */
	@Test
	public void testBadFloors() 
	{
		assertFalse("Only 5 floors", myElevator.pushUp(6));
		assertFalse("First floor should be 1", myElevator.pushUp(0));
		assertFalse("Only 5 floors", myElevator.pushDown(6));
		assertFalse("First floor should be 1", myElevator.pushDown(0));
		assertFalse("No up button on fifth floor", myElevator.pushUp(5));
		assertFalse("No down button on first floor", myElevator.pushDown(1));
	}
	
}
