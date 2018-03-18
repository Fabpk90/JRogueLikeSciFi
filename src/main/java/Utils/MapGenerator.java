package Utils;

import Actors.Placeable;/*

    Uses cellular automata to generate the cave, using the 4-5 rule

    Ver 0.1: Working, but creating orphaned isle

 */

public class MapGenerator
{
    static private int height;
    static private int width;
    
    static private MapData mapData;

    static public MapData getMap(int height, int width)
    {
        mapData = new MapData(height, width);

        MapGenerator.height = height;
        MapGenerator.width = width;

        generateWalls();

        activeAutomaton(5);

        return mapData;
    }


    static private void activeAutomaton( int iterations)
    {
        int wallCount;
        //used to store the matrix when is being modified
        //so that the algorithm modify generation by generation the map
        Placeable.Tile[][] mapDataCopy = mapData.getmapMatrix().clone();

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
                        if(mapData.getTileAt(i - 1,j).getGlyph() == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(mapData.getTileAt(i + 1,j).getGlyph() == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(mapData.getTileAt(i - 2,j).getGlyph() == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(mapData.getTileAt(i + 2,j).getGlyph() == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(mapData.getTileAt(i,j + 1).getGlyph() == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(mapData.getTileAt(i,j - 1).getGlyph() == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(mapData.getTileAt(i,j + 2).getGlyph() == Placeable.Tile.WALL.getGlyph())
                            wallCount++;
                        if(mapData.getTileAt(i,j - 2).getGlyph() == Placeable.Tile.WALL.getGlyph())
                            wallCount++;

                        if(mapData.getTileAt(i,j).getGlyph() == Placeable.Tile.WALL.getGlyph())
                        {
                            //we check according to the rule
                            if(wallCount >= 5 || wallCount <= 1)
                            {
                                mapDataCopy[i][j] = Placeable.Tile.WALL;
                            }
                            else
                            {
                                mapDataCopy[i][j] = Placeable.Tile.FLOOR;
                            }
                        }
                        else if (mapData.getTileAt(i,j).getGlyph() == Placeable.Tile.FLOOR.getGlyph())
                        {
                            if(wallCount > 5)
                                mapDataCopy[i][j] = Placeable.Tile.WALL;
                        }


                    } //make the edges walls
                    else if (i  == 0 || i + 1 == height || j == 0 || j + 1 == width)
                        mapDataCopy[i][j] = Placeable.Tile.WALL;
                }
            }

            mapData.setmapMatrix(mapDataCopy);
        }

    }

    static private void generateWalls()
    {
        for(int i = 0; i < height; i++)
        {
            for (int j = 0; j < width; j++) {
                if(Math.random()  <= .45)
                    mapData.setTileAt(i, j, Placeable.Tile.WALL);
                else
                    mapData.setTileAt(i, j, Placeable.Tile.FLOOR);
            }
        }
    }
}
