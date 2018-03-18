package Utils;

import Actors.Placeable;

public class MapData
{
    private Placeable.Tile [][] mapMatrix;

    private int height;
    private int width;

    public MapData(int height, int width)
    {
        mapMatrix = new Placeable.Tile[height][width];

        this.height = height;
        this.width = width;
    }

    public Placeable.Tile [][] getmapMatrix()
    {
        return mapMatrix;
    }

    public void setmapMatrix(Placeable.Tile[][] mapMatrix) {
        this.mapMatrix = mapMatrix;
    }

    public Placeable.Tile getTileAt(int x, int y)
    {
        if(x < 0 || x > width
                || y < 0 || y > height)
            return Placeable.Tile.WALL;

        return mapMatrix[x][y];
    }
    
    public void setTileAt(int x, int y, Placeable.Tile placeable)
    {
        mapMatrix[x][y] = placeable;
    }
}
