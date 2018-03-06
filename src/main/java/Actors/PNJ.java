package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

import static Utils.Constants.CActor;

public class PNJ extends Actor {

    //int exp;
    //items drop;

    //Si on sait quel glyph utiliser
    public PNJ(char glyph, Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(glyph, color, health, atk, def, position);
    }

    //Si on a pas de glyph particulié a utiliser
    public PNJ(Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(CActor, color, health, atk, def, position);
    }

    //Par défaut absolu
    public PNJ()
    {
        super();
    }

}
