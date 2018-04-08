package Utils;

import Actors.Placeable;

public class MapData
{
    private int level;

    private Placeable.Tile [][] mapMatrix;

    private int size;

    public MapData(int size)
    {
        mapMatrix = new Placeable.Tile[size][size];

        this.size = size;

        level = 0;
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
        if(x < 0 || x >= size || y < 0 || y >= size)
            return Placeable.Tile.WALL;

        return mapMatrix[x][y];
    }
    public Placeable.Tile getTileAt(Vector2D vec)
    {
        return getTileAt((int)vec.getX(),(int)vec.getY());
    }

    public Placeable.Tile getTileAt(float x, float y)
    {
        return getTileAt((int) x, (int)y);
    }

    public int getLevel() {
        return level;
    }

    public void setTileAt(int x, int y, Placeable.Tile placeable)
    {
        mapMatrix[x][y] = placeable;
    }

    public void setTileAt(Vector2D vec, Placeable.Tile placeable)
    {
        mapMatrix[(int)vec.getX()][(int)vec.getY()] = placeable;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
