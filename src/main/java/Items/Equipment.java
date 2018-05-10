package Items;


import Actors.Player;
import Items.Item;
import Utils.Vector2D;

//Exceedingly useful class, so much that I would go as far as to say that it embodies the very essence of
//Mankind, Pancakes and irony.

public abstract class Equipment extends Item
{

    private static int type;
    public Equipment(Tile tile, Vector2D position, String name, int type)
    {
        super(tile, position, name);
        this.type = type;
    }

    public int getEquipmentType(){
        return type;
    }

    @Override
    public void equip(Player p) {
        //hahalol overrifde
    }
}
