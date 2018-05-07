package Utils;

import Actors.Actor;
import Actors.PJ;

import java.io.Serializable;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class GameManager implements Serializable
{

    private Terrain terrain;

    private PJ player;  //From Actor to PJ

    static private StringBuilder log; // used for logging actions in the terminal such as combat log

    static public boolean exitGame;

    public GameManager(int size)
    {
        //ajouter le player au terrain
        terrain = new Terrain(size);

        player = new PJ(20, 10, 10);

        terrain.setPlayer(player);

        log = new StringBuilder();
    }

    public void render()
    {
        Scanner sc = new Scanner(System.in);

        do {
            terrain.printMap();

            printLog();
            printStats();

            parseCMD(sc);

            if(!exitGame)
                terrain.updateTerrain();

            System.out.print("\033[H\033[2J"); //clear the console
            System.out.flush();
        }while(!exitGame);

        sc.close();
    }

    private void printStats()
    {
        System.out.println("Health "+player.getHealth());
    }

    private void printLog()
    {
        System.out.println(log);
        log = new StringBuilder();
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
                else if(commands[0].equals("attack"))
                {
                    boolean attackGoneWrong = true;
                    if(commands[1].equals("right"))
                    {
                        isCorrectCMD = true;
                        if(terrain.attackMonsterAt(Actor.Direction.RIGHT))
                        {
                            addLog("Player attacking an enemy on his right");
                            attackGoneWrong = false;
                        }
                    }
                    else if (commands[1].equals("down"))
                    {
                        isCorrectCMD = true;
                        if(terrain.attackMonsterAt(Actor.Direction.DOWN))
                        {
                            addLog("Player attacking an enemy on his bottom");
                            attackGoneWrong = false;
                        }
                    }
                    else if(commands[1].equals("left"))
                    {
                        isCorrectCMD = true;
                        if(terrain.attackMonsterAt(Actor.Direction.LEFT))
                        {
                            addLog("Player attacking an enemy on his left");
                            attackGoneWrong = false;
                        }
                    }
                    else if(commands[1].equals("up"))
                    {
                        isCorrectCMD = true;
                        if(terrain.attackMonsterAt(Actor.Direction.UP))
                        {
                            addLog("Player attacking an enemy on his top");
                            attackGoneWrong = false;
                        }
                    }

                    if(attackGoneWrong)
                        addLog("The player attacks the void..");
                }
               else if(commands[0].equals("open"))
                {
                    if(commands[1].equals("inventory"))
                    {
                        //isCorrectCMD = true; activer si on veut qu'ouvrir l'inventaire face passer un tour.
                        addLog("Opening Inventory");
                        player.openInventory();
                    }
                }
                /*else if(commands[0].equals("pick"))
                {
                    if(commands[1].equals("up"))
                    {
                        addLog("pickin' up da muney");
                        terrain.take(Vector2D.getVector2DUp());
                    }
                }*/
            }
            else if (input.equals("exit"))
            {
                isCorrectCMD = true;
                exitGame = true;
            }
            else if(input.equals("save"))
            {
                if(SaveManager.saveInstance(this))
                    System.out.println("Saved successful");
                else
                    System.out.println("Error during saving");
            }
        }

    }

    public static void addLog(String log)
    {
        GameManager.log.append(log+"\n");
    }

    public static StringBuilder getLog() {
        return log;
    }
}
