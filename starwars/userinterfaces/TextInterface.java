/**
 * A text-based UI for displaying messages and the simulation grid, and for obtaining user input.
 * 
 * At some stage we might want to replace this UI with a GUI.
 * 
 * @author ram
 * @author Asel
 */
/*
 * Change log
 * 2017-02-02: Displaying the map/grid is now a responsibility of the TextInterface and not of Grid or MiddleWorld (asel)
 * 2017-02-04: Removed the SWGrid parameter from the displayTextGrid method and renamed it to drawGrid() - (asel)
 */
package starwars.userinterfaces;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.userInterface.MapRenderer;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import edu.monash.fit2099.simulator.userInterface.SimulationController;
import starwars.SWActionInterface;
import starwars.SWActor;
import starwars.SWEntityInterface;
import starwars.SWGrid;
import starwars.SWLocation;
import starwars.SWWorld;

/**
 * IMPORTANT
 * This UI is no longer required and is not compatible with the controller. Needs to be deleted! - Asel
 */
public class TextInterface implements MessageRenderer, MapRenderer, SimulationController {
	
	/** Hobbit grid of the world*/
	/** Hobbit grid of the world*/
	private SWGrid grid;
	
	/** The number of items to be displayed per location including the location label and colon ':'*/
	private static int locationWidth = 8;

	/**
	 * Constructor for the Text Interface
	 * <p>
	 * This text interface can be used to display messages, grid and obtain user input in the Text Console
	 * 
	 * @author 	ram
	 * @param 	world The world being considered by the Text Interface
	 * @pre 	world should not be null
	 */
	public TextInterface(SWWorld world) {
		grid = world.getGrid();
	}

	/**
	 * Display the simulation banner.
	 * 
	 * Based on code from the original Eiffel version of this program, and originally 
	 * generated by the UNIX program figlet.
	 * 
	 * @author ram
	 */
	public static void showBanner() {
		String [] lines = { 
			" ____  _              __        __           ",
			"/ ___|| |_ __ _ _ __  \\ \\      / /_ _ _ __ ___",
			"\\___ \\| __/ _` | '__|  \\ \\ /\\ / / _` | '__/ __|",
			" ___) | || (_| | |      \\ \\V  \\V / (_| | |  \\__ \\",
			"|____/ \\__\\__,_|_|       \\_/\\_/ \\__,_|_|  |___/",
			"",
			"A long time ago in a galaxy far, far away",
			"",
			"The laws of physics were compressed onto a",
			"",
			"two-dimensional grid, and time was composed of",
			"",
			"discrete instants.  And some other stuff happened",
			"",
			"but waiting for it to scroll down is boring."};
		
		for(String line: lines) {
			System.out.println(line);
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		
			
			
	

	
	/**
	 * Render the grid and display it in the Text Interface
	 * 
	 * @author ram
	 * @author Asel
	 */
	@Override
	public void render() {
		drawGrid();	
	}

	
	/**
	 * Display a message.  Part of the MessageRenderer interface.
	 * 
	 * @author ram
	 * @param message the String to display
	 */
	@Override
	public void render(String message) {
		System.out.println(message);

	}

	/**
	 * Displays the Hobbit Grid using a System.out.println()
	 * <p>
	 * This method prints the entire Hobbit Grid as a text output. 
	 * Each location will be separated out with vertical seperators '|' and will contain the symbols 
	 * of the locations contents
	 * 
	 * @author 	Asel
	 * @post 	prints the Grid (a matrix of locations with there contents) on the console as text
	 * 
	 */
	private void drawGrid(){
		
		assert (grid!=null)	:"grid to be draw cannot be null";
		
		String buffer = "";
		final int gridHeight = grid.getHeight();
		final int gridWidth  = grid.getWidth();
		
		EntityManager<SWEntityInterface, SWLocation> em = SWWorld.getEntitymanager();
		
	
		for (int row = 0; row< gridHeight; row++){ //for each row
			for (int col = 0; col< gridWidth; col++){ //each column of a row
				
				//current location
				SWLocation loc = grid.getLocationByCoordinates(col, row);
				
				StringBuffer emptyBuffer = new StringBuffer();
				char es = loc.getEmptySymbol(); 
				
				for (int i = 0; i < locationWidth - 3; i++) { 	//add empty symbol character to the buffer
					emptyBuffer.append(es);						//adding 2 less here because one space is reserved for the location symbol
				}									  			//and one more for the colon : used to separate the location symbol and the symbol(s) of the contents of that location
					
				//new buffer buf with a vertical line separator | + symbol of the location + :
				StringBuffer buf = new StringBuffer("|" + loc.getSymbol() + ":"); 
				
				//get the Contents of the location
				List<SWEntityInterface> contents = em.contents(loc);
				
				
				if (contents == null || contents.isEmpty())
					buf.append(emptyBuffer);//add empty buffer to buf to complete the string buffer
				else {
					for (SWEntityInterface e: contents) { //add the symbols of the contents
						buf.append(e.getSymbol());
					}
				}
				buf.append(emptyBuffer); //add the empty buffer again since the symbols of the contents that were added might not actually fill the location upto locationWidth
				
				buf.setLength(locationWidth+1);//set the length of buf to the required locationWidth
				
				buf.append("| ");//add the vertical line seperator to mark the end of that location
				
				buffer += buf; //add the buffer (buf) created for the location to the buffer (the buffer that will eventually be printed)
				
			}
			buffer += "\n"; //new row
		}
		
		System.out.println(buffer); //print the grid on the screen
	}
	
	/**
	 * Display a menu and receive user input.
	 * 
	 * @param a the SWActor to display options for
	 * @return the SWActionInterface that the player has chosen to perform.
	 */
	public static SWActionInterface getUserDecision(SWActor a) {
		Scanner instream = new Scanner(System.in);
		ArrayList<SWActionInterface> cmds = new ArrayList<SWActionInterface>();

		//for all the actions of the Hobbit actor
		for (SWActionInterface ac : SWWorld.getEntitymanager().getActionsFor(a)) {
			if (ac.canDo(a))
				cmds.add(ac);//add the ones the Hobbit Actor can do
		}
		
		Collections.sort(cmds);//sorting the actions for a prettier output

		//construct the commands to be displayed on screen
		for (int i = 0; i < cmds.size(); i++) {
			System.out.println(i + 1 + " " + cmds.get(i).getDescription());
		}

		
		int selection = 0;
		while (selection < 1 || selection > cmds.size()) {//loop until a command in the valid range has been obtained
			System.out.println("Enter command:");
			selection = (instream.nextInt());
		}

	
		return cmds.get(selection-1);//return the action selected
	}
}
