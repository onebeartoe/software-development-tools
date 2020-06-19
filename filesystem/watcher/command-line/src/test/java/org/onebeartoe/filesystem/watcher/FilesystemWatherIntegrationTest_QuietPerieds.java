
package org.onebeartoe.filesystem.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;
import static org.onebeartoe.system.Sleeper.sleepo;
import org.testng.annotations.Test;

//TODO: correct the mispelled wather
public class FilesystemWatherIntegrationTest_QuietPerieds 
{
    /**
     * This test integrates with input files on the local filesystem.
     * US01AC06 & US01AC09
     */
    @Test
     public void elapsesBeforeCommandIsExecuted() throws IOException 
    {
        Path directory = Paths.get("src/main");
        
        boolean recursive = true;

        long seconds = 10;
        
        Duration quietPerion = Duration.ofSeconds(seconds);

        String echoContent = "this is echo content";

        String currentTime = (new Date() ).toString();

        String ftwPath = "watch"+currentTime+".text";

        File fileToWatch = new File(ftwPath);

//TODO: rena me this to fileWatcherLogfile        
        File fwOutfile = new File("file-watcher.log");

        String command = String.format("cat %s >> %s",echoContent,ftwPath);

        DirectoryWatcherProfile profile = new DirectoryWatcherProfile();
        
        profile.pattern = "*.text"; 
        profile.quietPeriod = quietPerion;
        profile.command = command;
        profile.directory = directory;
        profile.recursive = recursive;

        DirectoryWatcher implementation = new DirectoryWatcher(profile);

//TODO: reomove fw and use 'implementation' 
        DirectoryWatcher fw = implementation;
        
        fw.start();

        // make sure the command DOES go off immediately after initialization 
        //      (no inital quiet period)
        String immediateEcho = "This is append text IMMIDIATELY after initialization.";
        append(fileToWatch, immediateEcho);
        assertFileToWatchContains(immediateEcho);
        
                
        // the quiet period just went off,
        // make sure the second command does NOT go off before the reset quiet period ends
        long duationInMillis = quietPerion.toMillis();
                
        sleepo( duationInMillis / 2);
        touch(fileToWatch); // restart quiete perid #1
        sleepo( duationInMillis / 2); // sleep for half the quite perid time

        String appendTtextBeforeQuiretElapses = "This is append text before quiet period elapses.";
        append(fileToWatch, appendTtextBeforeQuiretElapses);  // modify the file again before 
                                                              // the quiet perid ends,
                                                              // restarting quiete perid #2                                                                            
        assertFileOnlyHasOneLineFromInitial();

        // assert the quit period was restarted twice
        long restartedSleepo = quietPerion.toMillis() + Duration.ofSeconds(3).toMillis();
        sleepo(restartedSleepo);

        fw.terminate();

        assertFileContains( fwOutfile, "quitePeriodRestartMessage " + "#1");
        assertFileContains( fwOutfile, "quitePeriodRestartMessage " + "#2");

// how is this needed or is it redundant?        
        assertFileContains(fileToWatch, echoContent);
    }

    private void append(File fileToWatch, String immediateEcho)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertFileToWatchContains(String immediateEcho)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void touch(File fileToWatch)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertFileContains(File fwOutfile, String string)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertFileDoesOnlyHasOneLine()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertFileOnlyHasOneLineFromInitial()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
