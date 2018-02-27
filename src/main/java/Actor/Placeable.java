package Actor;

import org.fusesource.jansi.Ansi;

public class Placeable {

    private char glyph;
    private Ansi.Color color;

    public Placeable(char glyph, Ansi.Color color)
    {
        this.glyph = glyph;
        this.color = color;
    }

    public char getGlyph()
    {
        return glyph;
    }

    public void setGlyph(char glyph)
    {
        this.glyph = glyph;
    }

    public Ansi.Color getColor() {
        return color;
    }

    public void setColor(Ansi.Color color) {
        this.color = color;
    }
}
