package Utils;

import Actors.Actor;
import Actors.Player;

import java.io.Serializable;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class GameManager implements Serializable
{

    private Terrain terrain;

    private Player player;  //From Actor to Player

    static private StringBuilder log; // used for logging actions in the terminal such as combat log

    static public boolean exitGame = false;

    static public boolean winGame = false;
    final static  public  int levelMax = 3;

    public GameManager(int size, Player player)
    {
        this.player = player;

        terrain = new Terrain(size, this.player);

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
        }while(!exitGame || !winGame);

        if(exitGame)
        {
            System.out.println("See you adventurer");
        }
        else if(winGame)
        {
            System.out.println("You arrived at the center of the ship!");
            System.out.println("You quickly initiate the auto-destruction protocol, and blow yourself up!");
        }

        sc.close();
    }

    private void printStats()
    {
        System.out.println(player.getName()+"'s Health "+player.getHealth());
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
                switch (commands[0]) {
                    case "move":
                        switch (commands[1]) {
                            case "right":
                                isCorrectCMD = true;
                                terrain.movePlayer(Vector2D.getVector2DRight());
                                break;
                            case "down":
                                isCorrectCMD = true;
                                terrain.movePlayer(Vector2D.getVector2DDown());
                                break;
                            case "left":
                                isCorrectCMD = true;
                                terrain.movePlayer(Vector2D.getVector2DLeft());
                                break;
                            case "up":
                                isCorrectCMD = true;
                                terrain.movePlayer(Vector2D.getVector2DUp());
                                break;
                        }
                        break;
                    case "attack":
                        boolean attackGoneWrong = true;
                        switch (commands[1]) {
                            case "right":
                                isCorrectCMD = true;
                                if (terrain.attackMonsterAt(Actor.Direction.RIGHT)) {
                                    addLog("Player attacking an enemy on his right");
                                    attackGoneWrong = false;
                                }
                                break;
                            case "down":
                                isCorrectCMD = true;
                                if (terrain.attackMonsterAt(Actor.Direction.DOWN)) {
                                    addLog("Player attacking an enemy on his bottom");
                                    attackGoneWrong = false;
                                }
                                break;
                            case "left":
                                isCorrectCMD = true;
                                if (terrain.attackMonsterAt(Actor.Direction.LEFT)) {
                                    addLog("Player attacking an enemy on his left");
                                    attackGoneWrong = false;
                                }
                                break;
                            case "up":
                                isCorrectCMD = true;
                                if (terrain.attackMonsterAt(Actor.Direction.UP)) {
                                    addLog("Player attacking an enemy on his top");
                                    attackGoneWrong = false;
                                }
                                break;
                        }

                        if (attackGoneWrong)
                            addLog("The player attacks the void..");
                        break;
                    case "open":
                        if (commands[1].equals("inventory")) {
                            //isCorrectCMD = true; activer si on veut qu'ouvrir l'inventaire face passer un tour.
                            addLog("Opening Inventory");
                            player.openInventory();
                        }
                        break;
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

    //used to reinitialize object not serialized
    public void loaded()
    {
        log = new StringBuilder();
    }
}
