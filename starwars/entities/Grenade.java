package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.actions.Leave;
import starwars.actions.Take;
import starwars.actions.Throw;

/**
 * An entity that has the <code>WEAPON</code> attribute and so can
 * be used to <code>Attack</code> others, etc.
 *
 * @see 	{@link starwars.entities.Reservoir}
 * @see 	{@link starwars.actions.Chop}
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
        this.hitpoints = 100; // start with a fully charged pistol

        this.addAffordance(new Take(this, m)); //add the Take affordance so that the blaster can be picked up

        //the blaster has capabilities
        this.capabilities.add(Capability.WEAPON);   // and WEAPON so that it can be used to attack
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
     * amount of <code>damage</code> from this <code>Swords</code> <code>hitpoints</code>
     * <p>
     * This method will also change this <code>Grenade</code>s <code>longDescription</code> to
     * "A broken sword that was once gleaming"  and this <code>Grenade</code>s <code>shortDescription</code> to
     * "a broken sword" if the <code>hitpoints</code> after taking the damage is zero or less.
     * <p>
     * If the <code>hitpoints</code> after taking the damage is zero or less, this method will remove the
     * <code>CHOPPER</code> and <code>WEAPON</code> capabilities from this <code>Grenade</code> since a broken sword
     * cannot be used to <code>Chop</code> or <code>Attack</code>.
     * <p>
     *
     * @author 	Asel
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
