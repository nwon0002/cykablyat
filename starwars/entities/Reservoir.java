package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.SWAffordance;
import starwars.SWEntity;
import starwars.SWWorld;
import starwars.actions.Dip;

/**
 * Class to represent a water reservoir.  <code>Reservoirs</code> are currently pretty passive.
 * They can be dipped into to fill fillable entities (such as <code>Canteens</code>.  They
 * are assumed to have infinite capacity.
 *
 * @author 	ram
 * @see 	{@link starwars.entities.Canteen}
 * @see {@link starwars.entites.Fillable}
 * @see {@link starwars.actions.Fill} 
 */
public class Reservoir extends SWEntity {

	/**
	 * Constructor for the <code>Reservoir</code> class. This constructor will,
	 * <ul>
	 * 	<li>Initialize the message renderer for the <code>Reservoir</code></li>
	 * 	<li>Set the short description of this <code>Reservoir</code> to "a water reservoir</li>
	 * 	<li>Set the long description of this <code>Reservoir</code> to "a water reservoir..."</li>
	 * 	<li>Add a <code>Dip</code> affordance to this <code>Reservoir</code> so it can be taken</li> 
	 *	<li>Set the symbol of this <code>Reservoir</code> to "T"</li>
	 * </ul>
	 *
	 * @param 	m <code>MessageRenderer</code> to display messages.
	 * @see 	{@link starwars.actions.Dip} 
	 */
	public Reservoir(MessageRenderer m) {
		super(m);
		SWAffordance dip = new Dip(this, m);
		this.addAffordance(dip);

		this.hitpoints = 40;
		this.setLongDescription("a water reservoir.");
		this.setShortDescription("a water reservoir, full of cool, clear, refreshing water");
		this.setSymbol("W");
	}

	@Override
	public String getShortDescription() {
		if (this.hitpoints < 0){
			this.setShortDescription("the wreckage of a water reservoir");
			this.setLongDescription("a damaged water reservoir, leaking slowly.");
			this.setSymbol("X");
		}else if (0 < this.hitpoints && this.hitpoints < 20){
			this.setShortDescription("damaged water reservoir");
			this.setLongDescription("the wreckage of a water reservoir.");
			this.setSymbol("V");
		}
		return shortDescription;
	}

	public String getLongDescription() {

		if (this.hitpoints < 0){
			this.setShortDescription("the wreckage of a water reservoir");
			this.setLongDescription("a damaged water reservoir, leaking slowly.");
			this.setSymbol("X");
		}else if (0 < this.hitpoints && this.hitpoints < 20){
			this.setShortDescription("damaged water reservoir");
			this.setLongDescription("the wreckage of a water reservoir.");
			this.setSymbol("V");
		}
		return longDescription;
	}

	/**
	 * Reservoir are destructible
	 * @param damage - the amount of damage that would be inflicted on a non-mystical Entity
	 */
	@Override
	public void takeDamage(int damage) {
		super.takeDamage(damage);

        if (this.hitpoints <= 0){
			this.setShortDescription("the wreckage of a water reservoir");
			this.setLongDescription("a damaged water reservoir, leaking slowly.");
			this.setSymbol("X");
		}else if (0 <= this.hitpoints && this.hitpoints <= 20){
			this.setShortDescription("damaged water reservoir");
			this.setLongDescription("the wreckage of a water reservoir.");
			this.setSymbol("V");
		}

    }

}
