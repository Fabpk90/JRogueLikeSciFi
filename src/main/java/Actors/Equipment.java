package Actors;


import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

//Exceedingly useful class, so much that I would go as far as to say that it embodies the very essence of
//Mankind, Pancakes and irony.

public abstract class Equipment extends Item
{

    public Equipment(Tile tile, Vector2D position, String name)
    {
        super(tile, position, name);
    }
}
