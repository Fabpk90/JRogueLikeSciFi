package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

public class Placeable implements Serializable {

    public enum Tile
    {
        WALL('X', Ansi.Color.WHITE),
        FLOOR('.', Ansi.Color.WHITE),
        TRAP('.', Ansi.Color.RED),
        ACTOR('A', Ansi.Color.YELLOW),
        PLAYER('P', Ansi.Color.BLUE),
        EXIT('E', Ansi.Color.GREEN),
        MONSTER('M', Ansi.Color.RED),
        ITEM('I', Ansi.Color.BLUE),
        NPC('N', Ansi.Color.YELLOW);

        private char glyph;
        private Ansi.Color color;

        Tile(char glyph, Ansi.Color color)
        {
            this.glyph = glyph;
            this.color = color;
        }

        public void setColor(Ansi.Color color) {
            this.color = color;
        }

        public void setGlyph(char glyph) {
            this.glyph = glyph;
        }

        public char getGlyph() {
            return glyph;
        }

        public Ansi.Color getColor() {
            return color;
        }

        @Override
        public String toString() {
            return ""+glyph;
        }
    }

    private Tile tile;
    private Vector2D position;

    public Placeable(Tile tile, Vector2D position)
    {
        this.tile = tile;
        this.position = position;
    }

    @Override
    public String toString()
    {
        return ""+ansi().fg(tile.color).a(tile.glyph);
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setPosition(int x, int y)
    {
        position.set(x, y);
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    public Vector2D getPosition() {
        return position;
    }
}
