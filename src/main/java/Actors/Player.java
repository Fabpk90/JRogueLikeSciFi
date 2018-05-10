package Actors;

import Items.Armor;
import Items.Equipment;
import Items.Item;
import Items.Weapon;
import Utils.GameManager;
import Utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class Player extends Actor {

    private int exp;
    private List<Item> inv; //Liste chainé représentant l'inventaire, je laisse en com le temps de faire les items
    private Armor arm;     //Contient d'abord l'équipement de base puis l'armure équipé
    private Weapon weap;


    public Player(int health, int atk, int def, String name)
    {
        super(Tile.PLAYER, health, atk, def, Vector2D.getVector2DZero(), name);
        inv = new ArrayList <>();
        arm = null;
        weap = null;
    }

    public void openInventory()
    {
        if(inv.isEmpty())
            System.out.println("Your inventory is empty. Sad Face");
        else
        {
            int ct = 0;

            for(Item i : inv)
            {
                if(i == null) System.out.println("WHAT");
                else System.out.println(ct + " - " + i.toString());
                ct++;
            }
            System.out.println();
        }
    }

    public Item getItemAt(int id)
    {
        if(id >= 0 && id < inv.size())
            return inv.get(id);

        System.out.println("id is either too big or too small");
        return null;
    }

    public void displayStats()
    {
        System.out.println("Health: " + getHealth());

        System.out.println("Atk: " + (getAtk()));

        if(arm == null)    System.out.println("Def: " + getDef());
        else System.out.println("Def: " + (getDef() + arm.getDef()));
    }

    public void equip(Item i)
    {
        Equipment e = null;
        boolean cont = true;

        try{
            e = Equipment.class.cast(i);
        }catch(ClassCastException exc)
        {
            System.out.println("You can't equip that item");
            cont = false;
        }

        if(cont)
            switch(e.getEquipmentType())
            {
                case 0:
                {
                    try {
                        arm = Armor.class.cast(e);
                    }catch(ClassCastException exc)
                    {
                        System.out.println("Cast exception, you can't equip that item");
                    }

                    break;
                }
                case 1:{
                    try {
                        weap = Weapon.class.cast(e);
                    }catch(ClassCastException exc)
                    {
                        System.out.println("Cast exception, you can't equip that item");
                    }

                    break;
                }

            }

        System.out.println(this.getName() + " successfully equipped " + i.getName());
    }

    public void addInventory(Item i)
    {
        inv.add(i);
    }

    public void removeInventory(Item i)
    {
        inv.remove(i);
    }

    public Armor getArm() {return arm;}
    public Weapon getWeap() {return weap;}
    public void unequipArm() { this.arm = null;}
    public void unequipWeap() {this.weap = null;}


    @Override
    public int getAtk()
    {
        if(weap != null) return super.getAtk() + weap.getAtk();
        else return super.getAtk();
    }

    public int getDef()
    {
        if(arm != null) return super.getDef() + arm.getDef();
        else return super.getDef();
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

    public List<Item> getInv()
    {
        return inv;
    }
}
