package Utils;

import Actors.Actor;
//import Actors.Placeable;
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
    final static  public  int levelMax = 5;

    //size of the map, + 2 each floor
    final static private int size = 8;

    public GameManager( Player player)
    {
        this.player = player;

        terrain = new Terrain(size, this.player);

        log = new StringBuilder();
    }

    public void render()
    {
        Scanner sc = new Scanner(System.in);
        MenuManager menuMan = new MenuManager(terrain);

        do {
            terrain.printMap();

            printLog();
            printStats();

            parseCMD(sc, menuMan);

            if(!exitGame)
                terrain.updateTerrain();

            System.out.print("\033[H\033[2J"); //clear the console
            System.out.flush();
        }while(!exitGame && !winGame );

        //printing in the case where the play dies because of traps
        printLog();

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

    private void parseCMD(Scanner sc, MenuManager menuMan)
    {
        boolean isCorrectCMD = false;
        String input;

        while(!isCorrectCMD) {
            System.out.println("Awaiting command");
            input = sc.nextLine();
            String[] commands = input.split(" "); //divide the input string in pieces

            if (commands.length >= 2) //min of 2 consecutive cmds
            {
                switch (commands[0])
                {
                    case "move":
                        switch (commands[1])
                        {
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

                    case "take":
                        switch (commands[1]) {
                            case "up":
                                addLog("Picking up an item");
                                isCorrectCMD = true;
                                terrain.takeAt(Vector2D.getVector2DUp());
                                break;
                            case "down":
                                addLog("Picking up an item");
                                isCorrectCMD = true;
                                terrain.takeAt(Vector2D.getVector2DDown());
                                break;
                            case "left":
                                addLog("Picking up an item");
                                isCorrectCMD = true;
                                terrain.takeAt(Vector2D.getVector2DLeft());
                                break;
                            case "right":
                                addLog("Picking up an item");
                                isCorrectCMD = true;
                                terrain.takeAt(Vector2D.getVector2DRight());
                                break;
                        }
                        break;
                    }
                }
            else if (input.equals("exit"))
            {
                isCorrectCMD = true;
                exitGame = true;
            }
            else if(input.equals("save"))
            {
                if(SaveManager.saveInstance(this))
                    System.out.println("Saved successfully");
                else
                    System.out.println("Error during saving");
            }
            else if(input.equals("load"))
            {
                isCorrectCMD = true;

                if(SaveManager.saveExists())
                {
                    GameManager gm = SaveManager.loadSave();
                    terrain = gm.terrain;
                    player = gm.player;
                }
                else
                {
                    System.out.println("No save available!");
                }
            }
            else if(input.equals("menu")) {
                isCorrectCMD = menuMan.menuManaging(player, this); //return value used to exit the game from the menu
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
