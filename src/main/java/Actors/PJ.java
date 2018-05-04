package Actors;

import Utils.Vector2D;

public class PJ extends Actor {

    //public List<items> inv; Liste chainé représentant l'inventaire, je laisse en com le temps de faire les items

    public PJ( int health, int atk, int def, Vector2D position)
    {
        super(Tile.PLAYER, health, atk, def, position);
        //inv = new ArrayList <items>();
    }

    public PJ( int health, int atk, int def)
    {
        super(Tile.PLAYER, health, atk, def, Vector2D.getVector2DZero());
        //inv = new ArrayList <items>();
        setName("Player");
    }

    @Override
    void onDie() {

    }

    @Override
    public boolean Attack(Actor actorAttacked) {
        return super.Attack(actorAttacked);
    }
}
