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
    public List<Item> inv; //Liste chainé représentant l'inventaire, je laisse en com le temps de faire les items
    private Armor arm;     //Contient d'abord l'équipement de base puis l'armure équipé
    private Weapon weap;


    public Player(int health, int atk, int def)
    {
        super(Tile.PLAYER, health, atk, def, Vector2D.getVector2DZero(), "Player");
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
                System.out.println(ct + " - " + i.toString());
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

        if(weap == null)    System.out.println("Atk: " + getAtk());
        else System.out.println("Atk: " + (getAtk() + weap.getAtk()));

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
                        System.out.println("Dunno what happened but you can't do that");
                    }
                }
                case 1:{
                    try {
                        weap = Weapon.class.cast(e);
                    }catch(ClassCastException exc)
                    {
                        System.out.println("Dunno what happened but you can't do that");
                    }
                }

            }

    }

    public void addInventory(Item i)
    {
        inv.add(i);
    }

    public void removeInventory(Item i)
    {
        inv.remove(i);
    }

    public void addHelmet() //Fonction test d'affichage (j'avais une cible "open gift" dans le GameManager
    {                       // qui appelait cette fonction, le truc c'est qu'après tous les espaces vides étaient des H jaunes
                            //Gros concept. Probablement parceque j'ai fait de la chiasse
        inv.add(new Armor(Tile.ITEM, new Vector2D(6,6),"Test", 10));
    }

    public Armor getArm() {return arm;}
    public Weapon getWeap() {return weap;}
    public void unequipArm() { this.arm = null;}
    public void unequipWeap() {this.weap = null;}

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
