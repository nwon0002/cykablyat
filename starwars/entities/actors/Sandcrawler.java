package starwars.entities.actors;

import java.util.ArrayList;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWActor;
import starwars.SWWorld;
import starwars.Team;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.Patrol;

public class Sandcrawler extends SWActor {

    /**
     * Constructor for the <code>Droid</code> class. This constructor will,
     * <ul>
     * 	<li>Initialize the message renderer for the <code>Droid</code></li>
     * 	<li>Initialize the world for this <code>Droid</code></li>
     * </ul>
     *
     * @param m <code>MessageRenderer</code> to display messages.
     * @param world the <code>SWWorld</code> world to which this <code>Droid</code> belongs to
     *
     */
	
	private static Sandcrawler sc = null;
	private Patrol path;
	
	private Sandcrawler(MessageRenderer m, SWWorld world, Direction [] moves) {
		super(Team.NEUTRAL, 50, m, world);
		this.setShortDescription("Jawa Sandcrawler.");
        path = new Patrol(moves);
    }
	
	public static Sandcrawler getSandcrawler(MessageRenderer m, SWWorld world, Direction [] moves) {
		sc = new Sandcrawler(m, world, moves);
//		sc.activate();
		return sc;
	}

	@Override
	public void act() {
		// TODO Auto-generated method stub
		
		Direction newdirection = path.getNext();
		say(getShortDescription() + " moves " + newdirection);
		Move myMove = new Move(newdirection, messageRenderer, world);
		
		scheduler.schedule(myMove, this, 1);
		}

}
