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
            parseCMD(sc);

            terrain.updateTerrain();

            System.out.print("\033[H\033[2J"); //clear the console
            System.out.flush();
        }while(!exitGame);
    }

    private void parseCMD(Scanner sc)
    {

        boolean isCorrectCMD = false;
        String input;

        while(!isCorrectCMD)
        {
            input = sc.nextLine();
            String[] commands = input.split(" "); //divide the input string in pieces

            if(commands.length >= 2) //min of 2 consecutive cmds
            {
                if(commands[0].equals("move"))
                {
                    if(commands[1].equals("right"))
                    {
                        isCorrectCMD = true;
                        terrain.movePlayer(Vector2D.getVector2DRight());
                    }
                    else if (commands[1].equals("down"))
                    {
                        isCorrectCMD = true;
                        terrain.movePlayer(Vector2D.getVector2DDown());
                    }
                    else if(commands[1].equals("left"))
                    {
                        isCorrectCMD = true;
                        terrain.movePlayer(Vector2D.getVector2DLeft());
                    }
                    else if(commands[1].equals("up"))
                    {
                        isCorrectCMD = true;
                        terrain.movePlayer(Vector2D.getVector2DUp());
                    }
                }
            }
            else if (input.equals("exit")) //TODO: handle the case where the player wants to quit but it is trap in a gangbang, resulting in his death
            {
                isCorrectCMD = true;
                exitGame = true;
            }
        }

    }
}
