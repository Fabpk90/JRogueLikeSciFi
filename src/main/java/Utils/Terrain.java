package Utils;

import Actors.Actor;
import Actors.Placeable;

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

    public void setPlayer(Actor player)
    {
        this.player = player;
    }
}
