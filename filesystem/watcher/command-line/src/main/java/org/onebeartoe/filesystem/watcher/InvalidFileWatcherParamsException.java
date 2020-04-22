
package org.onebeartoe.filesystem.watcher;

import org.apache.commons.cli.ParseException;

public class InvalidFileWatcherParamsException extends ParseException
{
    private static final long serialVersionUID = 1L;

    public InvalidFileWatcherParamsException(String message) 
    {
        super(message);
    }
}
