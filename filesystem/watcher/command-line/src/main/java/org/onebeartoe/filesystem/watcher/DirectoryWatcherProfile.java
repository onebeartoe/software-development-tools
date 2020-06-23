
package org.onebeartoe.filesystem.watcher;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds meta data for a directory watcher service.
 */
public class DirectoryWatcherProfile
{
    Path directory;
    
    boolean recursive;
    
    List<WatcherItem> watchItems;
    
    public DirectoryWatcherProfile()
    {
        watchItems = new ArrayList();
    }
}
