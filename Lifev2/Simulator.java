import javafx.scene.paint.Color; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
 *
 *
 * K22019372 - Sanika Gadgil
 * K23098137 - Yuliia Bohak
 */

public class Simulator {

    private static final double MYCOPLASMA_CELLS = 0.45;
    private static final double CELLS_ALIVE_PROB = 0.5;
    private static final double HAEMOPHILUS_CELLS = 0.25;
    private static final double RHIZOBIUM_CELLS = 0.65;
    private static final double DISEASED_CELLS = 0.25;
    
    private static List<Cell> cells;
    private Field field;
    private int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            if(!cell.getUpdateState()){
                cell.act();
                cell.setUpdated();
            }
            System.out.println(cell.getClass());
        }

        for (Cell cell : cells) {
          cell.updateState();
          cell.setUpdateFalse();
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();
    }

    /**
     * Randomly populate the field live/dead life forms
     */

    private void populate() {
      Random rand = Randomizer.getRandom();
      field.clear();
      
      
      for (int row = 0; row < field.getDepth(); row++) {
        for (int col = 0; col < field.getWidth(); col++) {
          Location location = new Location(row, col);
          
          Double random = rand.nextDouble();
          
          if (random <= HAEMOPHILUS_CELLS) {
            Haemophilus haemo = new Haemophilus(field, location, Color.ORANGE);
            cellCreation(rand, haemo);
            
          } 
          
          else if (random <= MYCOPLASMA_CELLS) {
            Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
            cellCreation(rand, myco);
          } 
          
          else if (random <= RHIZOBIUM_CELLS) {
            Rhizobium rhizo = new Rhizobium(field, location, Color.ORANGE);
            cellCreation(rand, rhizo);
          }
          else
          {
            Brucella bruc = new Brucella(field, location, Color.ORANGE);
            cellCreation(rand, bruc);
            
          }
        }
        }
    }
    
    private static void cellCreation(Random rand, Cell cell){
    
        if (rand.nextDouble() <= CELLS_ALIVE_PROB)
            {
                
                if (rand.nextDouble() <= DISEASED_CELLS)
                {
                    Disease newCell = new Disease(cell.getField(), cell.getLocation(), cell);
                    newCell.getField().place(newCell, newCell.getLocation());
                    cells.add(newCell);
                    
                } else {
                    cells.add(cell);
                }
            }
            else
            {
                cell.setDead();
                cells.add(cell);
            }
    }
    
    /**
     * Return the cells on the field.
     * @return The list of Cells.
     */
    public static List<Cell> getCells(){
        return cells;
    }

    /**
     * 
     */
    public static void updateCells(int index, Cell newCell){
        cells.set(index, newCell);
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    /**
     * Return field
     */
    public Field getField() {
        return field;
    }
    
    /**
     * Return the number of the current generation
     */
    public int getGeneration() {
        return generation;
    }
}
