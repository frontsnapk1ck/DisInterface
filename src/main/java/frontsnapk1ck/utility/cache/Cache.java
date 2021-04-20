package frontsnapk1ck.utility.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

public class Cache<K , H> {

    public static final long DEFAULT_TIME = 5000L;
    public static final long DEFAULT_INTERVAL = 50L;
    public static final int  DEFAULT_MAX_ITEMS = 1000;

    public static final long FOREVER = -1L;

    private long keepTime;
    private long interval;
    private LRUMap< K , CacheObject<H> > cacheMap;
    private Thread cacheThread;

    private boolean running;

    public Cache() 
    {
        this(DEFAULT_TIME);
    }

    public Cache(long keepTime) 
    {
        this(keepTime , DEFAULT_INTERVAL);
    }

    public Cache(long keepTime, int maxItems) 
    {
        this(keepTime, DEFAULT_INTERVAL, maxItems);
    }

    public Cache(long keepTime, long interval) 
    {
        this(keepTime, interval , DEFAULT_MAX_ITEMS);
    }


    public Cache(long keepTime, long interval, int maxItems) 
    {
        this.keepTime = keepTime;
        this.interval = interval;
        this.cacheMap = new LRUMap< K, CacheObject<H> >(maxItems);
        this.cacheThread = makeCacheThread();
        this.start();
    }
    
    private void start() 
    {
        this.running = true;
        this.cacheThread.start();
    }

    private Thread makeCacheThread() 
    {
        Thread t = new Thread( new Runnable()
        {
            public void run() 
            {
                cacheLoop();
            };
        } );
        t.setDaemon( true );
        t.setName("Cache Thread");
        return t;
    }

    protected void cacheLoop() 
    {
        while (this.running)
        {
            cooldown();
            cleanup();
        }
    }

    private void cooldown() 
    {
        try 
        {
            Thread.sleep(interval);
        }
        catch (InterruptedException e){
        }
    }

    public void cleanup() 
    {
        long now = System.currentTimeMillis();

        List<K> toRm = new ArrayList<K>();

        synchronized (cacheMap) 
        {
            MapIterator<K, CacheObject<H>> itr = cacheMap.mapIterator();            
            while (itr.hasNext())
            {
                K key = itr.next();
                CacheObject<H> c = itr.getValue();

                if (c != null && (now > (c.getKeepTime() + c.getLastAccessed())))
                    toRm.add(key);
            }

        }

        for (K key : toRm) 
        {
            synchronized (cacheMap) 
            {
                CacheObject<H> removed = cacheMap.remove(key);
                removed(removed.getValue());
            }
        }
    }

    protected void removed(H value) 
    {
        //nothing to do yet
    }

    public void put(K key, H value) 
    {
        this.put(key, value, keepTime);
    }

    public void put(K key, H value , long keepTime) 
    {
        synchronized (cacheMap) 
        {
            cacheMap.put(key, new CacheObject<H>(value , keepTime));
        }
    }
 
    public H get(K key) 
    {
        synchronized (cacheMap) 
        {
            CacheObject<H> c = (CacheObject<H>) cacheMap.get(key);
 
            if (c == null)
                return null;
            else 
                return c.getValue();
        }
    }
 
    public boolean remove(K key)
    {
        synchronized (cacheMap) 
        {
            return cacheMap.remove(key) != null;
        }
    }
 
    public int size() 
    {
        synchronized (cacheMap) 
        {
            return cacheMap.size();
        }
    }

    public boolean has(K key) 
    {
        synchronized (cacheMap) 
        {
            return cacheMap.containsKey(key);
        }
    }


    public void stop()
    {
        this.running = false;
    }

    /**
     * make sure you are careful and dont trigger any concurrent modification 
     * errors.
     * 
     * @return the {@link LRUMap} of a parameterized type to the {@link CacheObject<H>}. 
     */
    protected LRUMap<K, CacheObject<H>> getCacheMap() 
    {
        return cacheMap;
    }
    
}
