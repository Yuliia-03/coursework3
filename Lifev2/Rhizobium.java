 
import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Write a description of class Rhizobium here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rhizobium extends Cell
{
    private int age;
    private static final Color[] COLORS = {Color.BROWN, Color.RED, Color.ORANGE, Color.YELLOW, Color.LIGHTGREEN, Color.DARKGREEN, Color.LIGHTBLUE, Color.DARKBLUE, Color.VIOLET};
    
    /**
     * Constructor for objects of class Rhizobium
     */
    public Rhizobium(Field field, Location location, Color col)
    {
        super(field, location, col);
        this.age = 0;
    }

    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        if (isAlive()) {
            this.age++;
            
            if (this.age == 20 && neighbours.size()<8){
                setNextState(false);
            }
            else if (this.age == 15 && neighbours.size()<6) {
                setNextState(false);
            }
            else if (this.age == 10 && neighbours.size()<4) {
                setNextState(false);
            }
            else if (this.age == 5 && neighbours.size()<2) {
                setNextState(false);
            }
            else {
                setNextState(true);
            }
        }
        else
        { 
            if (neighbours.size() == 4)
            {
                setNextState(true);
            }
        }
    }
    
    public Color getColor() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        return COLORS[neighbours.size()];
    }
}
