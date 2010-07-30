/**
 * Copyright (C) 2009 andrey
 */
package ch.utils.eclipse;

/**
 * @author andrey
 *
 */
public class HelperException extends Exception
{
    /**
     * @param message
     */
    public HelperException(String message)
    {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public HelperException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
