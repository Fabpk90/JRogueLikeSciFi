package Utils;

import Actors.*;
import Items.Armor;
import Items.Item;
import Items.Potion;
import Items.Weapon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static org.fusesource.jansi.Ansi.ansi;

public class Terrain implements Serializable {

    private int size;
    private MapData mapData;

    private Player player;

    private ArrayList<Monster> monsters;
    private ArrayList<Item> itemsOnTheGround;
    private ArrayList<Trap> traps;

    private Random random;

    public Terrain(int size, Player player)
    {
        this.size = size;
        this.player = player;

        random = new Random();

        mapData = new MapData(size);

        monsters = new ArrayList<>();
        itemsOnTheGround = new ArrayList<>();
        traps = new ArrayList<>();

        generateMap();
    }

    public void updateTerrain()
    {
        for(Monster m : monsters)
        {
            if(!m.checkForPlayer())
                m.wander();
            else
            if(m.Attack(player))
                System.out.println("Player dead");
        }
    }

    private void generateMap()
    {
        if(mapData.getLevel() + 1 >= GameManager.levelMax)
            GameManager.winGame = true;
        else
        {
            monsters = new ArrayList<>();
            itemsOnTheGround = new ArrayList<>();

            //increase the size of the map 
            size += 2;

            mapData = MapGenerator.getMap(size, mapData.getLevel(), this);
            resetPlayerPosition();
        }

    }


    //goes trough the entire matrix and prints the map
    //the printing is executed in a batch
    public void printMap()
    {
        StringBuilder str = new StringBuilder();
        for(int i=0;i<size;i++)
        {
            for(int j=0;j<size;j++)
            {
                str.append(ansi().fg(mapData.getTileAt(i,j).getColor()).a(mapData.getTileAt(i,j)));
            }
            str.append("\n");
        }

        System.out.print(str);
    }

    //Randomly place the player in the map
    private Vector2D getGeneratedPlayerRandomPos()
    {
        int floorTiles = 0;
        int i,j;

        while (true)
        {
            i = random.nextInt(size);
            j = random.nextInt(size);


            if(mapData.getTileAt(i, j) == Placeable.Tile.FLOOR)
            {
                floorTiles++;

                floorTiles += mapData.getTileAt(i, j - 1) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i, j + 1) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i - 1, j) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i + 1, j) == Placeable.Tile.FLOOR ? 1 : 0;

                if(floorTiles > 3)
                    return new Vector2D(i, j);

