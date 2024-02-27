import javafx.scene.paint.Color;
import java.util.List;
/**
 * Write a description of class Disease here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
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
        
        System.out.println(healthyCells);
        if(isAlive()){
            if(healthyCells >= 3 && this.diseasedTerm < 5){
                recover();
            } else {
                infect(neighbours);
                if(this.diseasedTerm >= 5) {
                    this.originalCell.setNextState(false);
                    this.getField().place(this.originalCell, this.getLocation());
                
                    int index = Simulator.getCells().indexOf(this);
                    Simulator.getCells().set(index, this.originalCell);
                } else {
                    this.setNextState(true);
                }
            
            }
            this.diseasedTerm++;
        }
    }
    
    /**
     * This is where the Diseased cell infect all it's neighbours
     * The Diseaased Cell will infect it's neighbours only if the number of
     * healthy cells around it will be less then 3 or cell is diseased
     * for 5 iterations
     */
    private void infect(List<Cell> neighbours)
    {
        for(Cell cell: neighbours) {
            if(!(cell instanceof Disease)){
                Disease newCell = new Disease(cell.getField(), cell.getLocation(), cell);
                cell.getField().place(newCell, cell.getLocation());
                
                int index = Simulator.getCells().indexOf(cell);
                Simulator.getCells().set(index, newCell);
                
                newCell.setNextState(true);
                newCell.setUpdated();
            }
        }
    }
    
    /**
     * This is where the Diseased cell recover
     * The cell would recover if and only if the number of healty neighbours is 
     * at least 3 and the cell is diseased for less then 5 iterations 
     */
    private void recover()
    {
        this.getField().place(this.originalCell, this.getLocation());
        
        int index = Simulator.getCells().indexOf(this);
        Simulator.getCells().set(index, this.originalCell);
        
        originalCell.setNextState(true);
        originalCell.setUpdated();
    }
    
    
}
