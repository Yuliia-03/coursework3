 
import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
/**
 * Write a description of class Haemophilus here.
 *
 */
public class Haemophilus extends Cell
{
    
    /**
     * Constructor for objects of class Haemophilus
     */
    public Haemophilus(Field field, Location location, Color col)
    {
        super(field, location, col);
    }

    /**
     
     */
    public void act()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
    
        if (isAlive()) {
            if (neighbours.size() > 1)
                setNextState(true);
            if (neighbours.size() == 8)
            {
                Random rand = new Random();
                int r = rand.nextInt(256);
                int g = rand.nextInt(256);
                int b = rand.nextInt(256);   
                setColor(Color.rgb(r, g, b));
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
}
