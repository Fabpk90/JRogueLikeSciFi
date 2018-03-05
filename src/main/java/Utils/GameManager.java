package Utils;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

public class GameManager implements Serializable {

    private Terrain terrain;

    //private Player player;

    public GameManager(int height, int width)
    {
        //player = new Player();
        //ajouter le player au terrain
        terrain = new Terrain(height, width);
    }

    public void render()
    {
        while(true)
        System.out.println(ansi().fgBlue().a( terrain.getMap()));
    }
}
