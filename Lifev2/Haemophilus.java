 
import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
/**
 * Haemophilus influenzae type b (Hib) is a type of bacteria that can cause life-threatening infections. 
 * Fun Fact: Haemophilus influenzae received its name because it was first isolated from the lungs of individuals who died during an 
 * epidemic of influenza virus infection in 1890.
 *
 */
public class Haemophilus extends Cell
{
    
    /**
     * Create a new Haemophilus.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The color of the cell.
     */
    public Haemophilus(Field field, Location location, Color col)
    {
        super(field, location, col);
    }

    /**
     * This is how the Haemophilus decides if it's alive or not
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
    
    /**
     * This is how the Haemophilus changes its color when it bhas exactly 3 neighbours.
     */
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
    
    /**
     * This is how the Haemophilus and Brucella perform Symbiosis (parasitic relationship). 
     * If there are more than 3 Brucella cells surrounding a single Haemophilus cell, then the Haemophilus cell is converted into Brucella.
     */

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
            
            int index = Simulator.getCells().indexOf(this);
            Simulator.getCells().set(index, newCell);
            
            newCell.setUpdated();
            
        }
        
    }
    
}
