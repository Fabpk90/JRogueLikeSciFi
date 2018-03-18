package Utils;

import Actors.Actor;
import Actors.Placeable;

import java.util.Random;

import static org.fusesource.jansi.Ansi.ansi;

public class Terrain {

    private int height;
    private int width;

    private char [][] charMatrix;

    private Actor player;
    //private ArrayList monsters;

    public Terrain(int height, int width)
    {
        this.height = height;
        this.width = width;

        generateMap();
    }

    private void generateMap()
    {
        charMatrix = MapGenerator.getMap(height, width);
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
                if(player.getPosition().getX()==i && player.getPosition().getY()==j)
                {
                    System.out.print(ansi().fg(player.getTile().getColor()).a(player.getTile().getGlyph()));
                }
                else
                    System.out.print(ansi().fg(Placeable.Tile.WALL.getColor()).a(charMatrix[i][j]));

            }
            System.out.print("\n");
        }
    }

    private Vector2D getPlayerRandomposition()
    {
        int floorTiles = 0;
        int i,j;

        Random r = new Random();

        while (true)
        {
            i = r.nextInt(height);
            j = r.nextInt(width);

            //if the selected tile is a wall, do no consider it
            if(getTileAt(i, j) == Placeable.Tile.FLOOR.getGlyph())
            {
                floorTiles++;

                floorTiles += getTileAt(i, j - 1) == Placeable.Tile.FLOOR.getGlyph() ? 1 : 0;
                floorTiles += getTileAt(i, j + 1) == Placeable.Tile.FLOOR.getGlyph() ? 1 : 0;
                floorTiles += getTileAt(i - 1, j) == Placeable.Tile.FLOOR.getGlyph() ? 1 : 0;
                floorTiles += getTileAt(i + 1, j) == Placeable.Tile.FLOOR.getGlyph() ? 1 : 0;

                if(floorTiles > 3)
                    return new Vector2D(i, j);
            }
        }
    }

    public char getTileAt(int x, int y)
    {
        if(x < 0 || x > width
                || y < 0 || y > height)
            return Placeable.Tile.WALL.getGlyph();

        return charMatrix[x][y];
    }

    public void setPlayer(Actor player)
    {
        this.player = player;

        player.setPosition(getPlayerRandomposition());
    }


}
