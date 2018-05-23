package starwars.actions;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.entities.Grenade;

import java.util.List;

/** NEW PART
 * <code>SWAction</code> that lets a <code>SWActor</code> throw an object (grenade).
 *
 */

public class Throw extends SWAffordance {
    /**
     * Constructor for the <code>Throw</code> Class. Will initialize the message renderer, the target and
     * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
     *
     * @param theTarget a <code>SWEntity</code> that is being left
     * @param m the message renderer to display messages
     */
    public Throw(SWEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
        priority = 1;
    }


    /**
     * Returns if or not this <code>Throw</code> can be performed by the <code>SWActor a</code>.
     * <p>
     * This method returns true if and only if <code>a</code> is carrying a grenade.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return 	true if the <code>SWActor</code> has a grenade, false otherwise
     * @see		{@link starwars.SWActor#getItemCarried()}
     */
    @Override
    public boolean canDo(SWActor a) {
         if (a.getItemCarried() != null && a.getItemCarried().getSymbol() == "g"){
             return true;
         };
         return false;
    }

    /**
     * Perform the <code>Throw</code> action by damaging actors and other entities,
     * in “rings” of differing severity.
     * <p>
     * This method should only be called if the <code>SWActor a</code> is alive.
     *
     * @param 	a the <code>SWActor</code> that is throwing the target
     * @see 	{@link #target}
     * @see		{@link starwars.SWActor#isDead()}
     */
    @Override
    public void act(SWActor a) {
        if (target instanceof SWEntityInterface) {
            SWLocation location = SWWorld.getEntitymanager().whereIs(a);

            //get the contents of the current location
            List<SWEntityInterface> contents = SWWorld.getEntitymanager().contents(location);
            // Entities in the location where the grenade is thrown lose 20 hitpoints
            if (contents.size() > 1) { // if it is equal to one, the only thing here is this Actor, so there is nothing to damage
                for (SWEntityInterface entity : contents) {
                    if (entity != target || entity != a) { // don't include the actor and grenade
                        entity.takeDamage(20); // lose health
                    }
                }
            }

            for (Grid.CompassBearing d : Grid.CompassBearing.values()) { // iterate through every neighbouring direction
                System.out.println(d);
                // check contents in neighbouring directions in one-step locations
                SWLocation oneStepLocation = (SWLocation) location.getNeighbour(d);

                if (oneStepLocation != null) {
                    //get the contents of the location
                    List<SWEntityInterface> oneStepContents = SWWorld.getEntitymanager().contents(oneStepLocation);
                    // Check for any entity in that location
                    //System.out.println(oneStepLocation);
                    //System.out.println(oneStepContents);
                    if (oneStepContents != null) { // e
                        for (SWEntityInterface entity : oneStepContents) {
                            entity.takeDamage(10); // lose 10 points
                        }
                    }

                }

            }

            }

    }


    /**
     * A String describing what this action will do, suitable for display in a user interface
     *
     * @author ram
     * @return String comprising "leave " and the short description of the target of this <code>Leave</code>
     */
    @Override
    public String getDescription() {
        return "Throw " + target.getShortDescription();
    }

}
