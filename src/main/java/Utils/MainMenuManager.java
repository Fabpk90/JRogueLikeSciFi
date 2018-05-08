package Utils;

import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class MainMenuManager
{
    static public void Menu()
    {
        GameManager gm;
        String input;
        Scanner sc = new Scanner(System.in);
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
                && !(input.equals("load")) &&
                !(input.equals("exit")));

        if(input.equals("start"))
        {
            gm =  new GameManager(10);
            gm.render();
        }
        else if(isSaveAvaible && input.equals("load"))
        {
            gm=SaveManager.loadSave();
            if(gm != null)
                gm.render();
            else
                System.out.println("Error in loading the save!");
        }
    }
}
