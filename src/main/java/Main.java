

import Actor.Placeable;
import Util.MapGenerator;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

public class Main {

   static public void  main(String [] args)
    {
        Placeable pl = new Placeable('F', Ansi.Color.RED);

        System.out.println(ansi().fg(pl.getColor()).a("fuck "+pl.getGlyph()));
        //System.out.println(ansi().fgBlue().a(MapGenerator.getMap(11, 25, 3 )));
    }

}
