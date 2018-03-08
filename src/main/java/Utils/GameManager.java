package Utils;

import Actors.Actor;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

public class GameManager implements Serializable {

    private Terrain terrain;

    private Actor player;

    public GameManager(int height, int width)
    {
        //player = new Player();
        //ajouter le player au terrain
        terrain = new Terrain(height, width);

        player = new Actor();
        terrain.setPlayer(player);
    }

    public void render()
    {
        System.out.println(ansi().fgBlue().a( terrain.getMap()));
    }
}
