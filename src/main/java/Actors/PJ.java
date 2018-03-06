package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;
import java.util.ArrayList;
import java.util.List;

import static Utils.Constants.CPlayer;

public class PJ extends Actor {

    //public List<items> inv; Liste chainé représentant l'inventaire, je laisse en com le temps de faire les items

    public PJ()
    {
        super();
        //inv = new ArrayList <items>();
    }

    public PJ(Ansi.Color color, int health, int atk, int def, Vector2D position)
    {
        super(CPlayer, color, health, atk, def, position);
        //inv = new ArrayList <items>();
    }
}
