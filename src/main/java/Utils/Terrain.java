package Utils;

import Actors.Actor;
import Actors.Monster;
import Actors.Placeable;

import java.util.ArrayList;
import java.util.Random;

import static org.fusesource.jansi.Ansi.ansi;

public class Terrain {

    private int height;
    private int width;

    private MapData mapData;

    private Actor player;
    private ArrayList<Monster> monsters;

    public Terrain(int height, int width)
    {
        this.height = height;
        this.width = width;

        mapData = new MapData(height, width);

        monsters = new ArrayList<Monster>();

        generateMap();
        generateExit();
    }

    private void generateMap()
    {
        mapData = MapGenerator.getMap(height, width, mapData.getLevel(), monsters);
        mapData.setLevel(mapData.getLevel() + 1);
    }


    //goes trough the entire matrix and
    public void printMap()
    {
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                System.out.print(ansi().fg(mapData.getTileAt(i,j).getColor()).a(mapData.getTileAt(i,j)));
            }
            System.out.print("\n");
        }
    }

    //Randomly place the player in the map
    private Vector2D getPlayerRandomPosition()
    {
        int floorTiles = 0;
        int i,j;

        Random r = new Random();

        while (true)
        {
            i = r.nextInt(height);
            j = r.nextInt(width);

            //if the selected tile is a wall, do no consider it
            if(mapData.getTileAt(i, j) == Placeable.Tile.FLOOR)
            {
                floorTiles++;

                floorTiles += mapData.getTileAt(i, j - 1) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i, j + 1) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i - 1, j) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i + 1, j) == Placeable.Tile.FLOOR ? 1 : 0;

                if(floorTiles > 3)
                    return new Vector2D(i, j);
            }
        }
    }

    //generate an exit by searching a "free" Tile from the bottom
    //It starts searching from the opposite position of the player -> the bottom
    private void generateExit()
    {
        int floorTiles = 0;
        boolean found = false;

        for(int i = height - 1; i > 0 && !found; i--)
        {
            for(int j = 0; j < width && !found; j++)
            {
                if(mapData.getTileAt(i, j) == Placeable.Tile.FLOOR)
                {
                    floorTiles++;

                    floorTiles += mapData.getTileAt(i, j - 1) == Placeable.Tile.FLOOR ? 1 : 0;
                    floorTiles += mapData.getTileAt(i, j + 1) == Placeable.Tile.FLOOR ? 1 : 0;
                    floorTiles += mapData.getTileAt(i - 1, j) == Placeable.Tile.FLOOR ? 1 : 0;
                    floorTiles += mapData.getTileAt(i + 1, j) == Placeable.Tile.FLOOR ? 1 : 0;

                    if(floorTiles > 3)
                    {
                        mapData.setTileAt(i, j, Placeable.Tile.EXIT);
                        found = true;
                    }

                }
            }
        }
    }

    public void setPlayer(Actor player)
    {
        this.player = player;

        resetPlayerPosition();
    }

    public void movePlayer(Vector2D vec)
    {
        Vector2D vector = new Vector2D(vec.getX(), vec.getY());
        vector.add(player.getPosition());

        if(mapData.getTileAt(vector) != Placeable.Tile.WALL)
        {
            mapData.setTileAt(player.getPosition(), Placeable.Tile.FLOOR);
            player.move(vec);
            if(mapData.getTileAt(player.getPosition()) == Placeable.Tile.EXIT)
            {
                generateMap();
                generateExit();

                resetPlayerPosition();
            }
            else
                mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
        }
    }

    public void updateMonsters()
    {
        for(Monster m : monsters)
        {
            wander(m);
        }
    }

    private void wander(Monster mon) //Absolute Rip-off
    {
        Random r = new Random();
        int dir;      //no fucking idea if it's actually equiprobable

        Vector2D vector = new Vector2D();   //The Vector used to check if a wall is already there
        Vector2D vec = new Vector2D();      //The vector used to move the monster's skinny ass

        //correcting your fucking dumbass "equiprobable" shit code
        while(mapData.getTileAt(vector) == Placeable.Tile.WALL
                || mapData.getTileAt(vector) == Placeable.Tile.PLAYER)
        {
            vector = Vector2D.getVector2DZero();
            vec = Vector2D.getVector2DZero();

            dir = r.nextInt(4);
            if(dir == 0)                        //As beautiful as it can get
            {
                vector.add(0,1);
                vec.add(0,1);
            }

            if(dir == 1)
            {
                vector.add(1,0);
                vec.add(1,0);
            }

            if(dir == 2)
            {
                vector.add(0,-1);
                vec.add(0,-1);
            }

            if(dir == 3)
            {
                vector.add(-1,0);
                vec.add(-1,0);
            }

            vector.add(mon.getPosition());
        }

        mapData.setTileAt(mon.getPosition(), Placeable.Tile.FLOOR);
        mon.move(vec);

        mapData.setTileAt(mon.getPosition(), Placeable.Tile.MONSTER);

    }

    private void resetPlayerPosition()
    {
        player.setPosition(getPlayerRandomPosition());
        mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
    }
}
