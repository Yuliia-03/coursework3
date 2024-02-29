 
import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Rhizobium is a bacterium found in soil that helps in fixing nitrogen in leguminous plants. 
 * Fun Fact: Rhizobia used for more than 100 years in legume biofertilization [22] are particularly safe for humans, hence its edible!
 *
 * K22019372 - Sanika Gadgil
 * K23098137 - Yuliia Bohak
 */
public class Rhizobium extends Cell
{
    private int age; //to count the number of generations the rhizobium has survived for
    
    private static final Color[] COLORS = {Color.BROWN, Color.PINK, Color.ORANGE, Color.YELLOW, Color.LIGHTGREEN, Color.DARKGREEN, Color.LIGHTBLUE, Color.DARKBLUE, Color.PURPLE};
    
    /**
     * Create a new Haemophilus.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The color of the cell.
     */
    public Rhizobium(Field field, Location location, Color col)
    {
        super(field, location, col);
        this.age = 0; 
        
    }
    
    /**
    * This is how the Rhizobium decides if it's alive or not
    */

    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        if (isAlive()) {
            this.age++;

            if (this.age % 5 == 0 && neighbours.size() < ((this.age/5) * 2)){
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
    
    /**
     * This is how the Rhizobium changes its color based on its age.
     */
    public Color getColor() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        return COLORS[neighbours.size()];
    }
}

    

