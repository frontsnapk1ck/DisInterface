package frontsnapk1ck.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {

    public static String[] read(String path) 
    {
        try {
            File f = new File(path);
            Scanner s = new Scanner(f);
            List<String> data = new ArrayList<String>();
            
            while (s.hasNextLine())
                data.add( s.nextLine() );

            s.close();

            Object[] arr = data.toArray();
            String[] sArr = new String[arr.length]; 
            
            for (int i = 0; i < sArr.length; i++) 
            {
                sArr[i] = (String)arr[i];  
            }

            return sArr;

        } catch (IOException e) {
            System.err.println("error reading " + path);
            e.printStackTrace();
        }
        return null;
    }
    

    /**
     * 
     * @param path the path with wich you would like to read
     * @return  null is the file doesnt exit or it is not a folder 
     *          otherwise it returns list of all the filenames in the 
     *          that folder
     */
    public static String[] readFolderContents (String path)
    {
        File f = new File(path);
        if (!f.exists())
            return null;
        if (!f.isDirectory())
            return null;

        String[] arr = new String[f.listFiles().length];
        int i = 0;
        for (File sf : f.listFiles())
        {
            String name = sf.getName();
            name.replace(path, "");
            arr[i] = name;
            i++;
        }
        return arr;
    }

    

    public static String[] readFolderContents(File file) 
    {
		return readFolderContents(file.getPath());
	}


	public static boolean exists(String file) 
    {
        try {
            File f = new File(file);
            return f.exists();
        } catch (Exception e) 
        {
            return false;
        }
	}
}
