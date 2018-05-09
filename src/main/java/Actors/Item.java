package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

public abstract class Item extends Placeable implements Serializable {

    private String name;

    public Item(Tile tile, Vector2D position, String name)
    {
        super(tile, position);
        this.name = name;
    }

    public String getName() {return name;}

    public void equip(Player p) {System.out.println("You can't equip that item");}

    @Override
    public String toString()
    {
        return name;
    }
}
