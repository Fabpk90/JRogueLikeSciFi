package Util;

/*

    Uses cellular automata to generate the cave, using the 4-5 rule

    Ver 0.1: Working, but creating orphaned isle

 */

public class MapGenerator
{
    static private int height;
    static private int width;

    static public String getMap(int height, int width, int iterations)
    {
        char mapArrayChar[][] = new char[height][width];

        MapGenerator.height = height;
        MapGenerator.width = width;

        generateWalls(mapArrayChar);

        for (int i = 0; i < iterations; i++) {
            iterateOnMap(mapArrayChar);
        }

        return getStringFromCharMatrix(mapArrayChar);
    }

    private static void iterateOnMap(char[][] mapArrayChar)
    {
        int wallCount;
        for (int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++)
            {
                wallCount = 0;
                if(i - 2 >= 0 && i + 2 <= height - 1 && j - 2 >= 0 && j + 2 <= width - 1) // check for array boundaries
                {
                    //we count surrounding walls
                    // applying 4-5 rule
                    if(mapArrayChar[i - 1][j] == Constants.CWall)
                        wallCount++;
                    if(mapArrayChar[i + 1][j] == Constants.CWall)
                        wallCount++;
                    if(mapArrayChar[i - 2][j] == Constants.CWall)
                        wallCount++;
                    if(mapArrayChar[i + 2][j] == Constants.CWall)
                        wallCount++;
                    if(mapArrayChar[i][j + 1] == Constants.CWall)
                        wallCount++;
                    if(mapArrayChar[i][j - 1] == Constants.CWall)
                        wallCount++;
                    if(mapArrayChar[i][j + 2] == Constants.CWall)
                        wallCount++;
                    if(mapArrayChar[i][j - 2] == Constants.CWall)
                        wallCount++;

                    //we check according to the rule
                    if(wallCount >=5)
                    {
                        mapArrayChar[i][j] = Constants.CWall;
                    }
                    else
                    {
                        mapArrayChar[i][j] = Constants.CFloor;
                    }

                } //make the edges walls
                else if (i  == 0 || i + 1 == height || j == 0 || j + 1 == width)
                    mapArrayChar[i][j] = Constants.CWall;
            }
        }
    }

    static private String getStringFromCharMatrix(char[][] charMatrix)
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

    static private void generateWalls(char [][] map)
    {
        for(int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++) {
                if(Math.random()  < .45)
                    map[i][j] = Constants.CWall;
                else
                    map[i][j] = Constants.CFloor;
            }
        }
    }
}
