package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.actions.Throw;

/** NEW PART
 * An entity that has the <code>WEAPON</code> attribute and so can
 * be used to <code>Attack</code> others, etc.
 *
 * @see 	{@link starwars.actions.Attack}
 */
public class Grenade extends SWEntity {

    /**
     * Constructor for the <code>Grenade</code> class. This constructor will,
     * <ul>
     * 	<li>Initialize the message renderer for the <code>Grenade</code></li>
     * 	<li>Set the short description of this <code>Grenade</code> to "a grenade"</li>
     * 	<li>Set the long description of this <code>Grenade</code> to "A grenade that can be thrown"</li>
     * 	<li>Set the hit points of this <code>Grenade</code> to 100</li>
     * 	<li>Add a <code>Take</code> affordance to this <code>Grenade</code> so it can be taken</li>
     *	<li>Add a <code>WEAPON Capability</code> to this <code>Grenade</code> so it can be used to <code>Attack</code></li>
     * </ul>
     *
     * @param m <code>MessageRenderer</code> to display messages.
     *
     * @see {@link starwars.actions.Take}
     * @see {@link starwars.Capability}
     */
    public Grenade(MessageRenderer m) {
        super(m);

        this.shortDescription = "a grenade";
        this.longDescription = "A grenade that can be thrown";
        this.hitpoints = 100; // start with 100

        //add the Take affordance so that the grenade can be picked up
        this.addAffordance(new Take(this, m));

        // add WEAPON capability so that it can be used to attack
        this.capabilities.add(Capability.WEAPON);
    }

    /**
     * A symbol that is used to represent the Grenade on a text based user interface
     *
     * @return 	Single Character string "g"
     * @see 	{@link starwars.SWEntityInterface#getSymbol()}
     */
    public String getSymbol() {
        return "g";
    }

    /**
     * Method insists damage on this <code>Grenade</code> by reducing a certain
     * amount of <code>damage</code> from this <code>SWentity</code> <code>hitpoints</code>
     * <p>
     * This method will also change this <code>Grenade</code>s <code>longDescription</code> to
     * "A grenade is damaged, it cannot be thrown anymore"  and this <code>Grenade</code>s <code>shortDescription</code> to
     * "a damaged grenade" if the <code>hitpoints</code> after taking the damage is zero or less.
     * <p>
     * If the <code>hitpoints</code> after taking the damage is zero or less, this method will remove the
     * <code>WEAPON</code> capabilities from this <code>Grenade</code> since a broken grenade
     * cannot be used to <code>Attack</code>.
     * <p>
     *
     * @param 	damage the amount of <code>hitpoints</code> to be reduced
     * @see 	{@link starwars.actions.Attack}
     */
    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if (this.hitpoints<=0) {
            this.shortDescription = "a damaged grenade";
            this.longDescription  = "A grenade is damaged, it cannot be thrown anymore";

            this.capabilities.remove(Capability.WEAPON);
        }
    }

}
