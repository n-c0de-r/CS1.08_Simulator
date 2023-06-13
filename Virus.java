import java.util.*;

/**
 * A virus in a 2D virusular automaton.
 * The virus has multiple possible states.
 * This is an implementation of the rules for Brian's Brain.
 * @see https://en.wikipedia.org/wiki/Brian%27s_Brain
 * 
 * @author David J. Barnes and Michael Kölling
 * @version  2016.02.29
 */
public class Virus
{
    // The possible states. Assignment 1 change the states
    public static final int ALIVE = 0, DEAD = 1;
    // The number of possible states. Assignment 1 change the number
    public static final int NUM_STATES = 2;

    // The virus's state.
    private int state;
    // The virus's neighbors.
    private Virus[] neighbors;

    /**
     * Set the initial state to be DEAD.
     */
    public Virus()
    {
        this(DEAD);
    }
    
    /**
     * Set the initial state.
     * @param initialState The initial state
     */
    public Virus(int initialState)
    {
        state = initialState;
        neighbors = new Virus[0];
    }
    
    /**
     * Determine this virus's next state, based on the
     * state of its neighbors.
     * This is an implementation of the rules for Brian's Brain.
     * @return The next state.
     */
    public int getNextState()
    {// Assigment 1 reduce states
        // Count the number of neighbors that are alive.
        // Pull the original For loop out, as the number of alive
        // neighbors is needed in every case, it should be done first.
        int aliveCount = 0;
        for(Virus n : neighbors) {
            if(n.getState() == ALIVE) {
                aliveCount++;
            }
        }
        
        if(state == DEAD) {
            return aliveCount == 3 ? ALIVE : DEAD;
        } 
        else /*if(state == DYING)*/ {
            return (aliveCount < 2 || aliveCount > 3) ? DEAD : ALIVE;
        // }
        // else {
            // return DYING;
        }
    }
    
    /**
     * Receive the list of neighboring viruses and take
     * a copy.
     * @param neighborList Neighboring viruses.
     */
    public void setNeighbors(ArrayList<Virus> neighborList)
    {
        neighbors = new Virus[neighborList.size()];
        neighborList.toArray(neighbors);
    }

    /**
     * Get the state of this virus.
     * @return The state.
     */
    public int getState()
    {
        return state;
    }
    
    /**
     * Set the state of this virus.
     * @param The state.
     */
    public void setState(int state)
    {
        this.state = state;
    }   
    
}
