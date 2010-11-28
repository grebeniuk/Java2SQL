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
	private static final long serialVersionUID = -1451201755792546148L;

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