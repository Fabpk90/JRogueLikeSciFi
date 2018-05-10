package Items;

import Actors.Player;
import Utils.Vector2D;

public class Weapon extends Equipment {

    private int atk;
    //public int range; Si on poursuit l'idée des armes à distance on aura besoin de ça

    public Weapon (Tile tile, Vector2D position, String name, int atk)
    {
        super(tile, position, name);
        this.atk = atk;
    }

    //Cloning builder for the equipment system
    public Weapon(Weapon W)
    {
        super(W.getTile(), W.getPosition(), W.getName());
        this.atk = W.getAtk();
    }

    public int getAtk() {return atk;}

    public void setAtk(int atk) {
        this.atk = atk;
    }

    @Override
    public void equip(Player p){ p.equipWeapon(this);}

    @Override
    public String toString()
    {
        return getName() + " Atk Power: " + atk;
    }
}