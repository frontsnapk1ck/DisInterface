package frontsnapk1ck.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Saver {

    public static void saveAppend (String path , String save)
    {
        try {
            File f = new File(path);
            Scanner s = new Scanner(f);
            String old = "";

            while (s.hasNextLine())
                old += s.nextLine() + "\n";

            FileWriter fw = new FileWriter(path);
            fw.write(old);
            fw.append(save);

            fw.close();
            s.close();

        } catch (IOException e) 
        {
            System.err.println("an error occurred while appending to " + path);
            e.printStackTrace();
        }
    }

    public static void saveOverwrite (String path , String[] save)
    {
        try {
            FileWriter fw = new FileWriter(path);
            fw.write("");
            
            for (String string : save) 
                fw.append(string + "\n");

            fw.close();

        } catch (IOException e) 
        {
            System.err.println("an " + e.getClass().getSimpleName() + " occurred while saving to " + path);
            e.printStackTrace();
        }
    }
    

    /**
     * 
     * @param path
     * @return true if a new file is created
     */
    public static boolean saveNewFile (String path)
    {
        try {
            File file = new File(path);
            if (file.createNewFile())
                return true;
            else
                return false;
        } catch (IOException e) {
           System.err.println("an error has occurred");
           e.printStackTrace();
        }
        return false;
    }

    public static void deleteFiles(String path) 
    {
        File f = new File(path);
        File[] sub = f.listFiles();
        for (File f2 : sub) 
        {
            if (!f2.delete())
                deleteFiles(f2.getPath());
        }
        f.delete();
    }

    public static boolean deleteFile(String path) 
    {
        File f = new File(path);
        return f.delete();
    }

    public static void copyFrom(String from, String to) 
    {
        String[] out = FileReader.read(from);
        saveOverwrite(to, out);
	}

    public static boolean newFolder(String path) 
    {
        File dir = new File(path);
        if (!dir.exists())
        {
            dir.mkdir();
            return true;
        }
        return false;
	}

    public static void saveNewFile(String path, String[] save) 
    {
        saveNewFile(path);
        saveOverwrite(path, save);
	}

    public static boolean saveNewFolder(String path) 
    {
        try {
            File file = new File(path);
            if (file.mkdir())
                return true;
            else
                return false;
        } catch (SecurityException e) {
           System.err.println("an error has occurred\nsomething about perms\n\n");
           e.printStackTrace();
        }
        return false;
    }

    public static void clear(String path) 
    {
        saveOverwrite( path , new String[]{} );
	}


}
