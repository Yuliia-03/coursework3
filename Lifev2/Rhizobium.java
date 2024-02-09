package Lifev2;
import javafx.scene.paint.Color; 

/**
 * Write a description of class Rhizobium here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Rhizobium extends Cell
{
    private int age;
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
        
        
    }
}
