package Actor;

public class Player extends Placeable{

    private int health;
    private int atk;
    private int def;

    public Player(char glyph, int health, int atk, int def)
    {
        super(glyph);

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
