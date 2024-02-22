import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
/**
 * Write a description of class Brucella here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Brucella extends Cell
{
    private static final double BRUCELLA_ALIVE_PROB = 0.75;
    /**
     * Constructor for objects of class Brucella
     */
    public Brucella(Field field, Location location, Color col)
    {
        super(field, location, Color.BLACK);
    }

    /**
     
     */
    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        Random rand = Randomizer.getRandom();
        
        if (isAlive() && rand.nextDouble() <= BRUCELLA_ALIVE_PROB)  {
            conditionalSurvival();
        }
        else if(!isAlive() && rand.nextDouble() > BRUCELLA_ALIVE_PROB)
        { 
             if (neighbours.size() >= 1 && neighbours.size() <= 4)
             {
                 setNextState(true);
             }
            }
        }
    
 
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
