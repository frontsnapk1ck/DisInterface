package frontsnapk1ck.utility.event;

import java.math.BigInteger;

import frontsnapk1ck.utility.logger.Logger;

public class Worker {

    public static final int WAITING     = 0;
    public static final int WORKING     = 1;
    public static final int FINISHED    = 2;
    public static final int STOP        = 10;

    private static Logger logger;

    private int state;
    private Thread workerThread;
    protected Job job;
    private BigInteger count;
    private String id;

    static {
        logger = new Logger();
    }

    public Worker(String id ) 
    {
        super();
        this.id = id;
        this.state = WAITING;
        this.count = BigInteger.ZERO;
        config();
    }

    public static void setLogger(Logger logger) 
    {
        Worker.logger = logger;
    }

    private void config() 
    {
        Runnable r = new Runnable(){
            @Override
            public void run() 
            {
                eventLoop();
            }
        };
        String name = "Worker [" + id + "]";
        this.workerThread = new Thread( r , name );
        this.workerThread.start();
    }

    protected void eventLoop() 
    {
        while (this.state != STOP)
        {
            try
            {
                iteration();
            }
            catch(Exception e)
            {
                logger.warn("Worker", "Worker " + id + " ran into a " + e.getClass().getSimpleName() + " while operating on a job with the error " + e.getMessage());
            }
        }
    }

    private void iteration() 
    {
        if ( this.job == null)
            cooldown(EventManager.COOLDOWN_INTERVAL);
        else
        {
            this.state = WORKING;
            this.job.execute();
            this.count = this.count.add(BigInteger.ONE);
            this.state = FINISHED;
            this.job = null;
        }
    }

    private void cooldown(Long time) 
    {
        try 
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e) {
        }
    }

    public void execute(Job j)
    {
        if (this.state == WORKING)
            throw new RuntimeException("Worker not finished");

        this.job = j;
    }

    public boolean isFinished() 
    {
        return state == FINISHED;
    }

    public boolean isWaiting()
    {
        return state == WAITING;
    }

    public boolean isWorking() 
    {
		return this.state == WORKING;
	}

	public Job getJob() 
    {
		return job;
	}

    public int getState() 
    {
        return state;
    }

	public String getName() 
    {
		return this.workerThread.getName();
	}

    public static String parseState (int i)
    {
    
        if (i == WAITING)
            return "WAITING";
        if (i == WORKING)
            return "WORKING";
        if (i == FINISHED)
            return "FINISHED";
        if (i == STOP)
            return "STOP";
        throw new RuntimeException("Could not parse the number: " + i );
    }

    public BigInteger getCompleted() 
    {
        return this.count;
    }

}
