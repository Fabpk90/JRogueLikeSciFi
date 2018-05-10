package Utils;

import Actors.*;
import Items.Armor;
import Items.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static org.fusesource.jansi.Ansi.ansi;

public class Terrain implements Serializable {

    private int size;
    private MapData mapData;

    private Player player;                  //Changé de Actor à Player

    private ArrayList<Monster> monsters;
    private ArrayList<Item> itemsOnTheGround;
    private ArrayList<Trap> traps;

    public Terrain(int size, Player player)
    {
        this.size = size;
        this.player = player;

        mapData = new MapData(size);

        monsters = new ArrayList<Monster>();
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

            System.out.println(mapData.getLevel() - 1 >= GameManager.levelMax);
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

        Random r = new Random();

        while (true)
        {
            i = r.nextInt(size);
            j = r.nextInt(size);


            if(mapData.getTileAt(i, j) == Placeable.Tile.FLOOR)
            {
                floorTiles++;

                floorTiles += mapData.getTileAt(i, j - 1) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i, j + 1) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i - 1, j) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i + 1, j) == Placeable.Tile.FLOOR ? 1 : 0;

                System.out.println(floorTiles+" free tiles");

                if(floorTiles > 3)
                    return new Vector2D(i, j);

                floorTiles = 0;
            }
        }
    }

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


    public Trap getTrapAt(Vector2D position)
    {
        for (Trap trap : traps)
        {
            if(trap.getPosition().equals(position))
                return  trap;
        }

        return null;
    }

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

    public Monster getMonsterAt(Vector2D positionToSearch)
    {
        for(Monster m : monsters)
        {
            if(m.getPosition().equals(positionToSearch))
                return m;
        }

        return null;
    }

    public Item getItemAt(Vector2D positionToSearchFor)
    {
        for(Item i : itemsOnTheGround)
        {
            if(i.getPosition().equals(positionToSearchFor))
                return i;
        }

        return null;
    }

    public void take(Vector2D vec)
    {
        Vector2D vecPosition = new Vector2D(vec.getX(), vec.getY());
        vecPosition.add(player.getPosition());

        if(mapData.getTileAt(vecPosition) == Placeable.Tile.ITEM)   //Si on rajoute le champ ITEM dans l'enum
        {                                                           //Et qu'on change son glyph plus tard, il sera
                                                                    //toujours considéré comme un ITEM ? OUI! c'est ça qu'il fallait faire ;)

            player.addInventory(getItemAt(vecPosition));  //J'ai voulu m'inspirer de getMonsterAt mais je sais pas comment sont
            mapData.setTileAt(vecPosition, Placeable.Tile.FLOOR);                            //stocké les items en mémoire donc pour les trouver c'est tendu    Pour ça il faut où savoir l'index ou avoir l'objet, du coup la il faudrait une fonction qui dit
                                        // à cette endroit il y a X objet tiens sa référence
        }

    }

    //Test function, spawn an item on square 6,6 regardless of what was there
    public void spawnItem()
    {
        mapData.setTileAt(6,6, Placeable.Tile.ITEM);
        itemsOnTheGround.add(new Armor(Placeable.Tile.ITEM, new Vector2D(6,6), "FurSuit", -10 ));
    }

    //Drop an item from the player's inventory
    public void dropItem(String name, Vector2D vec)
    {
        Vector2D vecPosition = new Vector2D(vec.getX(), vec.getY());
        vecPosition.add(player.getPosition());

        Item tmp = player.searchInventory(name);

        if(tmp != null)
        {
            if(mapData.getTileAt(vecPosition) == Placeable.Tile.FLOOR)  //Make sure the target is an empty space
            tmp.setPosition(vecPosition);
            mapData.setTileAt(tmp.getPosition(), Placeable.Tile.ITEM);  //Update the map
            itemsOnTheGround.add(tmp);
            player.removeInventory(tmp);
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
}
