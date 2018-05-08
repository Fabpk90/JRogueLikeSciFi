package Utils;

import java.util.Random;

public class NameGenerator
{
    static private String[] nameSuffix = {"Slime", "Troll", "Bandit", "Gnome"};
    static private String[] namePrefix = {"Smelly", "Angry", "Sweaty", "Dangerous"};

    static private Random r = new Random();

    static public String getRandomMonsterName()
    {
        return namePrefix[r.nextInt(namePrefix.length)] +" "+ nameSuffix[r.nextInt(nameSuffix.length)];
    }
}
