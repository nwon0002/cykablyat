package starwars.entities;

import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.Capability;
import starwars.SWEntity;
import starwars.SWLocation;
import starwars.actions.ExitSandCrawler;
import starwars.actions.Leave;
import starwars.actions.Take;
/** NEW PART =====================
 *
 * An entity that is inside the Sandcrawler, it is used to
 * leave a SandCrawler.
 *
 **/
public class Door extends SWEntity {

    /* Keeps track of the sandcrawler's location on SWWorld,
     so the actor can be returned to that location*/
    private SWLocation sandcrawlerlocation;

    /**
     * Constructor for the <code>Door</code> class. This constructor will,
     * <ul>
     * 	<li>Initialize the message renderer for the <code>Door</code></li>
     * 	<li>Set the short description of this <code>Door</code> to "a Door"</li>
     * 	<li>Set the long description of this <code>Door</code> to "A Door to leave a Sandcrawler"</li>
     * 	<li>Set the hit points of this <code>Door</code> to 100</li>
     * 	<li>Add a <code>ExitSandCrawler</code> affordance to this <code>Door</code> so it used to leave </li>
     * </ul>
     *
     * @param m <code>MessageRenderer</code> to display messages.
     *
     * @see {@link starwars.actions.ExitSandCrawler}
     */
    public Door(MessageRenderer m) {
        super(m);

        this.shortDescription = "Exit";
        this.longDescription = "An Exit Door to leave a Sandcrawler";
        this.hitpoints = 100; // start with a fully charged pistol

        //add Exit affordance, so the door can de used to leave the sandcrowler
        this.addAffordance(new ExitSandCrawler(this, m));

    }

    /**
     * Returns the location of <code>SandCrawler</code> on the World
     *
     * @return  the location <code>SWLocation</code>
     * @see 	#sandcrawlerlocation
     */
    public SWLocation getSandcrawlerlocation() {
        return sandcrawlerlocation;
    }

    /**
     * Set the location of <code>SandCrawler</code> on the World
     *
     * @return  the location <code>SWLocation</code>
     * @see 	#sandcrawlerlocation
     */
    public void setSandcrawlerlocation(SWLocation sandcrawlerlocation) {
        this.sandcrawlerlocation = sandcrawlerlocation;
    }

    /**
     * A symbol that is used to represent the Door on a text based user interface
     *
     * @return 	Single Character string "E"
     * @see 	{@link starwars.SWEntityInterface#getSymbol()}
     */
    public String getSymbol() {
        return "E";
    }

}
