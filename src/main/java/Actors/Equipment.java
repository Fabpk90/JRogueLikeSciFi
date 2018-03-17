package Actors;


import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

//Exceedingly useful class, so much that I would go as far as to say that it embodies the very essence of
//Mankind, Pancakes and irony.

public class Equipment extends Items{

    public Equipment(char glyph, Ansi.Color color, Vector2D position, String name)
    {
        super(glyph, color, position, name);
    }
}
