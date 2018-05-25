package starwars.entities.actors;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.actions.Attack;
import starwars.actions.EnterSandCrawler;
import starwars.actions.Move;
import starwars.entities.Door;
import starwars.entities.actors.behaviors.Patrol;
import starwars.swinterfaces.SWGridController;

/** NEW PART =====================
 *  Sandcrawler is a vehicle used by Jawas to scavange for droids
 **/

public class SandCrawler extends SWActor {

    private Patrol path;

    private SWGrid interiorGrid;

    private Jawa jawa;

    private Door door;

    /**
     * Constructor for the <code>SandCrawler</code> class. This constructor will,
     * <ul>
     * 	<li>Initialize the message renderer for the <code>SandCrawler</code></li>
     * 	<li>Initialize the world for this <code>SandCrawler</code></li>
     * </ul>
     *
     * @param m <code>MessageRenderer</code> to display messages.
     * @param world the <code>SWWorld</code> world to which this <code>SandCrawler</code> belongs to
     *
     */
    public SandCrawler(MessageRenderer m, SWWorld world, Direction [] moves) {
        super(Team.NEUTRAL, 50, m, world);

        this.setShortDescription("Sandcrawler");
        this.setLongDescription("Sandcrawler - vehicle that scavanges the planet Tatooine for droids");

        path = new Patrol(moves);

        // Someone can enter the Sandcrawler
        this.addAffordance(new EnterSandCrawler(this, m));

        // Create a new grid for the sandcrawler
        SWLocation.SWLocationMaker factory = SWLocation.getMaker();
        interiorGrid = new SWGrid(factory, 4, 4);
        SWLocation loc;
        for (int row=0; row < interiorGrid.getHeight(); row++) {
            for (int col=0; col < interiorGrid.getWidth(); col++) {
                loc = interiorGrid.getLocationByCoordinates(col, row);
                loc.setLongDescription("Sandcrawler Interior (" + col + ", " + row + ")");
                loc.setShortDescription("Sandcrawler Interior (" + col + ", " + row + ")");
                loc.setSymbol('I');
            }
        }

        // There is a Jawa inside the Sandcrawler
        loc =interiorGrid.getLocationByCoordinates(1,1);
        jawa = new Jawa(Team.JAWA, 100, m, world);
        jawa.addAffordance(new Attack(jawa, m));
        jawa.setShortDescription("Jawa");
        jawa.setShortDescription("Jawa");
        SWWorld.getEntitymanager().setLocation(jawa, loc);

        // Place a Door to exit the Sandcrawler
        door = new Door(m);
        // Stores the location of the sandcrawler
        door.setSandcrawlerlocation(SWWorld.getEntitymanager().whereIs(this));
        SWLocation doorloc = interiorGrid.getLocationByCoordinates(0,1);
        SWWorld.getEntitymanager().setLocation(door, doorloc);
    }

    /**
     * Returns the interior grid of <code>SandCrawler</code> on the World
     *
     * @return  the grid <code>SWGrid</code>
     * @see 	#interiorGrid
     */
    public SWGrid getInteriorGrid() {
        return interiorGrid;
    }

    /**
     * This method will describe this <code>SandCrawler</code>'s scene and schedule the command accordingly.
     * <p>
     *
     * @see {@link #act()}
     * @see {@link starwars.swinterfaces.SWGridController}
     */
    @Override
    public void act() {
        if (this.getHitpoints() > 0) {
            Direction newdirection = path.getNext();
            if (newdirection != null) { // Move every second turn

                //get the location of the SandCrawler and describe it
                SWLocation location = SWWorld.getEntitymanager().whereIs(this);
                SWLocation nextlocation = (SWLocation) location.getNeighbour(newdirection);
                door.setSandcrawlerlocation(nextlocation);

                say(this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription());

                //get the contents of the location
                List<SWEntityInterface> contents = SWWorld.getEntitymanager().contents(location);

                if (contents.size() > 1) { // if it is equal to one, the only thing here is this SandCrawler, so there is nothing to report
                    for (SWEntityInterface entity : contents) {
                        if (entity != this) { // don't include self in scene description
                            if (entity.getSymbol() == "D") { // If found Droid
                                say(this.getShortDescription() + " found A Droid");
                                Droid droid = (Droid) entity;
                                // Jawa pilot inside the SandCrawler is owner
                                droid.setOwner(jawa);
                                // Remove from the world
                                SWWorld.getEntitymanager().remove(droid);
                                // Randomly put it inside the SandCrawler
                                SWLocation loc = interiorGrid.getLocationByCoordinates((int) (Math.floor(Math.random() * 4)), (int) (Math.floor(Math.random() * 4)));
                                SWWorld.getEntitymanager().setLocation(droid, loc);
                                say(this.getShortDescription() + " has taken A Droid");

                            }
                        }
                    }
                }
                say(getShortDescription() + " is heading " + newdirection + " next.");
                Move myMove = new Move(newdirection, messageRenderer, world);

                scheduler.schedule(myMove, this, 1);
            }
        }else{
            say("A Sandcrawler is broken, it cannot move");
        }
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if (this.getHitpoints() <= 0){
            this.setShortDescription("Immoblie Sandcrawler");
            this.setLongDescription("Sandcrawler is broken");
        }
    }
}