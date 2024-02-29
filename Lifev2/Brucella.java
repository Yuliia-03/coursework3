import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
/**
 * Brucella organisms are small aerobic intracellular coccobacilli.
 * 
 * Fun Fact: Brucella bacteria in the milk of infected animals can spread to humans 
 * in unpasteurized milk, ice cream, butter and cheeses.
 *
 * K22019372 - Sanika Gadgil
 * K23098137 - Yuliia Bohak
 */

public class Brucella extends Cell
{
    private static final double BRUCELLA_ALIVE_PROB = 0.75;
    
    /**
     * Create a new Brucella.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The color of the cell.
     */
    public Brucella(Field field, Location location, Color col)
    {
        super(field, location, col);
    }

    /**
     * This is how the Brucella decides if it's alive or not
     */
    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        Random rand = Randomizer.getRandom();
        
        if (isAlive()){
            if(rand.nextDouble() <= BRUCELLA_ALIVE_PROB)  {
                conditionalSurvival();
            }
            else if(neighbours.size() >= 4) {
                setNextState(true);
            }
        } else if(rand.nextDouble() > BRUCELLA_ALIVE_PROB)
        { 
             if (neighbours.size() >= 1 && neighbours.size() <= 4)
             {
                 setNextState(true);
             }
        }
    }
    
    /**
     * This is where the the conditions for the survival of Brussela are determined
     * If the cell is surrounded by at least 4 neighbors located in the north, south, 
     * west and east, then the cell survives
     */
    private void conditionalSurvival()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        if (neighbours.size() >= 4)
        { 
            Location currentLocation = this.getLocation();
            Location[] locations = {new Location(currentLocation.getRow()-1, currentLocation.getCol()),
                                        new Location(currentLocation.getRow()+1, currentLocation.getCol()),
                                        new Location(currentLocation.getRow(), currentLocation.getCol()-1),
                                        new Location(currentLocation.getRow(), currentLocation.getCol()+1)};
            for (int i = 0; i < 4; i++)
            {
                Cell foundCell = null;
                
                for (Cell cell: neighbours)
                {
                        
                    if(cell.getLocation().equals(locations[i]))
                    {
                        foundCell = cell;
                        break;
                    }
                        
                }
                    
                if (foundCell == null)
                {
                    break;
                }
                
                else
                {
                    neighbours.remove(foundCell);
                    
                    if (i == 3) {
                        setNextState(true);
                    }
                }
            }
        }

    }
}
