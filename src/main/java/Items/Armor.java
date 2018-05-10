package Items;

import Actors.Player;
import Utils.Vector2D;

public class Armor extends Equipment {

    private int def;

    public Armor (Tile tile, Vector2D position, String name, int def)
    {
        super(tile, position, name, 0);
        this.def = def;
    }

    //Cloning builder for the equip system
    public Armor(Armor A)
    {
        super(A.getTile(), A.getPosition(), A.getName(), 0);
        this.def = A.getDef();
    }

    public int getDef() {return def;}

    public void setDef(int def) {
        this.def = def;
    }

    //@Override
    //public void equip(Player p){ p.equipArmor(this);}

    @Override
    public String toString()
    {
        return getName() + " Def Power: " + def;
    }

}
