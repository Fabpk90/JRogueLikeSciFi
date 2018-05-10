package Items;

import Actors.Actor;
import Utils.NameGenerator;
import Utils.Vector2D;

public class Potion extends Item implements Usable
{
    private int amount;

    public Potion(Tile tile, Vector2D position, int amount)
    {
        super(tile, position, NameGenerator.getRandomPotionName());
        this.amount = amount;
    }

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