                floorTiles = 0;
            }
        }
    }

    //move the player if possible
    //checks fot traps and exit
    public void movePlayer(Vector2D vec)
    {
        Vector2D vecPosition = new Vector2D(vec.getX(), vec.getY());
        vecPosition.add(player.getPosition());

        if(mapData.getTileAt(vecPosition) != Placeable.Tile.MONSTER
                && mapData.getTileAt(vecPosition) != Placeable.Tile.WALL)
        {
            mapData.setTileAt(player.getPosition(), Placeable.Tile.FLOOR);
            player.move(vec);
            if(mapData.getTileAt(player.getPosition()) == Placeable.Tile.EXIT)
            {
                generateMap();
            }
            else if(mapData.getTileAt(player.getPosition()) == Placeable.Tile.TRAP)
            {
               Trap trap = getTrapAt(player.getPosition());

               player.takeDamage(trap.getDamageAmount() * 10);
               GameManager.addLog(player.getName()+" stepped on a trap, inflicting "+trap.getDamageAmount() * 10 + " damages");
               getTraps().remove(trap);

               mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
            }
            else
                mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
        }
    }


    private Trap getTrapAt(Vector2D position)
    {
        for (Trap trap : traps)
        {
            if(trap.getPosition().equals(position))
                return  trap;
        }

        return null;
    }

    //reset randomly the player position
    private void resetPlayerPosition()
    {
        player.setPosition(getGeneratedPlayerRandomPos());
        mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
    }


    public MapData getMapData() {
        return mapData;
    }

    public boolean attackMonsterAt(Actor.Direction playerAttackDirection)
    {
        Vector2D dir = null;
        switch (playerAttackDirection)
        {
            case UP:
                dir = Vector2D.getVector2DUp();
                break;

            case DOWN:
                dir = Vector2D.getVector2DDown();
                break;

            case LEFT:
                dir = Vector2D.getVector2DLeft();
                break;

            case RIGHT:
                dir = Vector2D.getVector2DRight();
                break;
        }

        dir.add(player.getPosition());

        if(mapData.getTileAt(dir) == Placeable.Tile.MONSTER)
        {
            player.Attack(getMonsterAt(dir));
            return true;
        }

        return false;
    }

    // @return The monster at the position given
    private Monster getMonsterAt(Vector2D positionToSearch)
    {
        for(Monster m : monsters)
        {
            if(m.getPosition().equals(positionToSearch))
                return m;
        }

        return null;
    }

    // @return The item at the position given
    private Item getItemAt(Vector2D positionToSearchFor)
    {
        for(Item i : itemsOnTheGround)
        {
            if(i.getPosition().equals(positionToSearchFor))
                return i;
        }

        return null;
    }

    //try to pickup the item at the position given
    public void takeAt(Vector2D vec)
    {
        Vector2D vecPosition = new Vector2D(vec.getX(), vec.getY());
        vecPosition.add(player.getPosition());

        if(mapData.getTileAt(vecPosition) == Placeable.Tile.ITEM)
        {
            player.addInventory(getItemAt(vecPosition));
            System.out.println(player.getName() + " found a " + getItemAt(vecPosition).getName()); //need to scroll up to read it
            mapData.setTileAt(vecPosition, Placeable.Tile.FLOOR);
        }

    }

    //Drop an item from the player's inventory
    public void dropItemAt(int id, Vector2D vec)
    {
        Vector2D vecPosition = new Vector2D(vec.getX(), vec.getY());
        vecPosition.add(player.getPosition());


        if(mapData.getTileAt(vecPosition) == Placeable.Tile.FLOOR){  //Make sure the target is an empty space player.getItemAt(id).setPosition(vecPosition);

            if(player.getArm() == player.getItemAt(id))              //If the dropped item was equipped, the player unequip it
                player.unequipArm();

            if(player.getWeap() == player.getItemAt(id))
                player.unequipWeap();

            player.getItemAt(id).setPosition(vecPosition);
            mapData.setTileAt(player.getItemAt(id).getPosition(), Placeable.Tile.ITEM);  //Update the map
            itemsOnTheGround.add(player.getItemAt(id));

            player.removeInventory(player.getItemAt(id));
        }
        else System.out.println("You can't drop something here");
    }

    //When a monster dies it has a 50% to drop loot, the items are more powerful than loot found on the ground
    public void monsterDrop(Vector2D vec)
    {
        Random r = new Random();

        //50% of the time
        if(r.nextInt(2) == 1) {
            switch (r.nextInt(3)) {
                case 0:
                    itemsOnTheGround.add(new Armor(Placeable.Tile.ITEM, new Vector2D(vec.getX(), vec.getY()), r.nextInt(8))); //Drop Armor
                    break;
                case 1:
                    itemsOnTheGround.add(new Weapon(Placeable.Tile.ITEM, new Vector2D(vec.getX(), vec.getY()), r.nextInt(8))); //Drop Weapon
                    break;
                case 2:
                    itemsOnTheGround.add(new Potion(Placeable.Tile.ITEM, new Vector2D(vec.getX(), vec.getY()), r.nextInt(20))); //Drop items
                    break;
            }

            mapData.setTileAt((int) vec.getX(), (int) vec.getY(), Placeable.Tile.ITEM);
        }
    }

    public void removeMonster(Monster monster)
    {
        monsters.remove(monster);
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<Trap> getTraps() {
        return traps;
    }

    public ArrayList<Item> getItemList() {
        return itemsOnTheGround;
    }
}
