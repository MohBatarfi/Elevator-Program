package elevator;

/**
 * Elevator class to simulate the operation of an elevator.  Details
 * associated with opening and closing of the elevator doors, entry and
 * exit of people to and from the elevator, the number of people in, 
 * entering or leaving the elevator, and the timing of the movement of
 * the elevator are all "abstracted" out of the problem, encompassing 
 * all of these actions into a single "move()" operation.
 * 
 * @author Mohammed Batarfi
 *
 */
public class Elevator implements ElevatorOperations
{
	private int myNumberOfFloors;
	private int myCurrentFloor;
	private int[] myOutUpButton;
	private int[] myOutDownButton;
	private boolean[] myInnerButton;
	private boolean myMovingUp;
	private boolean myMovingDown;
	private boolean myPushedIn;
	private int myDirection;
	private int myLastButton;
	private int myPosition;
	private int myNextFloor;
	private int mySequence;

	
	/**
	 * Default constructor setting the number of floors for
	 * the elevator to five (5)
	 */
	public Elevator()
	{
		this(5);
	}
	 
	
	
	/**
	 * Constructor setting the number of floors
	 * 
	 * @param numFloors total number of floors
	 */
	public Elevator(int numFloors)
	{
		myNumberOfFloors = numFloors;
		myCurrentFloor = 0; 
		myDirection = NOT_SET; // To know our current direction
		myLastButton = 0; // To determine what is the last button pushed
		mySequence = 0; // To know the sequence of button pushed
		myNextFloor = NOT_SET;
		myMovingUp = false;
		myMovingDown = false;
		myPushedIn = false;
		myOutUpButton = new int [numFloors]; 	
		myOutDownButton = new int [numFloors]; 
		myInnerButton  = new boolean [numFloors];
		for (int i = 0; i < numFloors; i++)
		{
			myInnerButton[i] = false;
			myOutUpButton[i] = -1;
			myOutDownButton[i] = -1;
		}
		
	}

	/**
	 * 
	 * This function checks the direction and set it
	 * either to Up, Down, or not set.
	 * 
	 * @param floor
	 */
	private int setDirection(int floor)
	{
		if(floor > myCurrentFloor) 
		{
			myDirection = UP;
		}
		else if (floor < myCurrentFloor)
		{
			myDirection = DOWN;
		}
		else if (floor == myCurrentFloor)
		{
			myDirection = NOT_SET;
		}
		
		return myDirection;
	}
	
	
	/**
	 * pushUp function knows what floor has pushed up
	 * and store it to the sequence
	 */
	public boolean pushUp(int floor)
	{
		if(floor > 0 && floor < myNumberOfFloors)
		{
			setDirection(floor);
			myOutUpButton[floor-1] = ++mySequence;
			myLastButton = 1; // UP

			return true;
		}

		return false;
	}

	
	/**
	 * pushDown function knows what floor has pushed down
	 * and store it to the sequence
	 */
	public boolean pushDown(int floor)
	{
		if(floor > 1 && floor <= myNumberOfFloors && myOutDownButton[floor-1] != floor-1)
		{
			setDirection(floor);
			myOutDownButton[floor-1] = ++mySequence;
			myLastButton = 2; // Down
			return true;
		}

		return false;
	}
	
	/**
	 * This function checks if there are any buttons pushed
	 * inside the elevator on our way Up or Down
	 * 
	 * @param floor
	 * @return
	 */
	
	public boolean pushIn(int floor)
	{
		// Check valid floor
		if(floor < 1 || floor > myNumberOfFloors) 
		{
			return false;
		}
		else if(myCurrentFloor == floor)
		{
			return false;
		}
		myMovingUp = false;
		myMovingDown = false;
		/* To check out buttons if pressed
		* note: the outside Up and Down buttons has
		* the same length.
		*/
		
		for(int i = 0; i < myOutUpButton.length; i++)
		{
			if(myOutUpButton[i] != -1)
			{
				myMovingUp = true;
			}
			else if (myOutDownButton[i] != -1)
			{
				myMovingDown = true;
			}
		}

		// Check valid direction
		 if(!myMovingUp && myDirection == DOWN & floor > myCurrentFloor)
		{
			return false;
		}
		 if(!myMovingDown && myDirection == UP & floor < myCurrentFloor)
		{
			return false;
		}
		
		 myPushedIn = false;
			// Check if an inner button is pushed
			for(int i = 0; i < myInnerButton.length; i++)
			{
				if(myInnerButton[i] == true)
				{
					myPushedIn = true;
				}
			}
			// if false, we only care about the direction of the out buttons
			
			if(myPushedIn == false)
			{
	            setDirection(floor);
			}
			

			// Check inner buttons
			myInnerButton[floor -1] = true;
			myLastButton = 3; // Inside
			return true;
			
	}
	
	/**
	 * 
	 * This function checks direction when
	 * we reach the last floor up, or down.
	 */
	private void checkValidDirection()
	{
		if(myCurrentFloor == myNumberOfFloors) 
		{
            for (int i = 0; i < myNumberOfFloors; i++) 
            {
                if (myOutDownButton[i] != -1)
                    myDirection = DOWN;
            }
        }
        
        if (myCurrentFloor == 1) 
        {
            for (int i = 0; i < myNumberOfFloors; i++) 
            {
                if (myOutUpButton[i] != -1)
                    myDirection = UP;
            }
        }
        
        /* To check if there is any button pressed in
         * out Up, Down, or Inside (kills direction if no requests)
         */
        boolean killDirection = true;
        for (int i = 0; i < myOutUpButton.length; i++) 
        {
            if (myOutUpButton[i] != -1 || myOutDownButton[i] != -1 || myInnerButton[i])
                killDirection = false;
        }
        if (killDirection && myLastButton == 3)
            myDirection = NOT_SET;
	}
	
