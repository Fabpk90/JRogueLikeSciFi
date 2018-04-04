package Utils;

import Actors.Placeable;

public class MapData
{
    private int level;

    private Placeable.Tile [][] mapMatrix;

    private int height;
    private int width;

    public MapData(int height, int width)
    {
        mapMatrix = new Placeable.Tile[height][width];

        this.height = height;
        this.width = width;

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
        if(x < 0 || x >= width || y < 0 || y >= height)
            return Placeable.Tile.WALL;

        return mapMatrix[x][y];
    }
    public Placeable.Tile getTileAt(Vector2D vec)
    {
        if(vec.getX() < 0 || vec.getX() > width
                || vec.getY() < 0 || vec.getY() > height)
            return Placeable.Tile.WALL;

        return mapMatrix[(int)vec.getX()][(int)vec.getY()];
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
