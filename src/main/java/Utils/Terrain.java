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
    }

    private void generateMap(int iterations)
    {
        charMatrix = MapGenerator.getMap(height, width, iterations);
    }


    private String getStringFromCharMatrix(char[][] charMatrix)
    {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if(player.getPosition().getX()==i && player.getPosition().getY()==j)
                {
                    str.append(player.getGlyph());
                }
                else
                    str.append(charMatrix[i][j]);
            }
            str.append('\n');
        }

        return str.toString();
    }

    //Generates and returns the map
    public String getMap()
    {
        generateMap(4);
        return getStringFromCharMatrix(charMatrix);
    }

    public void setPlayer(Actor player)
    {
        this.player = player;
    }
}
