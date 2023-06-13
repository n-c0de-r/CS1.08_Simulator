import java.security.SecureRandom;
import java.util.*;

/**
 * Maintain the petri dish for a 2D virusular automaton.
 * 
 * @author David J. Barnes
 * @version  2016.02.29
 */
public class PetriDish
{
    // Default size for the petri dish.
    private static final int DEFAULT_ROWS = 50;
    private static final int DEFAULT_COLS = 50;
    
    // The grid of viruses.
    private Virus[][] viruses;
    // Visualization of the petri dish.
    private final PetriDishView view;

    /**
     * Create an petri dish with the default size.
     */
    public PetriDish()
    {
        this(DEFAULT_ROWS, DEFAULT_COLS);
    }

    /**
     * Create an petri dish with the given size.
     * @param numRows The number of rows.
     * @param numCols The number of cols;
     */
    public PetriDish(int numRows, int numCols)
    {
        setup(numRows, numCols);
        randomize();
        view = new PetriDishView(this, numRows, numCols);
        view.showViruses();
        
        blinker();
    }
    
    /**
     * Run the automaton for one step.
     */
    public void step()
    {
        int numRows = viruses.length;
        int numCols = viruses[0].length;
        // Build a record of the next state of each virus.
        int[][] nextStates = new int[numRows][numCols];
        // Ask each virus to determine its next state.
        for(int row = 0; row < numRows; row++) {
            int[] rowOfStates = nextStates[row];
            for(int col = 0; col < numCols; col++) {
                rowOfStates[col] = viruses[row][col].getNextState();
            }
        }
        // Update the viruses' states.
        for(int row = 0; row < numRows; row++) {
            int[] rowOfStates = nextStates[row];
            for(int col = 0; col < numCols; col++) {
                setVirusState(row, col, rowOfStates[col]);
            }
        }
    }
    
    /**
     * Reset the state of the automaton to all DEAD.
     */
    public void reset()
    {
        int numRows = viruses.length;
        int numCols = viruses[0].length;
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                setVirusState(row, col, Virus.DEAD);
            }
        }
    }
    
    /**
     * Generate a random setup.
     */
    public void randomize()
    {
        int numRows = viruses.length;
        int numCols = viruses[0].length;
        SecureRandom rand = new SecureRandom();
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                setVirusState(row, col, rand.nextInt(Virus.NUM_STATES));
            }
        }
    }
    
    /**
     * Set the state of one virus.
     * @param row The virus's row.
     * @param col The virus's col.
     * @param state The virus's state.
     */
    public void setVirusState(int row, int col, int state)
    {
        viruses[row][col].setState(state);
    }
    
    /**
     * Return the grid of viruses.
     * @return The grid of viruses.
     */
    public Virus[][] getViruses()
    {
        return viruses;
    }
    
    /**
     * Setup a new petri dish of the given size.
     * @param numRows The number of rows.
     * @param numCols The number of cols;
     */
    private void setup(int numRows, int numCols)
    {
        viruses = new Virus[numRows][numCols];
        for(int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                viruses[row][col] = new Virus();
            }
        }
        setupNeighbors();
    }
    
    /**
     * Give to a virus a list of its neighbors.
     */
    private void setupNeighbors()
    {
        int numRows = viruses.length;
        int numCols = viruses[0].length;
        // Allow for 8 neighbors plus the virus.
        ArrayList<Virus> neighbors = new ArrayList<>(9);
        for(int row = 0; row < numRows; row++) {
            for(int col = 0; col < numCols; col++) {
                Virus virus = viruses[row][col];
                // This process will also include the virus.
                for(int dr = -1; dr <= 1; dr++) {
                    for(int dc = -1; dc <= 1; dc++) {
                        // int nr = (numRows + row + dr) % numRows;
                        // int nc = (numCols + col + dc) % numCols;
                        // neighbors.add(viruses[nr][nc]);
                        /* Assignment 2, remove break overs on borders
                           by removing modulo, as it is responsible to
                           carry over states to the other side.*/
                        int nr = row + dr;
                        int nc = col + dc;
                        
                        // But this could lead to mistakes and needs a
                        // guard, so we check for weird values.
                        if(nr >= 0 && nr < numRows &&
                           nc >= 0 && nc < numCols)
                        {
                            neighbors.add(viruses[nr][nc]);
                        }
                    }
                }
                // The neighbours should not include the virus at
                // (row,col) so remove it.
                neighbors.remove(virus);
                virus.setNeighbors(neighbors);
                neighbors.clear();
            }
        }
    }
    
    /**
     * Assignment 3
     * Generate a Blinker Virus
     */
    public void blinker() {
         // Use the built-in methods and abstract them to
         // a general one that simulates a blinker.
         setVirusState(5,5,0);
         setVirusState(5,6,0);
         setVirusState(5,7,0);
    }
    
    // Assignment 4 is a puzzle and can't be found online easily.
    // I skipped the solution to this here.
}
