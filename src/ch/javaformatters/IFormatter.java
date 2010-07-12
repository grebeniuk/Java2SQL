package ch.javaformatters;

import java.io.IOException;

public interface IFormatter
{
    /**
     * Format string at given position.
     * @param offset
     * @return
     * @throws IOException
     */
    public String getFormatedString(int offset, int lenght)
        throws IOException;
}
