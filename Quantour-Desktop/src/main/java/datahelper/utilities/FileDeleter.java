package datahelper.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by kevin on 2017/4/16.
 */
public class FileDeleter {

    public void deleteUniverseFile(String universeName){
        File file = new File(System.getProperty("user.dir") + File.separator + "universes" + File.separator + universeName + ".txt");
        file.delete();
    }

}
