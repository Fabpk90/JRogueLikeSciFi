package Actor;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class Actor extends Placeable{

    private int health;
    private int atk;
    private int def;

    public Actor(char glyph, Ansi.Color color, int health, int atk, int def)
    {
        super(glyph, color);

        this.health = health;
        this.atk = atk;
        this.def =  def;
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
