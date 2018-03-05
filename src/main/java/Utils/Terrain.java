package Utils;

public class Terrain {

    private int height;
    private int width;

    private char [][] charMatrix;

    //private Player player;
    //private ArrayList monsters;

    public Terrain(int height, int width)
    {
        this.height = height;
        this.width = width;
    }

    private void generateMap(int iterations)
    {
        charMatrix = MapGenerator.getMap(height, width, iterations);
    }


    private String getStringFromCharMatrix(char[][] charMatrix)
    {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                str.append(charMatrix[i][j]);
            }
            str.append('\n');
        }

        return str.toString();
    }

    //Generates and returns the map
    public String getMap()
    {
        generateMap(4);
        return getStringFromCharMatrix(charMatrix);
    }

    /*public void setPlayer(Player player)
    {
        this.player = player;
    }*/
}
