
package org.onebeartoe.filesystem.watcher;

import java.io.File;
import java.time.Duration;
import java.util.Calendar;
import java.util.TimerTask;

/**
 *
 */
class WatcherItem
{
    String pattern;
    
    Duration quietPeriod;
    
    String command;    
    
    String outpath;
    
    Calendar lastQuitePeriodEnded;
    
    TimerTask timerTask;

    String logPath;
    
    int resetCount;
    
    public WatcherItem()
    {        
        lastQuitePeriodEnded = Calendar.getInstance();
        
        // set to eariest time to ignore the initial quiet period
        lastQuitePeriodEnded.setTimeInMillis(0L);
        
        resetCount = 0;
    }
}
