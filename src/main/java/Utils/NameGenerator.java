package Utils;

import Items.Equipment;

import java.util.Random;

public class NameGenerator
{
    static private String[] nameSuffix = {"Robot", "Sentinel", "Automated Turret", "Computer", "Mech"};
    static private String[] namePrefix = {"Haywire", "Angry", "Forsaken", "Dangerous", "HAL-like", "Mecha"};

    static private String[] armorSuffix = {"Suit", "Protection", "Exo-suit", "Cloths"};
    static private String[] weaponSuffix = {"Energy sword", "Plasma Claws", "Metal Pipe", "Orbs"};
    static private String[] equipmentPrefix = {"Dirty", "Old", "Godly", "Superior", "Damaged", "Prototype", "Annoying", "Unpleasant", "Red & White", "Celestial"};

    static private String[] potionSuffix = {"medkit", "Bandages", "Sanitizer", "Painkiller", "first-aid kit", "pizza"};
    static private String[] itemPrefix = {"Makeshift", "Regular", "Advanced", "Military-grade", "Unknown"};

    static private Random r = new Random();

    static public String getRandomMonsterName()
    {
        return namePrefix[r.nextInt(namePrefix.length)] +" "+ nameSuffix[r.nextInt(nameSuffix.length)];
    }

    static public String getRandomArmorName()
    {
        return equipmentPrefix[r.nextInt(equipmentPrefix.length)] +" "+ armorSuffix[r.nextInt(armorSuffix.length)];
    }

    static public String getRandomWeaponName()
    {
        return equipmentPrefix[r.nextInt(equipmentPrefix.length)] +" "+ weaponSuffix[r.nextInt(weaponSuffix.length)];
    }

    static public String getRandomPotionName()
    {
        return itemPrefix[r.nextInt(itemPrefix.length)] +" "+ potionSuffix[r.nextInt(potionSuffix.length)];
    }
}
