package Utils;

import Actors.Player;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainMenuManager
{
    static private Scanner sc = new Scanner(System.in);
    static public void Menu()
    {
        GameManager gm;
        String input;

        boolean isSaveAvaible = SaveManager.saveExists();

        System.out.println(ansi().fgBrightGreen().a("Hello adventurer!"));

        do {
            System.out.println("Start");
            if(isSaveAvaible)
                System.out.println("Load");

            System.out.println("Exit");
            input = sc.nextLine();
            input = input.toLowerCase();
        }while(!(input.equals("start"))
                && !(input.equals("load"))
                && !(input.equals("exit")));

        if(input.equals("start"))
        {
            gm =  new GameManager(10, createPlayer());
            gm.render();
        }
        else if(isSaveAvaible && input.equals("load"))
        {
            gm = SaveManager.loadSave();
            if(gm != null)
            {
                gm.loaded();
                gm.render();
            }

            else
                System.out.println("Error in loading the save!");
        }
    }

    static private Player createPlayer()
    {
        Player p = new Player(15, 12, 10);

        System.out.println("Welcome to the creation menu");
        System.out.println("Please insert your name adventurer");

        //TODO: check the input

        p.setName(sc.nextLine());


        return p;
    }
}
