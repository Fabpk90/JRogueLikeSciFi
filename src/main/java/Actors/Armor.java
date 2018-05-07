package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Armor extends Equipment {

    private int def;

    public Armor (char glyph, Ansi.Color color, Vector2D position, String name, int def)
    {
        super(glyph, color, position, name);
        this.def = def;
    }

    public int getDef() {return def;}

    public void setDef(int def) {
        this.def = def;
    }

    @Override
    public String toString()
    {
        return getName() + " Def Power: " + def;
    }
}
