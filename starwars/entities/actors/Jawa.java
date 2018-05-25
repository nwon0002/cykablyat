package starwars.entities.actors;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.matter.EntityManager;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.actions.Attack;
import starwars.actions.Move;
import starwars.entities.actors.behaviors.AttackInformation;
import starwars.entities.actors.behaviors.AttackNeighbours;

import java.util.ArrayList;
import java.util.List;

/** NEW PART
 * A Jawa.
 *
 * Jawas are small creatures that scavange the planet Tatooine for droids and other scrap material to use
 for trade.
 * They sit inside of the sandcrawler
 */

public class Jawa extends SWActor{


    /**
     * Constructor for the <code>Jawa</code> class. This constructor will,
     * <ul>
     * 	<li>Initialize the message renderer for the <code>Jawa</code></li>
     * 	<li>Initialize the world for this <code>Jawa</code></li>
     *  <li>Initialize the <code>Team</code> for this <code>Jawa</code></li>
     * 	<li>Initialize the hit points for this <code>Jawa</code></li>
     * </ul>
     *
     * @param team the <code>Team</code> to which the this <code>Droid</code> belongs to
     * @param hitpoints the hit points of this <code>Droid</code> to get started with
     * @param m <code>MessageRenderer</code> to display messages.
     * @param world the <code>SWWorld</code> world to which this <code>Droid</code> belongs to
     *
     */
    public Jawa(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
        super(team, hitpoints, m, world);
        this.setSymbol("J");

    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if (this.getHitpoints()<=0) {
            this.shortDescription = "Jawa is dead";
            this.longDescription  = "Jawa is dead";

        } ;
    }

    /**
     * This method will describe this <code>Jawa</code>'s scene and schedule the command accordingly.
     * <p>
     *
     * @see {@link #act()}
     * @see {@link starwars.swinterfaces.SWGridController}
     */
    @Override
    public void act() {

        SWLocation location = world.getEntityManager().whereIs(this);
        List<SWEntityInterface> entities = SWWorld.getEntitymanager().contents(location);

        for (SWEntityInterface e : entities) {
            // If there is a stranger in the interior of sandcrawler
            // attack him
            if( e != this &&
                    (e instanceof SWActor &&
                            ((SWActor) e).getTeam() != this.getTeam())) {
                for (Affordance a : e.getAffordances()) {
                    if (a instanceof Attack) {
                        scheduler.schedule(a, this, 1);
                        break;
                    }
                }
            }
        }

    }
}
