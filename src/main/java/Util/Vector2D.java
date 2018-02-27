package Util;

/*

Author: Fab
Ver 0.1: basic class, classic square root for distance

 */


public class Vector2D {

    private float x;
    private float y;


    static Vector2D getVector2DOne()
    {
        return new Vector2D(1, 1);
    }
    static Vector2D getVector2DZero() { return new Vector2D(0, 0); }

    static Vector2D getVector2DUp() { return new Vector2D(0, 1); }
    static Vector2D getVector2DDown() { return new Vector2D(0, -1); }
    static Vector2D getVector2DRight() { return new Vector2D(1, 0); }
    static Vector2D getVector2DLeft() { return new Vector2D(-1, 0); }

    static Vector2D add(Vector2D v1, Vector2D v2)
    {
        Vector2D v = new Vector2D();
        v.setX(v1.x + v2.x);
        v.setY(v1.y + v2.y);

        return v;
    }

    public Vector2D()
    {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(float x, float y)
    {
        this.x = x;
        this.y = y;
    }


    public float getDistanceFrom(Vector2D v)
    {
        float x2 = (x - v.x) * (x - v.x);
        float y2 = (y - v.y) * (y - v.y);

        return (float) Math.sqrt(x2 - y2);
    }

    public float getMagnitude()
    {
        return (float) Math.sqrt((x*x) + (y*y));
    }

    public void add(float x, float y)
    {
        this.x += x;
        this.y += y;
    }

    public void add(Vector2D v)
    {
        this.x += v.x;
        this.y += v.y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
