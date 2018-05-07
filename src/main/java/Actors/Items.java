package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

public class Items extends Placeable implements Serializable {

    private String name;

    public Items(char glyph, Ansi.Color color, Vector2D position, String name)
    {
        super(glyph, color, position);
        this.name = name;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    @Override
    public String toString()
    {
        return name;
    }
}
