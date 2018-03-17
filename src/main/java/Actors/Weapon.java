package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Weapon extends Equipment {

    public int atk;
    //public int range; Si on poursuit l'idée des armes à distance on aura besoin de ça

    public Weapon (char glyph, Ansi.Color color, Vector2D position, String name, int atk)
    {
        super(glyph, color, position, name);
        this.atk = atk;
    }
}