
package org.onebeartoe.filesystem.watcher;

import java.time.Duration;

import org.onebeartoe.application.RunProfile;

public class FileWatcherProfile extends RunProfile
{
	public String pattern1;
	public String command1;
        public Duration quietPeriod1;
        
        public String pattern2;
	public String command2;
	public Duration quietPeriod2;
	
        public String pattern3;
	public String command3;
	public Duration quietPeriod3;
	
        public String logFile;
	
        public FileWatcherProfile()
        {
            Duration defaultQuietPeriod = Duration.ofSeconds(30);
            
            quietPeriod1 = defaultQuietPeriod;
            quietPeriod2 = defaultQuietPeriod;
            quietPeriod3 = defaultQuietPeriod;
        }
}
