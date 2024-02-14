 
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
            if (neighbours.size() > 3)
            {
                symbiosis(neighbours);
                setNextState(true);
            }
        }
        else
        { 
            if (neighbours.size() == 2)
            {
                setNextState(true);
            }
        }
    }
    
    public Color getColor()
    {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        if(neighbours.size() == 3)
        {
            Random rand = new Random();
            int r = rand.nextInt(256);
            int g = rand.nextInt(256);
            int b = rand.nextInt(256);   
            return Color.rgb(r, g, b);
        }
        return super.getColor();
    }

    private void symbiosis(List<Cell> neighbours)
    {
        
        int brucellaCells = 0;
        for(Cell cell: neighbours) {
            if(cell instanceof Brucella){
                brucellaCells++;
            }
        }
            
        if(brucellaCells > 3) {
            
            Cell newCell = new Brucella(this.getField(), this.getLocation(), this.getColor());
            this.getField().place(newCell, this.getLocation()); 
        }
        
    }
}
