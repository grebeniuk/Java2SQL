/**
 * Copyright (C) 2009 andrey
 */
package ch.utils.eclipse.log;

/**
 * @author andrey
 *
 */
public interface ILog
{
    /**
     * Write debug to log.
     * 
     * @param msg Error message.
     */    
    void debug(String msg);
    
    /**
     * Write debug to log.
     * 
     * @param msg Error message.
     */    
    void debug(String msg, Throwable thr);
    
    /**
     * Write warning to log.
     * 
     * @param msg Error message.
     */  
    void warn(String msg);
    
    /**
     * Write warning to log.
     * 
     * @param msg Error message.
     * @param thr Error object.
     */  
    void warn(String msg, Throwable thr);
    
    /**
     * Write error to log.
     * 
     * @param msg Error message.
     */
    void error(String msg);

    /**
     * Write error to log with exception stack trace.
     * 
     * @param msg Error message.
     * @param thr Error object.
     */
    void error(String msg, Throwable thr);
}
