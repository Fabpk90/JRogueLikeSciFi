package Actors;

import Items.Armor;
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


    public Player(int health, int atk, int def)
    {
        super(Tile.PLAYER, health, atk, def, Vector2D.getVector2DZero(), "Player");
        inv = new ArrayList <>();
        arm = new Armor(Placeable.Tile.ITEM, new Vector2D(-1,-1), "Suit", 5 );
        weap = new Weapon(Placeable.Tile.ITEM, null, "Fist", 5 );
    }

    public void openInventory()
    {
        if(inv.isEmpty())
            System.out.println("Your inventory is empty. Sad Face");
        else
        {
            for(Item i : inv)
            {
                System.out.print(i.toString() + " ");
            }
            System.out.println();
        }
    }

    //look for an item in the inventory
    public Item searchInventory(String name)
    {
        if(inv.isEmpty())
            System.out.println("Can't equip something that doesn't exit jackass");
        else {
            for (Item i : inv)
                if (i.getName().equals(name)) return i;

        }

        System.out.println(name + " isn't in the inventory");
        return null;
    }

    //look for the item in the inventory then equip it
    public void equipItem(String name)
    {

        Item tmp = searchInventory(name);

        if(tmp != null)
        {
            tmp.equip(this);
            inv.remove(tmp);
        }

    }

    public void equipArmor(Armor A)
    {
        addInventory(this.arm);
        arm = new Armor(A);
    }

    public void equipWeapon(Weapon W)
    {
        addInventory(this.weap);
        weap = new Weapon(W);
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
