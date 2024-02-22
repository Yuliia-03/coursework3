import javafx.scene.paint.Color;
import java.util.List;
import java.util.stream.Stream;
/**
 * Write a description of class CopyOfDisease here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CopyOfDisease extends Cell
{
    
    protected Cell originalCell;
    private int diseasedTerm;
    
    /**
     * Constructor for objects of class CopyOfDisease
     */
    public CopyOfDisease (Field field, Location location, Cell originalCell)
    {
        super(field, location, Color.RED);
        this.originalCell = originalCell;
    }

    
    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        long healthyCells = Stream.of(neighbours)
                                    .filter(s -> !(s instanceof CopyOfDisease))
                                    .count();
        
        if(healthyCells >= 3 && diseasedTerm < 5){
            recover();
        } else {
            infect(neighbours);
            if(diseasedTerm >= 5) {
                this.setNextState(false);
                originalCell.setNextState(false);
            } else {
                this.setNextState(true);
            }
            
        }
        this.diseasedTerm++;
        //recover();
    }
    
    private void infect(List<Cell> neighbours)
    {
        for(Cell cell: neighbours) {
                Cell newCell = new CopyOfDisease(cell.getField(), cell.getLocation(), cell);
                cell.getField().place(newCell, cell.getLocation());
                setNextState(true);
            }
    }
    
    private void recover()
    {
        this.getField().place(this.originalCell, this.getLocation());
        setNextState(true);
    }
    
    public Cell getOriginalCell(){
        return this.originalCell;
    }
    
}
