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
     * Constructor for objects of class Disease
     */
    public Disease (Field field, Location location, Cell originalCell)
    {
        super(field, location, Color.RED);
        this.originalCell = originalCell;
        this.diseasedTerm = 1;
    }

    
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
                //recover();
                //this.setDead();
                this.originalCell.setNextState(false);
                this.getField().place(this.originalCell, this.getLocation());
                
                int index = Simulator.getCells().indexOf(this);
                Simulator.getCells().set(index, this.originalCell);
                //this.originalCell.setNextState(false);
            } else {
                this.setNextState(true);
            }
            
        }
        this.diseasedTerm++;
    }
        //recover();
    }
    
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
    
    private void recover()
    {
        this.getField().place(this.originalCell, this.getLocation());
        
        int index = Simulator.getCells().indexOf(this);
        Simulator.getCells().set(index, this.originalCell);
        
        originalCell.setNextState(true);
        originalCell.setUpdated();
    }
    
    
}
