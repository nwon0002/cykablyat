package starwars.actions;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.entities.actors.Droid;

/** NEW PART
 * <code>SWAction</code> that lets a <code>SWActor</code> to own a Droid.
 *
 */

public class Ownership extends SWAffordance{

    /**
     * Constructor for the <code>Ownership</code> Class. Will initialize the message renderer, the target and
     * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
     *
     * @param theTarget a <code>SWEntity</code> that is being owned
     * @param m the message renderer to display messages
     */
    public Ownership(SWEntityInterface theTarget, MessageRenderer m){
        super(theTarget, m);
        priority = 1;

    }

    /**
     * A String describing what this action will do, suitable for display in a user interface
     *
     * @return String comprising "own " and the short description of the target of this <code>Leave</code>
     */
    @Override
    public String getDescription() {
        return "own " + target.getShortDescription();
    }

    /**
     * Returns if or not this <code>Ownership</code> can be performed by the <code>SWActor a</code>.
     * <p>
     * This method returns true if and only if <code>Droid</code> is not owned by any <code>SWActor a</code>.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return 	true if the <code>Droid</code> is not owned by anyone, false otherwise
     * @see		{@link Droid#getOwner()}
     */
    @Override
    public boolean canDo(SWActor a) {
        Droid droid = (Droid) target;
        return droid.getOwner() == null;
    }

    /**
     * Perform the <code>Ownership</code> action by setting the <code>SWActor</code> as an owner to the target (
     * the <code>Droid</code>).
     * <p>
     * This method should only be called if the <code>SWActor a</code> is alive.
     *
     * @param 	a the <code>SWActor</code> that is owning the target
     * @see 	{@link #theTarget}
     * @see		{@link starwars.SWActor#isDead()}
     */
    @Override
    public void act(SWActor a) {
        if (target instanceof SWEntityInterface) {
            Droid droid = (Droid) target;
            droid.setOwner(a);  // SWActor a becomes the owner of the droid
            target.removeAffordance(this);  // Only one person can own the droid

            if(a.getTeam()== Team.GOOD){  //change the Team of the droid
                droid.setTeam(Team.GOOD);
            }else if(a.getTeam()==Team.TUSKEN){
                droid.setTeam(Team.TUSKEN);
            }else if(a.getTeam()==Team.EVIL){
                droid.setTeam(Team.EVIL);
            };
            System.out.println(a.getShortDescription() + " owned the Droid");
        }
    }
}

