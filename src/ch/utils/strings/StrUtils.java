/**
 * Copyright (C) 2009 andrey
 */
package ch.utils.strings;

/**
 * @author andrey
 *
 */
public class StrUtils
{
    /**
     * If string is null return empty one.
     * 
     * @param str String for validation.
     * @return Processed string.
     */
    public static String getNotNull(String str)
    {
        return str == null ? "" : str;
    }
}