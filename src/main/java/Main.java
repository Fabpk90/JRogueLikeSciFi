
import Actors.Actor;
import Utils.GameManager;

import static org.fusesource.jansi.Ansi.ansi;

public class Main {

   static public void  main(String [] args)
    {
        //testing actor
        //Actor act = new Actor();

        /* Testing colors
        Placeable pl = new Placeable('F', Ansi.Color.RED);

        System.out.println(ansi().fg(pl.getColor()).a("fuck "+pl.getGlyph()));
        */

        /* Testing save
        GameManager gm = new GameManager(10, 15);
        gm.generateMap(3);

        SaveManager.saveInstance(gm);

        System.out.println(SaveManager.loadSave().toString());
        */
        //Testing map generation
        //GameManager gm = new GameManager(10, 10);
        //System.out.println(ansi().fgBlue().a(gm.getMap(5)));
        //System.out.println(act.toString());
    }

}
