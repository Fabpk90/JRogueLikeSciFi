package Utils;

import Actors.Actor;

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
    public String getMap()
    {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if(player.getPosition().getX()==i && player.getPosition().getY()==j)
                {
                    str.append(player.getTile().getGlyph());
                }
                else
                    str.append(charMatrix[i][j]);
            }
            str.append('\n');
        }

        return str.toString();
    }

    public void setPlayer(Actor player)
    {
        this.player = player;
    }
}
