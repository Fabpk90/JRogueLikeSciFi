package Utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaveManager {

    final static private  String saveName = "save.dat";


    static public boolean saveInstance(GameManager gm)
    {
        try {
            FileOutputStream fileOut = new FileOutputStream(saveName);
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
            FileInputStream fileIn = new FileInputStream(saveName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            gm = (GameManager) in.readObject();
            in.close();
            fileIn.close();
        }catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }

        return gm;
    }

    static public boolean saveExists()
    {
        return Files.exists(Paths.get(saveName));
    }
}
