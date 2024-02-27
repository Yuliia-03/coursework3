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
 */

public class Simulator {

    private static final double MYCOPLASMA_CELLS = 0.55;
    private static final double CELLS_ALIVE_PROB = 0.5;
    private static final double HAEMOPHILUS_CELLS = 0.45;
    private static final double RHIZOBIUM_CELLS = 0.35;
    private static final double DISEASED_CELLS = 0.5;
    
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
          
          if (rand.nextDouble() <= HAEMOPHILUS_CELLS) {
            Haemophilus haemo = new Haemophilus(field, location, Color.ORANGE);
            
            if (rand.nextDouble() <= CELLS_ALIVE_PROB)
            {
                
                if (rand.nextDouble() <= DISEASED_CELLS)
                {
                    Disease newCell = new Disease(haemo.getField(), haemo.getLocation(), haemo);
                    newCell.getField().place(newCell, newCell.getLocation());
                    cells.add(newCell);
                    
                } else {
                    cells.add(haemo);
                }
            }
            else
            {
                haemo.setDead();
                cells.add(haemo);
            }
            
          }
          else 
          {
            Brucella bruc = new Brucella(field, location, Color.ORANGE);
            
            if (rand.nextDouble() <= CELLS_ALIVE_PROB)
            {
                
                if (rand.nextDouble() <= DISEASED_CELLS)
                {
                    Disease newCell = new Disease(bruc.getField(), bruc.getLocation(), bruc);
                    newCell.getField().place(newCell, newCell.getLocation());
                    cells.add(newCell);
                    
                } else {
                    cells.add(bruc);
                }
            }
            else
            {
                bruc.setDead();
                cells.add(bruc);
            }
            
          }
          
          if (rand.nextDouble() <= RHIZOBIUM_CELLS) {
            Rhizobium rhizo = new Rhizobium(field, location, Color.ORANGE);
            if (rand.nextDouble() <= CELLS_ALIVE_PROB)
            {
                cells.add(rhizo);
            } 
            else {
                rhizo.setDead();
                cells.add(rhizo);
            }
          }
          
          if (rand.nextDouble() <= MYCOPLASMA_CELLS) {
            Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
            if (rand.nextDouble() <= CELLS_ALIVE_PROB)
            {
                cells.add(myco);
            } 
            else {
                myco.setDead();
                cells.add(myco);
            }
          }
    }
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
     * Update the cells on the field.
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
