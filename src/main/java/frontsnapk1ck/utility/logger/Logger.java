package frontsnapk1ck.utility.logger;

import static frontsnapk1ck.utility.logger.Level.DEBUG;
import static frontsnapk1ck.utility.logger.Level.ERROR;
import static frontsnapk1ck.utility.logger.Level.INFO;
import static frontsnapk1ck.utility.logger.Level.WARN;

import org.slf4j.LoggerFactory;

public class Logger implements Loggable {

    private org.slf4j.Logger logger;

    public Logger() 
    {
    }

    @Override
    public void warn(String className, String error) {
        onReceive(className, error, WARN);
        logger = LoggerFactory.getLogger(className);
        logger.warn(error);
        logger = null;
    }

    public void error(String className, Throwable e) 
    {
        this.error(className, e.getMessage());
    }

    public void error( String className, String message ) 
    {
        onReceive(className, message, ERROR);
        logger = LoggerFactory.getLogger(className);
        logger.error(message);
        logger = null;
    }

    @Override
    public void debug(String className, String message) 
    {
        onReceive(className, message, DEBUG);
        logger = LoggerFactory.getLogger(className);
        logger.debug(message);
        logger = null;
    }

    public void debug(String className, Exception e) 
    {
        this.debug(className, e.getMessage());
	}

    @Override
    public void info(String className, String message) 
    {
        onReceive(className , message , INFO);
        logger = LoggerFactory.getLogger(className);
        logger.info(message);
        logger = null;
    }
    
    private void onReceive(String className, String message, Level level)
    {
        Thread t = Thread.currentThread();
        onReceive(className, message, null , level , t);
    }
    
    protected void onReceive(String className, String message, Throwable error , Level level, Thread t) 
    {
        // nothing to do yet
    }
}
