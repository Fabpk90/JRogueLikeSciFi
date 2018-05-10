package Items;

import Actors.Player;
import Utils.NameGenerator;
import Utils.Vector2D;

public class Weapon extends Equipment {

    private int atk;
    //public int range; Si on poursuit l'idée des armes à distance on aura besoin de ça

    public Weapon (Tile tile, Vector2D position, int atk)
    {
        super(tile, position, NameGenerator.getRandomWeaponName(), 1);
        this.atk = atk;

    }

    public Weapon (Tile tile, Vector2D position, String name, int atk)
    {
        super(tile, position, name, 1);
        this.atk = atk;

    }

    public int getAtk() {return atk;}

    public void setAtk(int atk) {
        this.atk = atk;
    }

    @Override
    public String toString()
    {
        return getName() + " Atk Power: " + atk;
    }
}