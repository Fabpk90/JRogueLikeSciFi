package Utils;


import java.util.Scanner;

import static Utils.SaveManager.loadSave;

public class MainMenuManager
{
    static public void Menu()
    {
        GameManager gm = new GameManager(15);
        String input;
        Scanner sc = new Scanner(System.in);
        String[] commands;

        do {
            System.out.println("Start or load ?");
            input = sc.nextLine();
            commands = input.split(" ");
        }while(!(commands[0].equals("start")) && !(commands[0].equals("load")));

        if(commands[0].equals("start"))
        {
            gm.render();
        }
        else if(commands[0].equals("load"))
        {
            gm=loadSave();
            gm.render();
        }
    }
}