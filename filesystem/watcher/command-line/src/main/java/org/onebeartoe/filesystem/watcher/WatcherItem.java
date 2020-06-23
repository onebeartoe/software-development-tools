
package org.onebeartoe.filesystem.watcher;

import java.time.Duration;

/**
 *
 */
class WatcherItem
{
    String pattern;
    
    Duration quietPeriod;
    
    String command;    
    
    String outpath;
}
