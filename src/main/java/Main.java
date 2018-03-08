
import Actors.Actor;
import Utils.GameManager;

import static org.fusesource.jansi.Ansi.ansi;

import java.util.Scanner;

public class Main {

   static public void  main(String [] args)
    {
        Scanner sc = new Scanner(System.in);
        String input;

        GameManager gm = new GameManager(10, 10);

        do {
            gm.render();
            input = sc.nextLine();
        }while(!input.equals("exit"));
    }

}
