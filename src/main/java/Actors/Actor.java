package Actors;

import Utils.Constants;
import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Actor extends Placeable{

    private Vector2D position;

    private int health;
    private int atk;
    private int def;

    public Actor()
    {
        super(Constants.CActor, Constants.ACActor);

        this.health = 5;
        this.atk = this.def = 0;
        this.position = Vector2D.getVector2DOne();
    }

    public Actor(char glyph, Ansi.Color color, int health, int atk, int def)
    {
        super(glyph, color);

        this.health = health;
        this.atk = atk;
        this.def =  def;

        position = new Vector2D();
    }

    public Actor(char glyph, Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(glyph, color);

        this.health = health;
        this.atk = atk;
        this.def =  def;

        this.position = position;
    }

    public int getAtk() {
        return atk;
    }

    public int getHealth() {
        return health;
    }

    public int getDef() {
        return def;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
