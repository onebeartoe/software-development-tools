
package org.onebeartoe.filesystem.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import org.onebeartoe.io.TextFileWriter;
import static org.onebeartoe.system.Sleeper.sleepo;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;


/**
 * 
 * This test integrates with input files on the local filesystem.
 */

//TODO: correct the mispelled wather
public class FilesystemWatherIntegrationTest_QuietPerieds
{
    private Random random; 
    
    public FilesystemWatherIntegrationTest_QuietPerieds()
    {
        random = new Random();
    }
    
    @Test
    /**
     * US01AC06
     */    
     public void initialQuitPeriodIsIgnored() throws IOException 
    {
        String testName = "initialQuitPeriodIsIgnored";
                
        String immediateEcho = "This is append text IMMIDIATELY after initialization.";
        
        TestDirectoryWatcherProfile profile = watchProfile(testName, immediateEcho);

        DirectoryWatcher implementation = new DirectoryWatcher(profile);
            
        implementation.processEvents();
        
        File touchFile = new File(profile.directory.toFile(), "some-file.text");
        
        touch(touchFile);        
        
        // make sure the command goes off immediately after initialization 
        //      (no inital quiet period)        
        assertFileContains(touchFile, immediateEcho);
    }

//TODO: this test     
    @Test
    /**
     * US01AC09
     */
    public void givenAnUnElapsedQuietPeriodWhenTheFilesystemIsModifiedThenQuietPeriodsRestarts() throws IOException, Exception
    {
//TODO: BORROW THESE VARIABLE FROM THE 'initialQuitPeriodIsIgnored' method
Duration quietPeriod = null;
File fileToWatch = null;
DirectoryWatcher fw = null;
File fwOutfile = null;
String echoContent = null;
//TODO:END        
        
        
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
        
        
//TODO: implement        
        throw new Exception("implment");
    }

    @Test
//TODO: this test            
    /**
     * 
     * 
     * US01AC10 
     */
    public void filesystemModificationOccursBeforeTheQuitePeriod() throws Exception
    {
//TODO: implement        
        throw new Exception("implment");
    }

    private void append(File fileToWatch, String immediateEcho) throws IOException
    {
        TextFileWriter appender = new TextFileWriter();
        
        boolean append = true;
        
        appender.writeText(fileToWatch, immediateEcho, append);
    }

    private void assertFileContains(File fwOutfile, String target) throws IOException
    {
        byte[] bytes = Files.readAllBytes(fwOutfile.toPath() );
        
        String content = new String(bytes);
        
        assertTrue( content.contains(target) );
    }

    private void assertFileOnlyHasOneLineFromInitial()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void touch(File file) throws IOException
    {
        Path path = file.toPath();
        
        if (Files.exists(path)) 
        {
            Files.setLastModifiedTime(path, FileTime.from(Instant.now()));
        } 
        else 
        {
            Files.createFile(path);
        }
    }
    
    private File uniqueTargetDirectory(String testName)
    {
        long timeStamp = Calendar.getInstance().getTimeInMillis();
        
        int randomInt = random.nextInt();
        
        String path = "target/test-data/" + testName + '-' + timeStamp + '-' + randomInt;
        
        File directory = new File(path);
        
        directory.mkdirs();
        
        return directory;
    }

    private TestDirectoryWatcherProfile watchProfile(String testName, String echoContent)
    {
        File file = uniqueTargetDirectory(testName);
        
//TODO: use this instead of a File for fileToWatch
        Path directory = file.toPath();
        
        boolean recursive = true;

        long seconds = 10;
        
        Duration quietPeriod = Duration.ofSeconds(seconds);

//        String currentTime = (new Date() ).toString();

        String outpath = file.getAbsolutePath() + ".out";
        
        String command = String.format("echo %s >> %s", echoContent, outpath);

        TestDirectoryWatcherProfile profile = new TestDirectoryWatcherProfile();
                        
        profile.pattern = "*.text"; 
        profile.quietPeriod = quietPeriod;
        profile.command = command;
        profile.directory = directory;
        profile.recursive = recursive;

        return profile;        
    }

    private void assertFileToWatchContains2echoContents(File fileToWatch, String echoContent)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
