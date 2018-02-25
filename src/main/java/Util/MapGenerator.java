package Util;

/*

    Uses cellular automata to generate the cave, using the 4-5 rule


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
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if(i - 1 >= 0 && i + 1 <= height - 1 && j - 1 >= 0 && j + 1 <= width - 1) // check for array boundaries
                {
                    if(!(mapArrayChar[i][j] == Constants.CWall && mapArrayChar[i - 1][j] == mapArrayChar[i][j]
                    && mapArrayChar[i + 1][j] == mapArrayChar[i][j] && mapArrayChar[i][j + 1] == mapArrayChar[i][j]
                            && mapArrayChar[i][j - 1] == mapArrayChar[i][j]))
                    {
                        mapArrayChar[i][j] = Constants.CFloor;
                    }
                }
            }
        }
    }

    static private String getStringFromCharMatrix(char[][] charMatrix)
    {
        String str = "";

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                str += charMatrix[i][j];
            }
            str += '\n';
        }

        return str;
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
