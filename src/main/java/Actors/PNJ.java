package Actors;

import Utils.Constants;
import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public abstract class PNJ extends Actor {


    public PNJ(Tile tile, int health, int atk, int def, Vector2D position)
    {
        super(tile, health, atk, def, position);
    }

    //Si on sait quel glyph utiliser
    public PNJ(char glyph, Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(glyph, color, health, atk, def, position);
    }

    //Si on a pas de glyph particulié a utiliser
    public PNJ(Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(Tile.NPC.getGlyph(), color, health, atk, def, position);
    }
}
