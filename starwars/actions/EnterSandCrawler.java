package starwars.actions;

import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.entities.actors.SandCrawler;
import starwars.swinterfaces.SWGridController;
import starwars.SWWorld;
import starwars.swinterfaces.SWGridTextInterface;

/** NEW PART =====================
 *
 * <code>SWAction</code> that lets a <code>SWActor</code> to enter a SandCrawler.
 */
public class EnterSandCrawler extends SWAffordance {

    /**
     * Constructor for the <code>EnterSandCrawler</code> Class. Will initialize the message renderer, the target and
     * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
     *
     * @param theTarget a <code>SWEntity</code> that <code>SWActor</code> is entering in
     * @param m the message renderer to display messages
     */
    public EnterSandCrawler(SWEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
        priority = 1;
    }

    /**
     * Returns if or not this <code>EnterSandCrawler</code> can be performed by the <code>SWActor a</code>.
     * <p>
     * This method returns true if and only if <code>a</code> has force ability.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return 	true if the <code>SWActor</code> has a Force
     * @see		{@link starwars.SWActor#getItemCarried()}
     */
    @Override
    public boolean canDo(SWActor a) {
        return a.getForce() > 0;
    }

    /**
     * Perform the <code>EnterSandCrawler</code> action by setting removing <code>SWActor</code>
     * from this grid and placing him inside the SAndCrawler.
     * <p>
     * This method should only be called if the <code>SWActor a</code> is alive.
     *
     * @param 	a the <code>SWActor</code> that is entering the target
     * @see 	{@link #target}
     * @see		{@link starwars.SWActor#isDead()}
     */
    @Override
    public void act(SWActor a) {
        if (target instanceof SWEntityInterface) {
            SandCrawler sc = (SandCrawler) target;
            // Remove the actor from the current world
            SWWorld.getEntitymanager().remove(a);

            // Inititlaize a Sandcrawler's interior grid
            SWGrid interiorGrid = sc.getInteriorGrid();
            SWGridController.changeGrid(interiorGrid);
            SWGridTextInterface.changeGrid(interiorGrid);
            // Place the actor in the new grid
            SWLocation location = interiorGrid.getLocationByCoordinates(0,0);
            SWWorld.getEntitymanager().setLocation(a, location);

        }
    }

    /**
     * A String describing what this action will do, suitable for display in a user interface
     *
     * @return String comprising "enter " and the short description of the target of this <code>EnterSandCrawler</code>
     */
    @Override
    public String getDescription() {
        return "Enter " + target.getShortDescription();
    }

}
