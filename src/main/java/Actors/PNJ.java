package Actors;

import Utils.Vector2D;

public abstract class PNJ extends Actor {


    public PNJ(Tile tile, int health, int atk, int def, Vector2D position, String name)
    {
        super(tile, health, atk, def, position, name);
    }
}
