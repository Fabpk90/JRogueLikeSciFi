
import Actors.Actor;
import Utils.GameManager;

import static org.fusesource.jansi.Ansi.ansi;

public class Main {

   static public void  main(String [] args)
    {
        GameManager gm = new GameManager(10, 10);
        gm.render();
    }

}
