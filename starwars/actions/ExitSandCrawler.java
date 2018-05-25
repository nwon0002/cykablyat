package starwars.actions;

import edu.monash.fit2099.simulator.time.Scheduler;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.entities.Door;
import starwars.entities.actors.SandCrawler;
import starwars.swinterfaces.SWGridController;
import starwars.SWWorld;
import starwars.swinterfaces.SWGridTextInterface;

/** NEW PART =====================
 *
 * <code>SWAction</code> that lets a <code>SWActor</code> to leave a SandCrawler.
 */
public class ExitSandCrawler extends SWAffordance {

    /**
     * Constructor for the <code>ExitSandCrawler</code> Class. Will initialize the message renderer, the target and
     * set the priority of this <code>Action</code> to 1 (lowest priority is 0).
     *
     * @param theTarget a <code>SWEntity</code> that <code>SWActor</code> is going out
     * @param m the message renderer to display messages
     */
    public ExitSandCrawler(SWEntityInterface theTarget, MessageRenderer m) {
        super(theTarget, m);
        priority = 1;
    }

    /**
     * Returns if or not this <code>ExitSandCrawler</code> can be performed by the <code>SWActor a</code>.
     * <p>
     * This method always returns true.
     *
     * @param 	a the <code>SWActor</code> being queried
     * @return 	always true
     * @see		{@link starwars.SWActor#getItemCarried()}
     */
    @Override
    public boolean canDo(SWActor a) {
        return a.getForce() > 0;
    }

    /**
     * Perform the <code>ExitSandCrawler</code> action by placing the <code>SWActor</code> to the target
     * SWWorld grid back.
     * <p>
     * This method should only be called if the <code>SWActor a</code> is alive.
     *
     * @param 	a the <code>SWActor</code> that is exiting the target
     * @see 	{@link #target}
     * @see		{@link starwars.SWActor#isDead()}
     */
    @Override
    public void act(SWActor a) {
        if (target instanceof SWEntityInterface) {
            Door door = (Door) target;
            // Remove the actor from SandCrawler interior
            SWWorld.getEntitymanager().remove(a);

            // Get the location of Sandcrawler on the SWWorld
            SWLocation scLoc = door.getSandcrawlerlocation();

            // Inititlaize the SWWorld grid back
            SWGrid originalGrid = a.getWorld().getGrid();
            SWGridController.changeGrid(originalGrid);
            SWGridTextInterface.changeGrid(originalGrid);

            // Place the actor in the SWWorld grid
            SWWorld.getEntitymanager().setLocation(a, scLoc);

        }
    }

    /**
     * A String describing what this action will do, suitable for display in a user interface
     *
     * @return String comprising "enter " and the short description of the target of this <code>EnterSandCrawler</code>
     */
    @Override
    public String getDescription() {
        return "Exit";
    }

}
