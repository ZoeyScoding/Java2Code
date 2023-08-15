// The CritterInfo interface defines a set of methods for querying the
// state of a critter simulation.  You should not alter this file.  See
// the documentation in the Critter class for a more detailed explanation.

public interface CritterInfo {
    
    public Critter.Neighbor getFront(); // returns neighbor in front of you
    public Critter.Neighbor getBack(); // returns neighbor in back of you
    public Critter.Neighbor getLeft(); // returns neighbor to your left
    public Critter.Neighbor getRight(); // returns neighbor to your right
    public Critter.Direction getDirection(); // returns direction you are facing 
    public Critter.Direction getFrontDirection(); // returns the direction of the neighbor in front of you
    public Critter.Direction getBackDirection(); // returns the direction of the neighbor in back of you
    public Critter.Direction getLeftDirection(); // returns the direction of the neighbor to your left
    public Critter.Direction getRightDirection(); // returns the direction of the neighbor to your right
}
