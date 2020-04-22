package org.onebeartoe.filesystem.watcher;

import java.time.Duration;

import org.onebeartoe.application.RunProfile;

public class FileWatcherProfile extends RunProfile
{
	public String pattern1;
	public String command1;
        public Duration quietPeriod1;
        
        public String pattern2;
	public String expectedCommand2;
	public Duration quietPeriod2;
	
        public String logFile;
	
}
