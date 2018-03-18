package Utils;

import Actors.Actor;
import Actors.Placeable;

import java.util.Random;

import static org.fusesource.jansi.Ansi.ansi;

public class Terrain {

    private int height;
    private int width;

    private MapData mapData;

    private Actor player;
    //private ArrayList monsters;

    public Terrain(int height, int width)
    {
        this.height = height;
        this.width = width;

        mapData = new MapData(height, width);

        generateMap();
        generateExit();
    }

    private void generateMap()
    {
        mapData = MapGenerator.getMap(height, width);
    }


    //generates a string based on the char matrix and other placeable in the map
    //goes trough the entire matrix and check whether an object is at the position
    //not very good, but works. Handles the case where monsters die, because it generates the entire map each time
    //TODO: Store the actors in the char matrix, remember that if something is on a position, it is a floor
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
        mapData.setTileAt((int)player.getPosition().getX(), (int)player.getPosition().getY(), Placeable.Tile.PLAYER);
    }


}
