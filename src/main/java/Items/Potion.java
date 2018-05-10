package Items;

import Actors.Actor;
import Utils.Vector2D;

public class Potion extends Item implements Usable
{
    private int amount;

    public Potion(Tile tile, Vector2D position, String name, int amount)
    {
        super(tile, position, name);
        this.amount = amount;
    }

    @Override
    public void Use(Actor user)
    {
        user.addHealth(amount);
    }

    @Override
    public String toString()
    {
        return getName() + ": " + amount;
    }
}
