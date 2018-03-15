package Utils;

import Actors.Placeable;/*

    Uses cellular automata to generate the cave, using the 4-5 rule

    Ver 0.1: Working, but creating orphaned isle

 */

public class MapGenerator
{
    static private int height;
    static private int width;
    
    static private char [][] charMatrix;

    static public char[][] getMap(int height, int width)
    {
        charMatrix = new char[height][width];

        MapGenerator.height = height;
        MapGenerator.width = width;

        generateWalls();

        iterateOnMap(5);

        return charMatrix;
    }

    static private void iterateOnMap( int iterations)
    {
        int wallCount;
        //used to store the matrix when is being modified
        //so that the algorithm modify generation by generation the map
        char [][] charMatrixCopy = charMatrix.clone();

        for(int iter = 0; iter < iterations; iter++)
        {
            for (int i = 0; i < height; i++)
            {
                for (int j = 0; j < width; j++)
                {
                    wallCount = 0;
                    if(i - 2 >= 0 && i + 2 <= height - 1 && j - 2 >= 0 && j + 2 <= width - 1) // check for array boundaries
                    {
                        //we count surrounding walls
                        // applying 4-5 rule
                        if(charMatrix[i - 1][j] == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(charMatrix[i + 1][j] == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(charMatrix[i - 2][j] == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(charMatrix[i + 2][j] == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(charMatrix[i][j + 1] == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(charMatrix[i][j - 1] == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(charMatrix[i][j + 2] == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(charMatrix[i][j - 2] == Placeable.Tile.WALL.getGlyph())
                            wallCount++;

                        if(charMatrix[i][j] == Placeable.Tile.WALL.getGlyph())
                        {
                            //we check according to the rule
                            if(wallCount >= 5 || wallCount <= 1)
                            {
                                charMatrixCopy[i][j] = Placeable.Tile.WALL.getGlyph();
                            }
                            else
                            {
                                charMatrixCopy[i][j] = Placeable.Tile.FLOOR.getGlyph();
                            }
                        }
                        else if (charMatrix[i][j] == Placeable.Tile.FLOOR.getGlyph())
                        {
                            if(wallCount > 5)
                                charMatrixCopy[i][j] = Placeable.Tile.WALL.getGlyph();
                        }


                    } //make the edges walls
                    else if (i  == 0 || i + 1 == height || j == 0 || j + 1 == width)
                        charMatrixCopy[i][j] = Placeable.Tile.WALL.getGlyph();
                }
            }

            charMatrix = charMatrixCopy;
        }

    }

    static private void generateWalls()
    {
        for(int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++) {
                if(Math.random()  <= .45)
                    charMatrix[i][j] = Placeable.Tile.WALL.getGlyph();
                else
                    charMatrix[i][j] = Placeable.Tile.FLOOR.getGlyph();
            }
        }
    }
}
