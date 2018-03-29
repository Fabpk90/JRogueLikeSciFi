package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Weapon extends Equipment {

    private int atk;
    //public int range; Si on poursuit l'idée des armes à distance on aura besoin de ça

    public Weapon (char glyph, Ansi.Color color, Vector2D position, String name, int atk)
    {
        super(glyph, color, position, name);
        this.atk = atk;
    }

    public int getAtk() {return atk;}

    public void setAtk(int atk) {
        this.atk = atk;
    }
}