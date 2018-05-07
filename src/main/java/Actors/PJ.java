package Actors;

import Utils.GameManager;
import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

import java.util.ArrayList;
import java.util.List;

public class PJ extends Actor {

    private int exp;
    public List<Items> inv; //Liste chainé représentant l'inventaire, je laisse en com le temps de faire les items

    public PJ( int health, int atk, int def, Vector2D position)
    {
        super(Tile.PLAYER, health, atk, def, position);
        inv = new ArrayList <>();
    }

    public PJ( int health, int atk, int def)
    {
        super(Tile.PLAYER, health, atk, def, Vector2D.getVector2DZero());
        inv = new ArrayList <>();
        setName("Player");
    }

    public void openInventory()
    {
        if(inv.isEmpty())
            System.out.print("Your inventory is empty. Sad Face");
        else
        {
            for(Items i : inv)
            {
                System.out.print(i.toString() + " ");
            }
            System.out.println();
        }
    }

    public void addHelmet() //Fonction test d'affichage (j'avais une cible "open gift" dans le GameManager
    {                       // qui appelait cette fonction, le truc c'est qu'après tous les espaces vides étaient des H jaunes
                            //Gros concept. Probablement parceque j'ai fait de la chiasse
        inv.add(new Armor('H', Ansi.Color.YELLOW, new Vector2D(6,6),"Test", 10));
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
