package Actors;

import Utils.Constants;
import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public abstract class PNJ extends Actor {


    public PNJ(Tile tile, int health, int atk, int def, Vector2D position)
    {
        super(tile, health, atk, def, position);
    }
}
