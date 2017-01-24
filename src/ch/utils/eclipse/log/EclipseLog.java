/**
 * Copyright (C) 2009 Andrey Grebeniuk
 */
package ch.utils.eclipse.log;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import ch.chviews.Activator;
import ch.utils.strings.StrUtils;

/**
 * @author Andrey Grebeniuk
 */
public class EclipseLog implements ILog
{
    private final int ERROR = 0;
    private final int WARN = 1;
    private final int DEBUG = 2;
    
    private int Level = ERROR;
    
    public void debug(String msg)
    {
        debugCommon(msg, null);
    }
    
    public void debug(String msg, Throwable thr)
    {
        debugCommon(msg, thr);
    }

    public void error(String msg)
    {
        errorCommon(msg, null);
    }
    
    /*
     * (non-Javadoc)
     * @see ch.utils.eclipse.log.ILog#warn(java.lang.String)
     */
    public void warn(String msg)
    {
        warnCommon(msg, null);
    }
    
    /*
     * (non-Javadoc)
     * @see ch.utils.eclipse.log.ILog#warn(java.lang.String, java.lang.Throwable)
     */
    public void warn(String msg, Throwable thr)
    {
        warnCommon(msg, thr);
    }

    public void error(String msg, Throwable thr)
    {
        errorCommon(msg, thr);
    }

    private void warnCommon(String msg, Throwable thr)
    {
        Activator.getDefault().getLog().log(getWarnStatus(msg, thr));
    }
    
    /**
     * Common method for error outputing.
     * 
     * @param msg
     *            Error message.
     * @param thr
     *            Error object.
     */
    private void errorCommon(String msg, Throwable thr)
    {
        Activator.getDefault().getLog().log(getErrorStatus(msg, thr));
    }
    
    /**
     * Common method for debug outputing.
     * 
     * @param msg
     *            Debug message.
     * @param thr
     *            Debug object.
     */
    private void debugCommon(String msg, Throwable thr)
    {
        if (Level >= DEBUG)
        {
            Activator.getDefault().getLog().log(getDebugStatus(msg, thr));
        }
    }

    /**
     * Method for error Status instance returning. 
     * @param msg
     * @param thr
     * @return
     */
    private Status getErrorStatus(String msg, Throwable thr)
    {
        return getStatus(msg, thr, IStatus.ERROR);
    }
    
    /**
     * Method for debug Status instance returning. 
     * @param msg
     * @param thr
     * @return
     */
    private Status getDebugStatus(String msg, Throwable thr)
    {
        return getStatus(msg, thr, IStatus.INFO);
    }
    
    private Status getWarnStatus(String msg, Throwable thr)
    {
        return getStatus(msg, thr, IStatus.WARNING);
    }
    
    private Status getStatus(String msg, Throwable thr, int stType)
    {
        if (thr == null)
        {
            return new Status(stType, Activator.getDefault().getBundle()
                    .getSymbolicName(), 0, StrUtils.getNotNull(msg), null);
        }
        else
        {
            return new Status(stType, Activator.getDefault().getBundle()
                    .getSymbolicName(), 0, StrUtils.getNotNull(msg), thr);
        }
    }

}
