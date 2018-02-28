package Util;

import java.io.*;

public class SaveManager {

    static public boolean saveInstance(GameManager gm)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream("save.yaya");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(gm);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }

        return true;
    }

    static public GameManager loadSave()
    {
        GameManager gm;

        try {
            FileInputStream fileIn = new FileInputStream("save.yaya");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gm = (GameManager) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return gm;
    }
}
