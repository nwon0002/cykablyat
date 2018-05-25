package starwars.entities.actors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.actions.Move;
import starwars.actions.Ownership;
import java.util.ArrayList;
import java.util.List;

/** NEW PART
 * A Droid.
 *
 * At this stage, the Droid has no owner, so he will not move anywhere. Once, someone owns him,
 * it starts to follow him or makes random moves.
 **
 */
public class Droid extends SWActor{
    /**the <code>SWActor</code> to which this <code>Droid</code> belongs to
     * Initially null
     * **/
    private SWActor owner = null;

    /**
     * Constructor for the <code>Droid</code> class. This constructor will,
     * <ul>
     * 	<li>Initialize the message renderer for the <code>Droid</code></li>
     * 	<li>Initialize the world for this <code>Droid</code></li>
     *  <li>Initialize the <code>Team</code> for this <code>Droid</code></li>
     * 	<li>Initialize the hit points for this <code>Droid</code></li>
     * </ul>
     *
     * @param team the <code>Team</code> to which the this <code>Droid</code> belongs to
     * @param hitpoints the hit points of this <code>Droid</code> to get started with
     * @param m <code>MessageRenderer</code> to display messages.
     * @param world the <code>SWWorld</code> world to which this <code>Droid</code> belongs to
     *
     */
    public Droid(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
        super(team, hitpoints, m, world);

        this.addAffordance(new Ownership(this, m));
    }

    /**
     * Returns the owner of <code>Droid</code>
     *
     * @return  the actor <code>SWActor</code>
     * @see 	#owner
     */
    public SWActor getOwner() {
        return this.owner;
    }

    /**
     * Assigns this <code>Droid</code> to <code>SWActor</code>
     *
     * This method will set owner <code>SWActor</code> to <code>Droid</code>
     *
     * @param 	a the actor to be set as owner of Droid
     * @see 	#owner
     */
    public void setOwner(SWActor a) {
        this.owner = a;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if (this.getHitpoints()<=0) {
            this.shortDescription = "a droid is immobile";
            this.longDescription  = "A Droid has run out of health. It needs to get fixed";

        } ;
    }

    /**
     * This method will describe this <code>Droid</code>'s scene and schedule the command accordingly.
     * <p>
     *
     * @see {@link #act()}
     * @see {@link starwars.swinterfaces.SWGridController}
     */
    @Override
    public void act() {
        if (this.getHitpoints() > 0) { // enough health
            if (this.owner != null) {
                //get the location of the Droid and describe it
                SWLocation location = SWWorld.getEntitymanager().whereIs(this);
                say(this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription());

                //get the contents of the location
                List<SWEntityInterface> contents = SWWorld.getEntitymanager().contents(location);

                //and find the owner
                boolean ownerFound = false;
                if (contents.size() > 1) { // if it is equal to one, the only thing here is this Droid, so there is nothing to report
                    for (SWEntityInterface entity : contents) {
                        if (entity != this) { // don't include self in scene description
                            if (entity.getSymbol() == this.owner.getSymbol()) {
                                say("A Droid is in the same location as its owner");
                                ownerFound = true;
                                if (location.getSymbol() == 'b') { // check if in the Badlands
                                    this.takeDamage(100); //lose health if in Badlands
                                }
                            }
                        }
                    }
                }

                Direction heading = null; // if owner is not found,
                if (!ownerFound) {          // get the Direction to move
                    boolean found = false;
                    // iterate through every neighbouring direction
                    for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
                        // get locations of neighbouring directions
                        SWLocation checklocation = (SWLocation) location.getNeighbour(d);
                        // check if owner is in that direction
                        if (checklocation != null && checklocation == SWWorld.getEntitymanager().whereIs(this.owner)) {
                            heading = d;    // move to that direction
                            say(getShortDescription() + " has found the owner in " + heading);
                            say(getShortDescription() + " is heading " + heading + " next.");
                            Move myMove = new Move(heading, messageRenderer, world);
                            found = true;       // owner is found in the neighbouring location
                            scheduler.schedule(myMove, this, 1);
                        }
                    }

                    if (!found && Math.random() > 0.55) {  // owner is not found in the neighbouring location
                        ArrayList<Direction> possibledirections = new ArrayList<Direction>();

                        // build a list of available directions
                        for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
                            if (SWWorld.getEntitymanager().seesExit(this, d)) {
                                possibledirections.add(d);
                            }
                        }
                        // randomly choose the Direction to move
                            heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);
                    }
                }
                // ========================
                // Check if new loction is in Badlands
                SWLocation newlocation = (SWLocation) location.getNeighbour(heading);
                if ((newlocation != null && newlocation.getSymbol() == 'b')) {
                    this.takeDamage(100); //lose health if in Badlands
                }

            } else {
                //System.out.println("No owner");
                // no owner - no movement
            }
        } else  { // run out of health; becomes immobile
            say("A Droid is immobile, because it has run out of health ");
        }
    }
}
