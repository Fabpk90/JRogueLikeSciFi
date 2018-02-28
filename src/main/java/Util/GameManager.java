package Util;

import java.io.Serializable;

public class GameManager implements Serializable {
    private int height;
    private int width;

    private char [][] charMatrix;

    //private Player player;
    //private ArrayList monsters;

    public GameManager(int height, int width)
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
                str.append(charMatrix[i][j]);
            }
            str.append('\n');
        }

        return str.toString();
    }

    public String getMap(int iterations)
    {
        generateMap(iterations);
        return getStringFromCharMatrix(charMatrix);
    }

}