	/** 
	 * Check out Up Buttons
	 */
	private void checkUpButtons()
	{
		// Check for out Up buttons above
		for(int i = myPosition; i < myNumberOfFloors; i++)
		{
			if(myOutUpButton[i] != -1)
			{
				// if someone tried to push in down
				if(myPushedIn && myNextFloor < i+1)
				{
					myInnerButton[myNextFloor -1] = false;
					break;
				}
				myOutUpButton[i] = -1; // set the outside buttons to be false
				myNextFloor = i+1; 
				break;
			}
		}
		// Check for out Up buttons below 
		if(myNextFloor == 0)
		{
			for(int i = 0; i <= myPosition; i++)
			{
				if(myOutUpButton[i] != -1)
				{
					if(myPushedIn && myNextFloor < i+1)
					{
						myInnerButton[myNextFloor -1] = false;
						break;
					}
					myOutUpButton[i] = -1;
					myNextFloor = i+1;
					break;
				}
			}
		}
	}
	
	/**
	 * Check out down buttons
	 */
	private void checkDownButtons()
	{
		// Check for out Down buttons below
		for(int i = myPosition -1; i >= 0; i--)
		{
			if(myOutDownButton[i] != -1)
			{
				// if someone tried to push in up
				if(myPushedIn && myNextFloor > i+1)
				{
					myInnerButton[myNextFloor -1] = false;
					break;
				}
				myOutDownButton[i] = -1; // set the outside buttons to be false
				myNextFloor = i+1; 
				break;
			}
		}
		// Check for out Down buttons above 
		if(myNextFloor == 0)
		{
			for(int i = myNumberOfFloors-1; i > myPosition; i--)
			{
				if(myOutDownButton[i] != -1)
				{
					if(myPushedIn && myNextFloor > i+1)
					{
						myInnerButton[myNextFloor -1] = false;
						break;
					}
					myOutDownButton[i] = -1;
					myNextFloor = i+1;
					break;
				}
			}
		}
	}
	
	/**
	 * This function is for moving Up
	 */
	private void movingUp()
	{
		if(myDirection == UP) // Direction is Up
		{
			myPosition = 0;
			myPushedIn = false;
			if(myCurrentFloor != 0)
			{
				myPosition = myCurrentFloor -1;
				
			}
			// Check for inner buttons going up 
			for(int i = myPosition; i < myNumberOfFloors; i++)
			{
				if(myInnerButton[i])
				{
					setDirection(i+1);
					myNextFloor = i+1;
					myPushedIn = true;
					break;
				}
			}
			
			checkUpButtons();
			
			// Check for Down buttons if no Up or inside buttons pressed
			if(myNextFloor == 0)
			{
				for(int i = 0; i < myNumberOfFloors; i++)
				{
					if(myOutDownButton[i] != -1)
					{
						myOutDownButton[i] = -1;
						myNextFloor = i + 1;
						myDirection = DOWN;
						movingDown();
						break;
					}
				}
				
			}
			
			 if (myNextFloor != 0)
			{
				// invalid floor
				if(myInnerButton[myNextFloor-1])
				{
					myInnerButton[myNextFloor-1] = false;
				}
				myCurrentFloor = myNextFloor;
			}
		}
	}
	
	/**
	 * This function is for moving Down
	 */
	private void movingDown()
	{
		if (myDirection == DOWN) // Direction is Down
		{
		//	myPushedIn = false;
			myPosition = myNumberOfFloors -1;
			if(myCurrentFloor != 0)
			{
				myPosition = myCurrentFloor -1;
			}
			// Check for inner buttons going down 
			for(int i = myPosition; i >= 0; i--)
			{
				if(myInnerButton[i] == true)
				{
					setDirection(i+1);
					myNextFloor = i+1;
					myPushedIn = true;
					break;
				}
			}
						
			checkDownButtons();
						
			// Check for Down buttons if no Up or inside buttons pressed
			if(myNextFloor == 0)
			{
				for(int i = 0; i < myNumberOfFloors; i++)
				{
					if(myOutUpButton[i] != -1)
					{
						myOutUpButton[i] = -1;
						myNextFloor = i + 1;
						myDirection = UP;
						movingUp();
						break;
					}
				}
			}
		
		
		else if (myNextFloor != 0)
		{
			// invalid floor
			if(myInnerButton[myNextFloor-1])
			{
				myInnerButton[myNextFloor-1] = false;
			}
			myCurrentFloor = myNextFloor;
		}
	  }
	}
	public int move()
	{
		myNextFloor = 0;
		
		movingUp();
		movingDown();
		checkValidDirection();
		return myCurrentFloor;
	}
	
	public boolean setFloor(int floor)
	{
		return false;
	}
	
	public int getFloor()
	{
		return myCurrentFloor;
	}
	
	public int getDirection()
	{
		return myDirection;
	}

}