

import Util.MapGenerator;

import static org.fusesource.jansi.Ansi.ansi;

public class Main {

   static public void  main(String [] args)
    {
        System.out.println(ansi().fgBlue().a(MapGenerator.getMap(8, 25, 8)));
    }

}
