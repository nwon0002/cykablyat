package starwars.entities;

import edu.monash.fit2099.gridworld.Grid;
import edu.monash.fit2099.simulator.matter.Affordance;
import edu.monash.fit2099.simulator.space.Direction;
import edu.monash.fit2099.simulator.userInterface.MessageRenderer;
import starwars.*;
import starwars.actions.Attack;
import starwars.actions.Move;
import starwars.actions.Ownership;
import starwars.actions.Take;
import java.util.ArrayList;
import java.util.List;

public class Droid extends SWActor{

    /**
     * Constructor for this <code>SWEntity</code>. Will initialize this <code>SWEntity</code>'s
     * <code>messageRenderer</code> and set of capabilities.
     *
     * @param m the <code>messageRenderer</code> to display messages
     */
    private Ownership ownership;

    public Droid(Team team, int hitpoints, MessageRenderer m, SWWorld world) {
        super(team, hitpoints, m, world);

        ownership = new Ownership(this, m);
        this.addAffordance(ownership);
        this.setForce(0);

    }


    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);

        if (this.getHitpoints()<=0) {
            this.shortDescription = "a droid is immobile";
            this.longDescription  = "A Droid has run out of health. It needs to get fixed";

        } ;
    }

    /**
     * A symbol that is used to represent the LightSaber on a text based user interface
     *
     * @return 	A String containing a single character.
     * @see 	{@link starwars.SWEntityInterface#getSymbol()}
     */
    @Override
    public void act() {
        if (this.getHitpoints() > 0) { // enough health
            if (this.ownership.getOwner(this) != null) {
                //get the location of the Droid and describe it
                SWLocation location = SWWorld.getEntitymanager().whereIs(this);
                say(this.getShortDescription() + " [" + this.getHitpoints() + "] is at " + location.getShortDescription());

                //get the contents of the location
                List<SWEntityInterface> contents = SWWorld.getEntitymanager().contents(location);

                //and find the owner
                boolean ownerFound = false;
                if (contents.size() > 1) { // if it is equal to one, the only thing here is this Droid, so there is nothing to report
                    for (SWEntityInterface entity : contents) {
                        if (entity != this) { // don't include self in scene description
                            if (entity.getSymbol() == this.ownership.getOwner(this).getSymbol()) {
                                say("A Droid is in the same location as its owner");
                                ownerFound = true;
                                if (location.getSymbol() == 'b'){ // check if in the Badlands
                                    this.takeDamage(100); //lose health if in Badlands
                                }
                            }
                        }
                    }
                }

                Direction heading = null;

                // Owner is not in the same location
                if(!ownerFound){
                    ArrayList<Grid.CompassBearing> directions = new ArrayList<>();
                    boolean found = false;
                    Grid.CompassBearing n = (Grid.CompassBearing.NORTH);
                    Grid.CompassBearing nw = (Grid.CompassBearing.NORTHWEST);
                    Grid.CompassBearing ne = (Grid.CompassBearing.NORTHEAST);
                    Grid.CompassBearing w =  (Grid.CompassBearing.WEST);
                    Grid.CompassBearing s = (Grid.CompassBearing.SOUTH);
                    Grid.CompassBearing sw = (Grid.CompassBearing.SOUTHWEST);
                    Grid.CompassBearing se = (Grid.CompassBearing.SOUTHEAST);
                    Grid.CompassBearing e = (Grid.CompassBearing.EAST);

                    directions.add(n);
                    directions.add(nw);
                    directions.add(ne);
                    directions.add(w);
                    directions.add(s);
                    directions.add(sw);
                    directions.add(se);
                    directions.add(e);
                    for (int i =0; i<directions.size();i++){
                        SWLocation l = (SWLocation) location.getNeighbour(directions.get(i));
                        if (l != null &&  l == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                            Move myMove = new Move(directions.get(i), messageRenderer, world);
                            found = true;
                            scheduler.schedule(myMove, this, 1);
                        }
                    }
                    /*if (n != null && n == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                        //SWWorld.getEntitymanager().setLocation(this, n);

                        heading = Grid.CompassBearing.NORTH;
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);

                    }else if (nw != null && nw == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                        //SWWorld.getEntitymanager().setLocation(this, nw);
                        heading = Grid.CompassBearing.NORTHWEST;
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);

                    }else if (ne != null && ne == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                        //SWWorld.getEntitymanager().setLocation(this, ne);
                        //say(getShortDescription() + "is heading " + ne + " next.");
                        heading = Grid.CompassBearing.NORTHEAST;
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);

                    }else if (w != null && w == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                        //SWWorld.getEntitymanager().setLocation(this, w);
                        //say(getShortDescription() + "is heading " + w + " next.");
                        heading = Grid.CompassBearing.WEST;
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);

                    }else if (s != null && s == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                        //SWWorld.getEntitymanager().setLocation(this, s);
                        //say(getShortDescription() + "is heading " + s + " next.");
                        heading = Grid.CompassBearing.SOUTH;
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);

                    }else if (sw != null && sw == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                        //SWWorld.getEntitymanager().setLocation(this, sw);
                        //say(getShortDescription() + "is heading " + sw + " next.");
                        heading = Grid.CompassBearing.SOUTHWEST;
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);

                    }else if(se != null && se == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                        //SWWorld.getEntitymanager().setLocation(this, se);
                        //say(getShortDescription() + "is heading " + n + " next.");
                        heading = Grid.CompassBearing.SOUTHEAST;
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);

                    }else if (e != null && e == SWWorld.getEntitymanager().whereIs(this.ownership.getOwner(this))){
                        //SWWorld.getEntitymanager().setLocation(this, e);
                        //say(getShortDescription() + "is heading " + e + " next.");
                        heading = Grid.CompassBearing.EAST;
                        say(getShortDescription() + " is heading " + heading + " next.");
                        Move myMove = new Move(heading, messageRenderer, world);

                        scheduler.schedule(myMove, this, 1);

                    }else{// cannot find the owner; pick a direction at random*/
                        if (!found && Math.random() > 0.55){
                            ArrayList<Direction> possibledirections = new ArrayList<Direction>();

                            // build a list of available directions
                            for (Grid.CompassBearing d : Grid.CompassBearing.values()) {
                                if (SWWorld.getEntitymanager().seesExit(this, d)) {
                                    possibledirections.add(d);
                                }
                            }

                            heading = possibledirections.get((int) (Math.floor(Math.random() * possibledirections.size())));
                            //SWLocation headingloc = (SWLocation) location.getNeighbour(heading);
                            //SWWorld.getEntitymanager().setLocation(this, headingloc);
                            say(getShortDescription() + "i s heading " + heading + " next.");
                            Move myMove = new Move(heading, messageRenderer, world);

                            scheduler.schedule(myMove, this, 1);
                        }
                    //}
                }
                // ========================

                // Check if new loction is in Badlands
                SWLocation newlocation = (SWLocation) location.getNeighbour(heading);
                if ((newlocation != null && newlocation.getSymbol() == 'b' )) {
                    this.takeDamage(100); //lose health if in Badlands
                }

            }else{
                System.out.println("No owner");// no owner
            }
                //dont move
        }else { // run out of health; becomes immobile
            say("A Droid is immobile, because it has run out of health ");
        }
    }
}
