package Actors;

import Utils.Vector2D;
import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

public class Placeable {

    public enum Tile
    {
        WALL('X', Ansi.Color.WHITE),
        FLOOR(' ', Ansi.Color.WHITE),
        ACTOR('A', Ansi.Color.YELLOW),
        PLAYER('P', Ansi.Color.BLUE),
        NPC('N', Ansi.Color.RED);

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
    }

    private Tile tile;
    private Vector2D position;

    public Placeable(char glyph, Ansi.Color color, Vector2D position)
    {
        //set a fake tile, just to have it set
        tile = Tile.FLOOR;
        tile.setGlyph(glyph);
        tile.setColor(color);

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
