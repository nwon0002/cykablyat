package starwars.actions;

import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;

/** NEW PART
 * <code>SWAction</code> that lets a <code>SWActor</code> leave an object.
 *
 */

public class Leave extends SWAffordance {
    /**
     * Constructor for the <code>Leave</code> Class. Will initialize the message renderer, the target and
     * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
     *
     * @param theTarget a <code>SWEntity</code> that is being left
     * @param m the message renderer to display messages
     */
    public Leave(SWEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
        priority = 1;
    }


    /**
     * Returns if or not this <code>Leave</code> can be performed by the <code>SWActor a</code>.
     * <p>
     * This method returns true if and only if <code>a</code> is carrying any item.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return 	true if the <code>SWActor</code> is can take this item, false otherwise
     * @see		{@link starwars.SWActor#getItemCarried()}
     */
    @Override
    public boolean canDo(SWActor a) {
        return a.getItemCarried() != null;
    }

    /**
     * Perform the <code>Leave</code> action by setting the item carried by the <code>SWActor</code> to the target (
     * the <code>SWActor a</code>'s item carried would be the target of this <code>Leave</code>).
     * <p>
     * This method should only be called if the <code>SWActor a</code> is alive.
     *
     * @param 	a the <code>SWActor</code> that is leaving the target
     * @see 	{@link #target}
     * @see		{@link starwars.SWActor#isDead()}
     */
    @Override
    public void act(SWActor a) {
        if (target instanceof SWEntityInterface) {
            SWEntityInterface theItem = a.getItemCarried();
            a.setItemCarried(null);

            if (theItem.getSymbol().equals("†")) {
                theItem.removeCapability(Capability.WEAPON); // Lightsaber is no longer a WEAPON
            }

            // get the current location of the actor
            SWLocation location = SWWorld.getEntitymanager().whereIs(a);
            // put the item to the locationof SWActor
            SWWorld.getEntitymanager().setLocation(theItem, location);

            // Remove the Throw affordance
            Affordance[] affordances = theItem.getAffordances();
            for (int i=0; i<affordances.length; i++){
                if(affordances[i] instanceof Throw){
                    theItem.removeAffordance(affordances[i]);
                }
            }

            // the item is dropped, remove Leave affordance
            theItem.removeAffordance(this);
            // someone can take it, add Take affordance
            theItem.addAffordance(new Take(theItem, new MessageRenderer() {
                @Override
                public void render(String message) {
                    theItem.getShortDescription();
                }
            }));
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
        return "Leave " + target.getShortDescription();
    }

}
