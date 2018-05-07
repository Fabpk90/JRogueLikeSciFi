package Actors;

import Utils.GameManager;
import Utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class PJ extends Actor {

    private int exp;
    private List<Items> inv;

    public PJ( int health, int atk, int def)
    {
        super(Tile.PLAYER, health, atk, def, Vector2D.getVector2DZero());
        inv = new ArrayList<>();
        setName("Player");
    }

    @Override
    void onDie()
    {
        System.out.println("You die");
        GameManager.exitGame = true;
    }

    @Override
    public boolean Attack(Actor actorAttacked) {
        return super.Attack(actorAttacked);
    }

    public List<Items> getInv()
    {
        return inv;
    }
}
