import javafx.scene.paint.Color;
import java.util.List;
import java.util.stream.Stream;
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
    }

    
    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        long healthyCells = Stream.of(neighbours)
                                    .filter(s -> !(s instanceof Disease))
                                    .count();
        
        if(healthyCells >= 3 && diseasedTerm < 5){
            recover();
        } else {
            infect(neighbours);
            if(diseasedTerm >= 5) {
                setNextState(false);
            } else {
                setNextState(true);
            }
            diseasedTerm++;
        }
    }
    
    private void infect(List<Cell> neighbours)
    {
        for(Cell cell: neighbours) {
                Cell newCell = new Disease(cell.getField(), cell.getLocation(), cell);
                cell.getField().place(newCell, cell.getLocation());
            }
    }
    
    private void recover()
    {
        this.getField().place(this.originalCell, this.getLocation());
        setNextState(true);
    }
    
    
}
