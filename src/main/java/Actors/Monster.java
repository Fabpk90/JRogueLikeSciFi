package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Monster extends PNJ
{
    //int exp;
    //items drop;

    public Monster(Tile tile, int health, int atk, int def, Vector2D position)
    {
        super(tile, health, atk, def, position);
    }

    public Monster(char glyph, Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(glyph, color, health, atk, def, position);
    }

    @Override
    void onDie() {

    }
}
