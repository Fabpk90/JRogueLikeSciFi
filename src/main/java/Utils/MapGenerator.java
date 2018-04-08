package Utils;

import Actors.Monster;
import Actors.Placeable;/*

/*

    Uses cellular automata to generate the cave, using the 4-5 rule

    Ver 0.1: Working, but creating orphaned isle

 */

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator
{
    static private int size;

    static  private int level;
    
    static private MapData mapData;

    static public MapData getMap(int size, int level, ArrayList<Monster> monsters)
    {
        mapData = new MapData(size);

        MapGenerator.size = size;
        MapGenerator.level = level;

        monsters.clear();

        generateWalls();

        activeAutomaton(5);

        generateMonsters(monsters);

        return mapData;
    }

    private static void generateMonsters(ArrayList<Monster> monsters)
    {
        Random r = new Random();

        int placed = 0;
        int toPlace = r.nextInt(level+1 )+1;

        int x,y;

        while (placed != toPlace)
        {
            x = r.nextInt(size);
            y = r.nextInt(size);

            if(mapData.getTileAt(x,y) == Placeable.Tile.FLOOR)
            {
                placed++;

                mapData.setTileAt(x,y, Placeable.Tile.MONSTER);
                monsters.add(new Monster(Placeable.Tile.MONSTER, 10 , 10, 10,  new Vector2D(x, y), mapData));
            }
        }
    }


    static private void activeAutomaton( int iterations)
    {
        int wallCount;
        //used to store the matrix when is being modified
        //so that the algorithm modify generation by generation the map
        Placeable.Tile[][] mapDataCopy = mapData.getmapMatrix().clone();

        for(int iter = 0; iter < iterations; iter++)
        {
            for (int i = 0; i < size; i++)
            {
                for (int j = 0; j < size; j++)
                {
                    wallCount = 0;
                    if(i - 2 >= 0 && i + 2 <= size - 1 && j - 2 >= 0 && j + 2 <= size - 1) // check for array boundaries
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
                    else if (i  == 0 || i + 1 == size || j == 0 || j + 1 == size)
                        mapDataCopy[i][j] = Placeable.Tile.WALL;
                }
            }

            mapData.setmapMatrix(mapDataCopy);
        }

    }

    static private void generateWalls()
    {
        for(int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++) {
                if(Math.random()  <= .45)
                    mapData.setTileAt(i, j, Placeable.Tile.WALL);
                else
                    mapData.setTileAt(i, j, Placeable.Tile.FLOOR);
            }
        }
    }
}
