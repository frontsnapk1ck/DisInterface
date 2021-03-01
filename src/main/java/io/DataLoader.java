package io;

import java.util.List;

/**
 * t: type of object being loaded f: type of file being loaded
 */
public abstract class DataLoader< T , F >  {

    /**
     * 
     * @param file the location of what is being loaded
     * @return the thing requested
     */
    public T load( F file )
    {
        return null;
    }

    /**
     * 
     * @param file the location of the tings being loaded
     * @return a list of all the tings at that location
     */
    public List<T> loadALl( F file)
    {
        return null;
    }
    
}
