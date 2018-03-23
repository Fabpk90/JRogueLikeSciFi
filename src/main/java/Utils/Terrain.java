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
        mapData = MapGenerator.getMap(height, width);
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

        player.setPosition(getPlayerRandomPosition());
        mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
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

                player.setPosition(getPlayerRandomPosition());
                mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
            }
            else
                mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
        }
    }
}
