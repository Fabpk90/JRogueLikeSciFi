package Utils;

import Actors.Actor;
import Actors.PJ;

import java.io.Serializable;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class GameManager implements Serializable {

    private Terrain terrain;

    private Actor player;

    private String log; // used for logging actions in the terminal such as combat log

    private boolean exitGame;

    public GameManager(int height, int width)
    {
        //player = new Player();
        //ajouter le player au terrain
        terrain = new Terrain(height, width);

        player = new PJ(10, 10, 10);

        terrain.setPlayer(player);

        log = "";
    }

    public void render()
    {
        Scanner sc = new Scanner(System.in);

        do {
            terrain.printMap();
            System.out.println(log);
            parseCMD(sc.nextLine());

            System.out.print("\033[H\033[2J"); //clear the console
            System.out.flush();
        }while(!exitGame);
    }

    private void parseCMD(String input)
    {
        String[] commands = input.split(" ");

        if(commands.length >= 2)
        {
            if(commands[0].equals("move"))
            {
               if(commands[1].equals("right"))
               {
                   terrain.movePlayer(Vector2D.getVector2DRight());
               }
               else if (commands[1].equals("down"))
               {
                   terrain.movePlayer(Vector2D.getVector2DDown());
               }
               else if(commands[1].equals("left"))
               {
                   terrain.movePlayer(Vector2D.getVector2DLeft());
               }
               else if(commands[1].equals("up"))
               {
                   terrain.movePlayer(Vector2D.getVector2DUp());
               }
            }
        }
        else if (input.equals("exit"))
        {
            exitGame = true;
        }
    }
}
