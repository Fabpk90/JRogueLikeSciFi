package Actors;

import Utils.Constants;
import Utils.Terrain;
import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

public class Actor extends Placeable{

    public enum Direction
    {
        UP(0),
        RIGHT(1),
        DOWN(2),
        LEFT(3);

        private int direction;

        Direction(int direction)
        {
            this.direction = direction;
        }

        public int getDirection() {
            return direction;
        }
    }

    private int health;
    private int atk;
    private int def;

    private Direction direction;

    public Actor()
    {
        super(Tile.ACTOR, new Vector2D());

        this.health = 5;
        this.atk = this.def = 0;

        direction = Direction.UP;
    }

    public Actor(Tile tile, int health, int atk, int def)
    {
        super(tile, new Vector2D());

        this.health = health;
        this.atk = atk;
        this.def =  def;

        direction = Direction.UP;
    }

    public Actor(char glyph, Ansi.Color color, int health, int atk, int def)
    {
        super(glyph, color, new Vector2D());

        this.health = health;
        this.atk = atk;
        this.def =  def;

        direction = Direction.UP;

    }

    public Actor(Tile tile, int health, int atk, int def, Vector2D position)
    {
        super(tile, position);

        this.health = health;
        this.atk = atk;
        this.def =  def;

        direction = Direction.UP;
    }

    public Actor(char glyph, Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(glyph, color, position);

        this.health = health;
        this.atk = atk;
        this.def =  def;

        direction = Direction.UP;
    }

    public Actor(Tile tile, int health, int atk, int def, Vector2D position, Direction direction)
    {
        super(tile, position);

        this.health = health;
        this.atk = atk;
        this.def =  def;

        this.direction = direction;
    }

    public Actor(char glyph, Ansi.Color color, int health, int atk, int def, Vector2D position, Direction direction)
    {
        super(glyph, color, position);

        this.health = health;
        this.atk = atk;
        this.def =  def;

        this.direction = direction;
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

    //moves the player and set his direction according to the movement
    public void move(Vector2D vec)
    {
        getPosition().add(vec);

        if(vec.getX() > 0)
            direction = Direction.RIGHT;
        else if(vec.getX() < 0)
            direction = Direction.LEFT;
        else if(vec.getY() > 0)
            direction = Direction.UP;
        else if(vec.getY() < 0)
            direction = Direction.DOWN;
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
