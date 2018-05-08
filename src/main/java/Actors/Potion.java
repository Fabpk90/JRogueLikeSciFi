package Actors;

import Utils.Usable;
import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Potion extends Item implements Usable
{
    private int amount;

    public Potion(char glyph, Ansi.Color color, Vector2D position, String name, int amount)
    {
        super(glyph, color, position, name);
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
