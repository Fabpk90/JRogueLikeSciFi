package Actors;

import Utils.GameManager;
import Utils.Vector2D;

import java.io.Serializable;

public abstract class Actor extends Placeable implements Serializable {

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

    private String name;

    private int health;
    private int atk;
    private int def;
    private int level;

    private Direction direction;

    public Actor()
    {
        super(Tile.ACTOR, new Vector2D());

        this.name = "Actor test";

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


    public Actor(Tile tile, int health, int atk, int def, Vector2D position)
    {
        super(tile, position);

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
    
    public boolean Attack(Actor actorAttacked)
    {
        int trueDamage = atk - actorAttacked.getDef();
        GameManager.addLog(name+" attacks "+actorAttacked.getName() +" doing " +trueDamage+" damages");
        if(trueDamage > 0)
        {
            return actorAttacked.takeDamage(atk);
        }

        return false;
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

    abstract void onDie();

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


    public void addHealth(int amount)
    {
        health += amount;
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

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }
}
