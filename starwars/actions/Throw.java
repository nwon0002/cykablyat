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
            // get the current location of the actor
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
<<<<<<< HEAD
                // check contents in neighbouring directions
                // One step apart (10 points damage)
||||||| merged common ancestors
                // check contents in neighbouring directions in one-step locations
=======
                System.out.println(d);
                // check contents in neighbouring directions in one-step locations
>>>>>>> 88dc44b23a5ba9d5f4cb4a58e021221f9777acad
                SWLocation oneStepLocation = (SWLocation) location.getNeighbour(d);
<<<<<<< HEAD
                makeDamage(oneStepLocation, 10);

                // Two steps apart (5 points damage)
                if (oneStepLocation != null) { // if the location exists
                    SWLocation twoStepLocation = (SWLocation) oneStepLocation.getNeighbour(d);
                    makeDamage(twoStepLocation, 5);

                    // Check if location is the corner
                    int angle = d.getAngle();
                    if(angle == 45 || angle == 135 || angle == 225 || angle == 315){
                        // Damage the adjacent cells
                        SWLocation location1 = (SWLocation) oneStepLocation.getNeighbour(d.turn(45));
                        SWLocation location2 = (SWLocation) oneStepLocation.getNeighbour(d.turn(315));
                        makeDamage(location1, 5);
                        makeDamage(location2, 5);
||||||| merged common ancestors
                makeDamage(oneStepLocation, 10);

                // Two steps apart
                if (oneStepLocation != null) { // if the location exists
                    SWLocation twoStepLocation = (SWLocation) oneStepLocation.getNeighbour(d);
                    makeDamage(twoStepLocation, 5);

                    // Check if location is the corner
                    int angle = d.getAngle();
                    if(angle == 45 || angle == 135 || angle == 225 || angle == 315){
                        // Damage the adjacent cells
                        SWLocation location1 = (SWLocation) oneStepLocation.getNeighbour(d.turn(45));
                        SWLocation location2 = (SWLocation) oneStepLocation.getNeighbour(d.turn(315));
                        makeDamage(location1, 5);
                        makeDamage(location2, 5);
=======

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
>>>>>>> 88dc44b23a5ba9d5f4cb4a58e021221f9777acad
                    }

                }

            }
<<<<<<< HEAD
;
            a.setItemCarried(null); // the grenade is destroyed

        }
    }
||||||| merged common ancestors
        }
    }
=======
>>>>>>> 88dc44b23a5ba9d5f4cb4a58e021221f9777acad

<<<<<<< HEAD
    private void makeDamage(SWLocation location, int points){
        if (location != null) { // if the location exists
            //get the contents of the location
            List<SWEntityInterface> contents = SWWorld.getEntitymanager().contents(location);
            // Check for any entity in that location
            if (contents != null) { // e
                for (SWEntityInterface entity : contents) {
                    entity.takeDamage(points); // lose health
                }
||||||| merged common ancestors
    public void makeDamage(SWLocation location, int points){
        if (location != null) {
            //get the contents of the location
            List<SWEntityInterface> contents = SWWorld.getEntitymanager().contents(location);
            // Check for any entity in that location
            if (contents != null) { // e
                for (SWEntityInterface entity : contents) {
                    entity.takeDamage(points); // lose 10 points
                }
=======
>>>>>>> 88dc44b23a5ba9d5f4cb4a58e021221f9777acad
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
