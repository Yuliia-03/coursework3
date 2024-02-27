import javafx.scene.paint.Color;
import java.util.List;
/**
 * All cells can get Disease. In this class we define the behavior of Diseased cells,
 * conditions for spreading the disease and the condition for recovering.
 * 
 * 
 **/
public class Disease extends Cell
{
    
    protected Cell originalCell;
    private int diseasedTerm;
    
    /**
     * Create a new Diseased Cell.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param originalCell The cell that was diseased.
     */
    public Disease (Field field, Location location, Cell originalCell)
    {
        super(field, location, Color.RED);
        this.originalCell = originalCell;
        this.diseasedTerm = 1;
    }

    /**
     * This is how the Diseased cell decides if it's alive, recover or die
     */
    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        long healthyCells = neighbours.stream()
                                    .filter(s -> !(s instanceof Disease))
                                    .count();
        
        
        if(this.isAlive()){
            if(healthyCells >= 2 && this.diseasedTerm < 3){
                recover();
            } else {
                infect(neighbours);
                if(this.diseasedTerm == 3) {
                    this.originalCell.setNextState(false);
                    this.originalCell.updateState();
                    
                    this.getField().place(this.originalCell, this.getLocation());
                
                    replaceCell(this, this.originalCell);
                    
                    this.originalCell.setUpdated();
                } else {
                    this.setNextState(true);
                }
            
            }
            this.diseasedTerm++;
        }
    }
    
    /**
     * This is where the Diseased cell infect all it's neighbours
     * The Diseased Cell will infect it's neighbours only if the number of
     * healthy cells around it will be less then 3 or cell is diseased
     * for 5 iterations
     */
    private void infect(List<Cell> neighbours)
    {
        for(Cell cell: neighbours) {
            if(!(cell instanceof Disease)){
                Disease newCell = new Disease(cell.getField(), cell.getLocation(), cell);
                
                newCell.setNextState(true);
                newCell.setUpdated();
                cell.getField().place(newCell, cell.getLocation());
                
                replaceCell(cell, newCell);
            }
        }
    }
    
    /**
     * This is where the Diseased cell recover
     * The cell would recover if and only if the number of healthy neighbours is 
     * at least 3 and the cell is diseased for less then 5 iterations 
     */
    private void recover()
    {
        this.getField().place(this.originalCell, this.getLocation());
        this.originalCell.setNextState(true);
        this.originalCell.setUpdated();
        replaceCell(this, this.originalCell);
    }
    
    
    /**
     * This is where the Diseased cells a replaced by original cells when they recover
     * or the healthy cells are replaced by diseased cell during the spreading of the disease
     */
    private void replaceCell(Cell oldCell, Cell newCell) {
        int index = Simulator.getCells().indexOf(oldCell);
        Simulator.updateCells(index, newCell);
    }
}
