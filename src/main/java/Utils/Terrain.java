package Utils;

import Actors.Actor;
import Actors.Monster;
import Actors.Placeable;
import Actors.PJ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import static org.fusesource.jansi.Ansi.ansi;

public class Terrain implements Serializable {

    private int size;
    private MapData mapData;

    private PJ player;                  //Changé de Actor à PJ
    private ArrayList<Monster> monsters;

    public Terrain(int size, PJ player)
    {
        this.size = size;
        this.player = player;

        mapData = new MapData(size);

        monsters = new ArrayList<Monster>();

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
        mapData = MapGenerator.getMap(size, mapData.getLevel(), this);
        resetPlayerPosition();
    }


    //goes trough the entire matrix and prints the map
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

            //if the selected tile is a wall, do no consider it
            if(mapData.getTileAt(i, j) == Placeable.Tile.FLOOR)
            {
                floorTiles++;

                floorTiles += mapData.getTileAt(i, j - 1) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i, j + 1) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i - 1, j) == Placeable.Tile.FLOOR ? 1 : 0;
                floorTiles += mapData.getTileAt(i + 1, j) == Placeable.Tile.FLOOR ? 1 : 0;

                if(floorTiles > 3)
                    return new Vector2D(i, j);
            }
        }
    }

    public void setPlayer(PJ player)
    {
        this.player = player;

        resetPlayerPosition();
    }

    public void movePlayer(Vector2D vec)
    {
        Vector2D vecPosition = new Vector2D(vec.getX(), vec.getY());
        vecPosition.add(player.getPosition());

        if(mapData.getTileAt(vecPosition) == Placeable.Tile.FLOOR
                || mapData.getTileAt(vecPosition) == Placeable.Tile.EXIT)
        {
            mapData.setTileAt(player.getPosition(), Placeable.Tile.FLOOR);
            player.move(vec);
            if(mapData.getTileAt(player.getPosition()) == Placeable.Tile.EXIT)
            {
                generateMap();
            }
            else
                mapData.setTileAt(player.getPosition(), Placeable.Tile.PLAYER);
        }
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

    /*public void take(Vector2D vec)
    {
        Vector2D vecPosition = new Vector2D(vec.getX(), vec.getY());
        vecPosition.add(player.getPosition());

        if(mapData.getTileAt(vecPosition) == Placeable.Tile.ITEM)   //Si on rajoute le champ ITEM dans l'enum
        {                                                           //Et qu'on change son glyph plus tard, il sera
                                                                    //toujours considéré comme un ITEM ? OUI! c'est ça qu'il fallait faire ;)

            player.inv.add(getItemAt);  //J'ai voulu m'inspirer de getMonsterAt mais je sais pas comment sont
                                        //stocké les items en mémoire donc pour les trouver c'est tendu    Pour ça il faut où savoir l'index ou avoir l'objet, du coup la il faudrait une fonction qui dit
                                        // à cette endroit il y a X objet tiens sa référence
        }

    }*/

    public void removeMonster(Monster monster)
    {
        monsters.remove(monster);
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
