/**
 * Copyright (C) 2009 andrey
 */
package ch.utils.eclipse.log;

/**
 * @author andrey
 *
 */
public class Logger
{
    private static ILog log;
    
    /**
     * Get log subsystem.
     * 
     * @param clazz
     * @return
     */
    public static ILog getLogger(Object clazz)
    {
        if (log == null)
        {
            log = new EclipseLog();
        }
        
        return log;
    }
}