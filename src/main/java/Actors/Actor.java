package Actors;

import Utils.Constants;
import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Actor extends Placeable{

    protected int health;
    protected int atk;
    protected int def;

    public Actor()
    {
        super(Tile.ACTOR, new Vector2D());

        this.health = 5;
        this.atk = this.def = 0;
    }

    public Actor(Tile tile, int health, int atk, int def)
    {
        super(tile, new Vector2D());

        this.health = health;
        this.atk = atk;
        this.def =  def;

    }

    public Actor(char glyph, Ansi.Color color, int health, int atk, int def)
    {
        super(glyph, color, new Vector2D());

        this.health = health;
        this.atk = atk;
        this.def =  def;

    }

    public Actor(Tile tile, int health, int atk, int def, Vector2D position)
    {
        super(tile, position);

        this.health = health;
        this.atk = atk;
        this.def =  def;
    }

    public Actor(char glyph, Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(glyph, color, position);

        this.health = health;
        this.atk = atk;
        this.def =  def;
    }
    
    public boolean Attack(Actor actorAttacked)
    {
       return actorAttacked.takeDamage(atk);
    }
    
    /*
        @returns if the actor is dead
     */
    public boolean takeDamage(int damage)
    {
        if(health - damage > 0)
        {
            health -= damage;
            return false;
        }
        
        onDie();
        
        return true;
    }

    public void onDie()
    {
        //onDie Arrghh
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
