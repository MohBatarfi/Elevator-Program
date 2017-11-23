package elevator;

/**
 * Interface detailing the operations that we want any implementing class
 * to actually implement for the elevator to work.  Note that the 
 * implementing class can implement as many other methods as it chooses, but
 * it must also implement these.
 * 
 * @author Mohammed Batarfi
 *
 */
public interface ElevatorOperations 
{
	/**
	 * UP, DOWN, and NOT_SET are public properties, accessible
	 * outside of this class by typing (e.g.) ElevatorOperations.UP 
	 * or the class implementing this interface (e.g. if
	 * the class is Elevator, use Elevator.UP for example.)
	 */
	public final static int UP = 1;
	public final static int DOWN = -1;
	public final static int NOT_SET = 0;
	
	/**
	 * Method to simulating pushing the "up" button on the outside
	 * of an elevator on a given floor.
	 * 
	 * @param floor the floor that the button was pushed on
	 * @return true if the button could be pushed or false if it fails
	 */
	public boolean pushUp(int floor);

	/**
	 * Method to simulating pushing the "down" button on the outside
	 * of an elevator on a given floor.
	 * 
	 * @param floor the floor that the button was pushed on
	 * @return true if the button could be pushed or false if it fails
	 */
	public boolean pushDown(int floor);
	
	/**
	 * Method to simulating pushing the floor button on the inside
	 * of an elevator.  Note that it doesn't matter the actual floor
	 * the elevator is on when this button is pushed.
	 * 
	 * @param floor the floor that the person wants to go to
	 * @return true if the button could be pushed or false if it fails
	 */
	public boolean pushIn(int floor);
	
	/**
	 * Move one step in the elevator process.
	 * 
	 * @return the floor the elevator is on after the step
	 */
	public int move();
	
	/**
	 * Get the floor that the elevator is on.  
	 * 
	 * @return the floor the elevator is on
	 */
	public int getFloor();
	
	/**
	 * Get the direction the elevator will move in with the next
	 * call to the move() method, namely UP, DOWN, or NOT_SET.
	 * @return
	 */
	public int getDirection();

}
