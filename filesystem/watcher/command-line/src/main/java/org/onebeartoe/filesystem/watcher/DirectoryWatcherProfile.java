
package org.onebeartoe.filesystem.watcher;

import java.nio.file.Path;
import java.time.Duration;

/**
 * This class holds meta data for a directory watcher service.
 */
public class DirectoryWatcherProfile
{
    Path directory;
    
    boolean recursive;
    
    String pattern;
    
    Duration quietPeriod;
    
    String command;
}
