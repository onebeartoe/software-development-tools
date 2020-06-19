
package org.onebeartoe.filesystem.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Date;
import org.onebeartoe.io.TextFileWriter;
import static org.onebeartoe.system.Sleeper.sleepo;
import org.testng.annotations.Test;


/**
 * 
 * This test integrates with input files on the local filesystem.
 */

//TODO: correct the mispelled wather
public class FilesystemWatherIntegrationTest_QuietPerieds 
{
    @Test
    /**
     * US01AC06
     */    
     public void elapsesBeforeCommandIsExecuted() throws IOException 
    {
        Path directory = Paths.get("target/src/main");
        
        boolean recursive = true;

        long seconds = 10;
        
        Duration quietPeriod = Duration.ofSeconds(seconds);

        String currentTime = (new Date() ).toString();

        String ftwPath = "watch"+currentTime+".text";

        File fileToWatch = new File(ftwPath);

//TODO: rena me this to fileWatcherLogfile        
        File fwOutfile = new File("file-watcher.log");

        String echoContent = "this is echo content";        
        
        String command = String.format("cat %s >> %s", echoContent, ftwPath);

        DirectoryWatcherProfile profile = new DirectoryWatcherProfile();
        
        profile.pattern = "*.text"; 
        profile.quietPeriod = quietPeriod;
        profile.command = command;
        profile.directory = directory;
        profile.recursive = recursive;

        DirectoryWatcher implementation = new DirectoryWatcher(profile);

//TODO: reomove fw and use 'implementation' 
        DirectoryWatcher fw = implementation;
        
        fw.processEvents();

        // make sure the command DOES go off immediately after initialization 
        //      (no inital quiet period)
        String immediateEcho = "This is append text IMMIDIATELY after initialization.";
        append(fileToWatch, immediateEcho);
        assertFileToWatchContains(immediateEcho);
        
                
        // the quiet period just went off,
        // make sure the second command does NOT go off before the reset quiet period ends
        long duationInMillis = quietPeriod.toMillis();
                
        sleepo( duationInMillis / 2);
        touch(fileToWatch); // restart quiete perid #1
        sleepo( duationInMillis / 2); // sleep for half the quite perid time

        String appendTtextBeforeQuiretElapses = "This is append text before quiet period elapses.";
        append(fileToWatch, appendTtextBeforeQuiretElapses);  // modify the file again before 
                                                              // the quiet perid ends,
                                                              // restarting quiete perid #2                                                                            
        assertFileOnlyHasOneLineFromInitial();

        // assert the quit period was restarted twice
        long restartedSleepo = quietPeriod.toMillis() + Duration.ofSeconds(3).toMillis();
        sleepo(restartedSleepo);
        fw.terminate();
        assertFileContains( fwOutfile, "quitePeriodRestartMessage " + "#1");
        assertFileContains( fwOutfile, "quitePeriodRestartMessage " + "#2");

        assertFileToWatchContains2echoContents(fileToWatch, echoContent);
    }

//TODO: this test     
    @Test
    /**
     * US01AC09
     */
    public void givenAnUnElapsedQuietPeriodWhenTheFilesystemIsModifiedThenQuietPeriodsRestarts()
    {
//TODO: implement        
    }

//TODO: this test            
    /**
     * 
     * 
     * US01AC10 
     */
    public void filesystemModificationOccursBeforeTheQuitePeriod()
    {
//TODO: implement        
    }

    private void append(File fileToWatch, String immediateEcho) throws IOException
    {
        TextFileWriter appender = new TextFileWriter();
        
        boolean append = true;
        
        appender.writeText(fileToWatch, immediateEcho, append);
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

    private void assertFileToWatchContains2echoContents(File fileToWatch, String echoContent)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
