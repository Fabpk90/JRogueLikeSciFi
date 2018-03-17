package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Helmet extends Equipment {

    public int def;

    public Helmet (char glyph, Ansi.Color color, Vector2D position, String name, int def)
    {
        super(glyph, color, position, name);
        this.def = def;
    }
}
