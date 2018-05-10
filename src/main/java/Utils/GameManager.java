package Utils;

import Actors.Actor;
import Actors.Placeable;
import Actors.Player;
import Items.Weapon;   //Only needed for a test command
import Items.Potion;   //Only needed for a test command
//import org.fusesource.jansi.Ansi;

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
                    case "open":
                        switch (commands[1]) {
                            case "inventory":
                                //isCorrectCMD = true; activer si on veut qu'ouvrir l'inventaire face passer un tour.
                                addLog("Opening Inventory");
                                player.openInventory();
                                break;
                        }
                        break;

                    case "equip":
                        isCorrectCMD = true;
                        //player.equipItem(commands[1]);
                        break;
                    case "take":
                        switch (commands[1]) {
                            case "off": //for testing purpose, let you add an item to test the equip command
                                player.addInventory(new Weapon(Placeable.Tile.ITEM, null, "BigFist", 5));
                                player.addInventory(new Weapon(Placeable.Tile.ITEM, null, "FistAndFurious", 45));
                                player.addInventory(new Potion(Placeable.Tile.ITEM, null, "Herbe verte de resident evil", 3));
                                break;

                            case "on": //for testing purpose, let you spawn a FurSuit on case 6,6 to test take command
                                terrain.spawnItem();
                                break;

                            case "up":
                                addLog("Picking up an item");
                                isCorrectCMD = true;
                                terrain.take(Vector2D.getVector2DUp());
                                break;
                            case "down":
                                addLog("Picking up an item");
                                isCorrectCMD = true;
                                terrain.take(Vector2D.getVector2DDown());
                                break;
                            case "left":
                                addLog("Picking up an item");
                                isCorrectCMD = true;
                                terrain.take(Vector2D.getVector2DLeft());
                                break;
                            case "right":
                                addLog("Picking up an item");
                                isCorrectCMD = true;
                                terrain.take(Vector2D.getVector2DRight());
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
                menuMan.menuManaging(player);
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
