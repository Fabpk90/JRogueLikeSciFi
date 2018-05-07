package Actors;

import Utils.GameManager;
import Utils.MapData;
import Utils.Terrain;
import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

import java.util.Random;

public class Monster extends PNJ
{
    //int exp;
    //items drop;

    private Terrain terrain;

    public Monster(Tile tile, int health, int atk, int def, String name, Vector2D position, Terrain terrain)
    {
        super(tile, health, atk, def, position, name);

        this.terrain = terrain;
    }

    @Override
    void onDie()
    {
       terrain.getMapData().setTileAt(getPosition(), Placeable.Tile.FLOOR);
       terrain.removeMonster(this);
    }

    public void wander() //Absolute Rip-off
    {
        Random r = new Random();
        int dir = r.nextInt(4);      //no fucking idea if it's actually equiprobable

        Vector2D vector = new Vector2D();   //The Vector used to check if a wall is already there
        Vector2D vec = new Vector2D();      //The vector used to move the monster's skinny ass

        if(dir == 0)                        //As beautiful as it can get
        {
            vector.add(0,1);
            vec.add(0,1);
        }

        else if(dir == 1)
        {
            vector.add(1,0);
            vec.add(1,0);
        }

        else if(dir == 2)
        {
            vector.add(0,-1);
            vec.add(0,-1);
        }

        else if(dir == 3)
        {
            vector.add(-1,0);
            vec.add(-1,0);
        }

        vector.add(getPosition());

        if(terrain.getMapData().getTileAt(vector) != Placeable.Tile.WALL && terrain.getMapData().getTileAt(vector) != Tile.MONSTER)
        {
            terrain.getMapData().setTileAt(getPosition(), Placeable.Tile.FLOOR);

            move(vec);

            terrain.getMapData().setTileAt(getPosition(), Placeable.Tile.MONSTER);
        }
    }

    //
    //@return false if the player is not around (2 axes check)
    //
    public boolean checkForPlayer()
    {
        int playerFound = 0;

        Vector2D monsterPos = getPosition();

        playerFound += terrain.getMapData().getTileAt(monsterPos.getX() + 1, monsterPos.getY()) == Tile.PLAYER ? 1 : 0;
        playerFound += terrain.getMapData().getTileAt(monsterPos.getX() - 1, monsterPos.getY()) == Tile.PLAYER ? 1 : 0;
        playerFound += terrain.getMapData().getTileAt(monsterPos.getX(), monsterPos.getY() + 1) == Tile.PLAYER ? 1 : 0;
        playerFound += terrain.getMapData().getTileAt(monsterPos.getX(), monsterPos.getY() - 1) == Tile.PLAYER ? 1 : 0;

        System.out.println("player found "+ (playerFound != 0));

        return playerFound != 0;
    }

    @Override
    public boolean Attack(Actor actorAttacked) {
        return super.Attack(actorAttacked);
    }
}
