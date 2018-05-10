package Actors;

import Utils.Vector2D;

public class Trap extends Placeable {

    private int damageAmount;

    public Trap(Vector2D position, int damageAmount)
    {
        super(Tile.TRAP, position);
        this.damageAmount = damageAmount;
    }

    public int getDamageAmount()
    {
        return damageAmount;
    }
}
