package Utils;

import java.util.Scanner;
import Actors.Player;
import Items.Usable;

public class MenuManager {

    private Terrain terrain;

    public MenuManager(Terrain terrain)
    {
        this.terrain = terrain;
    }

    public boolean menuManaging(Player player, GameManager gm)
    {
        displayMenu();
        return parsingMenu(player, gm);
    }

    private void displayMenu()
    {
        System.out.println("");
        System.out.print("stats - ");
        System.out.print("inventory - ");
        System.out.println("save");
        System.out.print("exit menu - ");
        System.out.print("exit game");
        System.out.println("");
    }

    private void displayDirection()
    {
        System.out.println("");
        System.out.print("up - ");
        System.out.print("down - ");
        System.out.print("left - ");
        System.out.println("right - ");
        System.out.print("cancel");
        System.out.println("");
    }

    private void displayInventoryMenu()
    {
        System.out.println("");
        System.out.print("equip - ");
        System.out.print("use - ");
        System.out.println("drop");
        System.out.print("cancel");
        System.out.println("");
    }

    private void parsingDirection(int id)
    {
        Scanner sc = new Scanner(System.in);
        boolean isCorrectCMD = false;
        String input;

        while (!isCorrectCMD)
        {
            input = sc.nextLine();

            switch (input)
            {
                case "up":
                    isCorrectCMD = true;
                    terrain.dropItemAt(id, Vector2D.getVector2DUp());
                    break;
                case "down":
                    isCorrectCMD = true;
                    terrain.dropItemAt(id, Vector2D.getVector2DDown());
                    break;
                case "left":
                    isCorrectCMD = true;
                    terrain.dropItemAt(id, Vector2D.getVector2DLeft());
                    break;
                case "right":
                    isCorrectCMD = true;
                    terrain.dropItemAt(id, Vector2D.getVector2DRight());
                    break;
                case "cancel":
                    isCorrectCMD = true;
                    break;
            }

        }
    }

    private void parsingInventoryMenu(int id, Player player)
    {
        Scanner sc = new Scanner(System.in);
        boolean isCorrectCMD = false;
        String input;

        while (!isCorrectCMD)
        {
            input = sc.nextLine();

            switch (input)
            {
                case "equip":
                {
                    isCorrectCMD = true;
                    player.equip(player.getItemAt(id));
                    break;
                }case "use":
                {
                    isCorrectCMD = true;
                    Usable U;               //No fucking idea if this really work as intended

                    try{
                        U = Usable.class.cast(player.getItemAt(id));
                    }catch(ClassCastException e){
                        System.out.println("You can't use that item");
                        break;
                    }
                    U.Use(player);
                    player.removeInventory(player.getItemAt(id));
                    break;

                }case "drop":
                {
                    isCorrectCMD = true;
                    displayDirection();
                    parsingDirection(id);
                    break;
                }case "cancel":
                {
                    isCorrectCMD = true;
                    break;
                }
            }
        }
    }

    private void inventoryMenuManager(int id, Player player)
    {
        displayInventoryMenu();
        parsingInventoryMenu(id, player);
    }

    private void parsingInventory(Player player)
    {
        Scanner sc = new Scanner(System.in);
        boolean isCorrectCMD = false;
        String input;
        int id = -2;

        while (!isCorrectCMD)
        {
            System.out.println("Select the item's id or '-1' to cancel");
            input = sc.nextLine();

            try {
                id = Integer.parseInt(input);
            }catch (NumberFormatException e) {
                System.out.println("NaN");
                id = -2;
            }

            if(id == -1) isCorrectCMD = true;
            else if(id != -2)
            {
                isCorrectCMD = true;
                inventoryMenuManager(id, player);
            }
        }
    }

    private boolean parsingMenu(Player player, GameManager gm)
    {

        Scanner sc = new Scanner(System.in);
        boolean isCorrectCMD = false;
        String input;

        while (!isCorrectCMD) {
            input = sc.nextLine();
            String[] commands = input.split(" ");
            if(commands.length >= 1)
            {
                switch (commands[0])
                {
                    case "stats": {
                        isCorrectCMD = true;
                        player.displayStats();
                        break;
                    }

                    case "inventory": {

                        isCorrectCMD = true;
                        player.openInventory();
                        parsingInventory(player);
                        break;
                    }

                    case "save": {

                        isCorrectCMD = true;
                        if(SaveManager.saveInstance(gm))
                            System.out.println("Saved successfully");
                        else
                            System.out.println("Error during saving");
                        break;
                    }

                    case "exit": {
                        if (commands.length == 2)
                        {
                            switch (commands[1]) {
                                case "menu": {
                                    isCorrectCMD = true;                //exit menu
                                    break;
                                }

                                case "game": {
                                    GameManager.exitGame = true;        //exit game
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
