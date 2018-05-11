package Utils;

import Actors.Monster;
import Actors.Placeable;
import Items.Armor;
import Items.Item;
import Items.Potion;
/*

/*

    Uses cellular automata to generate the cave, using the 4-5 rule

    Ver 0.1: Working, but creating orphaned isle

 */
import Actors.Trap;
import Items.Weapon;

import java.util.ArrayList;
import java.util.Random;

public class MapGenerator
{
    static public int size;

    static private int level;
    
    static private MapData mapData;

    static private Random random;

    static public MapData getMap(int size, int level, Terrain terrain)
    {
        mapData = new MapData(size);

        random = new Random();

        MapGenerator.size = size;
        MapGenerator.level = level;

        generateWalls();
        activeAutomaton(5);
        generateExit();

        generateTraps(terrain.getTraps());

        generateItem(terrain.getItemList());

        generateMonsters(terrain.getMonsters(), terrain);

        mapData.setLevel(level + 1);

        return mapData;
    }

    private static void generateTraps(ArrayList<Trap> traps)
    {

        int placed = 0;
        int toPlace = random.nextInt(2 );

        int x,y;

        while (placed != toPlace)
        {
            x = random.nextInt(size);
            y = random.nextInt(size);

            if(getNbFreeAdjacentTiles(mapData, x, y) > 3)
            {
                placed++;

                Trap trap = new Trap(new Vector2D(x,y), 5);

                mapData.setTileAt(x, y, trap.getTile());
                traps.add(trap);
            }

        }

    }

    //randomly generate items
    private static void generateItem(ArrayList<Item>  itemsOnTheGround)
    {
        Random r = new Random();

        int placed = 0;
        int toPlace = 1;

        int x,y,type;

        while (placed != toPlace)
        {
            x = r.nextInt(size);
            y = r.nextInt(size);

            if(getNbFreeAdjacentTiles(mapData, x, y) > 3)
            {
                placed++;

                type = r.nextInt(3);
                switch(type)
                {
                    case 0:
                        itemsOnTheGround.add(new Armor(Placeable.Tile.ITEM,new Vector2D(x,y) ,r.nextInt(5)));
                        break;
                    case 1:
                        itemsOnTheGround.add(new Weapon(Placeable.Tile.ITEM,new Vector2D(x,y) ,r.nextInt(5)));
                        break;
                    case 2:
                        itemsOnTheGround.add(new Potion(Placeable.Tile.ITEM,new Vector2D(x,y) ,r.nextInt(15)));
                        break;
                }
                mapData.setTileAt(x, y, Placeable.Tile.ITEM);
            }

        }

    }

    //generates monsters according to the map level
    // populate the arrayList given
    private static void generateMonsters(ArrayList<Monster> monsters, Terrain terrain)
    {
        int placed = 0;
        int toPlace = random.nextInt(level+1 )+1;

        int x,y;

        while (placed != toPlace)
        {
            x = random.nextInt(size);
            y = random.nextInt(size);

            //see if adjacent tiles are free
            if(getNbFreeAdjacentTiles(mapData, x , y) > 3)
            {
                placed++;

                mapData.setTileAt(x,y, Placeable.Tile.MONSTER);
                monsters.add(new Monster(Placeable.Tile.MONSTER, 10 , 13, 10,  new Vector2D(x, y), terrain));
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
                        //we count surrounding walls, 8 cells
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

    //generate an exit by searching a "free" Tile from the bottom
    //It starts searching from the bottom
    static private void generateExit()
    {
        int floorTiles = 0;
        boolean found = false;

        for(int i = size - 1; i > 0 && !found; i--)
        {
            for(int j = 0; j < size && !found; j++)
            {

                floorTiles = getNbFreeAdjacentTiles(mapData, i, j);

                if(floorTiles > 3)
                {
                    mapData.setTileAt(i, j, Placeable.Tile.EXIT);
                    found = true;
                }
            }
        }
    }

    // @return -1 if no tiles, 0 if the center tiles is not free, X free tiles otherwise
    static private int getNbFreeAdjacentTiles(MapData mapData, int x, int y)
    {
        int floorTiles = -1;
        if(mapData.getTileAt(x, y) == Placeable.Tile.FLOOR)
        {
            floorTiles++;

            floorTiles += mapData.getTileAt(x, y - 1) == Placeable.Tile.FLOOR ? 1 : 0;
            floorTiles += mapData.getTileAt(x, y + 1) == Placeable.Tile.FLOOR ? 1 : 0;
            floorTiles += mapData.getTileAt(x - 1, y) == Placeable.Tile.FLOOR ? 1 : 0;
            floorTiles += mapData.getTileAt(x + 1, y) == Placeable.Tile.FLOOR ? 1 : 0;
        }

        return floorTiles;
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
