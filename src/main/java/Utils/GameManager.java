package Utils;

import Actors.Actor;

import java.io.Serializable;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class GameManager implements Serializable {

    private Terrain terrain;

    private Actor player;

    private String log; // used for logging actions in the terminal such as combat log

    public GameManager(int height, int width)
    {
        //player = new Player();
        //ajouter le player au terrain
        terrain = new Terrain(height, width);

        player = new Actor();

        terrain.setPlayer(player);

        log = "";
    }

    public void render()
    {
        Scanner sc = new Scanner(System.in);
        String input;

        do {
            terrain.printMap();
           terrain.movePlayer(Vector2D.getVector2DRight());

           System.out.println(log);

            input = sc.nextLine();

        }while(!input.equals("exit"));
    }
}
