
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
import java.util.Random;
import java.util.stream.Stream;
import org.onebeartoe.io.TextFileWriter;
import static org.onebeartoe.system.Sleeper.sleepo;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

/**
 * 
 * This test integrates with input files on the local filesystem.
 */

//TODO: correct the mispelled wather
public class FilesystemWatherIntegrationTest_quietPeriods
{
    private Random random; 
    
    public FilesystemWatherIntegrationTest_quietPeriods()
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
        
        TestDirectoryWatcherProfile profile = assertInitialQuitPeriodIsIgnored(testName);
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

        String appendTtextBeforeQuiretElapses = "This-is append-text-before-quiet-period-elapses.";
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

    @Test//(testName = "US01AC10")
    /**
     * US01AC10 
     */
    public void filesystemModificationOccursBeforeTheQuitePeriod() throws Exception
    {
        String testName = "US01AC10";
        
        TestDirectoryWatcherProfile profile = assertInitialQuitPeriodIsIgnored(testName);
        
        WatcherItem item = profile.watchItems.get(0);
                        
        // wait half the quiet perid time
        long halfQuietTimeSleepoMillis = item.quietPeriod.toMillis() / 2;

        sleepo(halfQuietTimeSleepoMillis);
        
        // touch a file under watch directory
        File touchFile = new File(profile.directory.toFile(), "before-mod.text");
        
        touch(touchFile);        
        
        // waite one second
        sleepo( Duration.ofSeconds(1).toMillis() );
        
        // assert the watcher outfile file only contains one entry from initial (quiet period was restared)
        String watchOutpath = profile.watchItems.get(0).outpath;
        
        Path outpath = Paths.get(watchOutpath);
        
        Stream<String> initialStream = Files.lines(outpath);
        
        Object[] initailArray = initialStream.toArray();
        
        assertEquals(initailArray.length, 1);

        // waite the quiet period plus one second
        sleepo(item.quietPeriod.toMillis() + 1000);
        
        // assert the watcher out file now contains two entrys 
        Stream<String> postStream = Files.lines( touchFile.toPath() );
        
        Object[] postArray = postStream.toArray();
        
        assertEquals(postArray.length, 2);
    }

    private void append(File fileToWatch, String immediateEcho) throws IOException
    {
        TextFileWriter appender = new TextFileWriter();
        
        boolean append = true;
        
        appender.writeText(fileToWatch, immediateEcho, append);
    }

    private void assertFileContains(File infile, String target) throws IOException
    {
        byte[] bytes = Files.readAllBytes(infile.toPath() );
        
        String content = new String(bytes);
        
        assertTrue( content.contains(target) );
    }

    private void assertFileOnlyHasOneLineFromInitial()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void assertFileToWatchContains2echoContents(File fileToWatch, String echoContent)
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
        
        if(randomInt < 0)
        {
            randomInt *= -1;  // make it a positive value to avoid the '-' 
        }
        
        String path = "target/test-data/" + testName + '-' + timeStamp + '-' + randomInt;
        
        File directory = new File(path);
        
        directory.mkdirs();
        
        return directory;
    }

    private TestDirectoryWatcherProfile watchProfile(String testName, String echoContent)
    {
        File file = uniqueTargetDirectory(testName);
        
        Path directory = file.toPath();
        
        boolean recursive = true;

        long seconds = 10;
        
        Duration quietPeriod = Duration.ofSeconds(seconds);

        String outpath = file.getAbsolutePath() + ".out";
        
        String command = String.format("src/test/resources/integration/echo.sh %s %s", echoContent, outpath);

        TestDirectoryWatcherProfile profile = new TestDirectoryWatcherProfile();
                        
        WatcherItem item = new WatcherItem();

        item.outpath = outpath;
        item.pattern = "*.text"; 
        item.quietPeriod = quietPeriod;
        item.command = command;
        
        profile.watchItems.add(item);
        
        profile.directory = directory;
        profile.recursive = recursive;

        return profile;        
    }

    private TestDirectoryWatcherProfile assertInitialQuitPeriodIsIgnored(String testName) throws IOException
    {
//TODO: change the value to something more generic for all the tests        
        String immediateEcho = "This-is-append-text-IMMIDIATELY-after-initialization.";
        
        TestDirectoryWatcherProfile profile = watchProfile(testName, immediateEcho);

        DirectoryWatcher implementation = new DirectoryWatcher(profile);
            
        implementation.processEvents();
        
        File touchFile = new File(profile.directory.toFile(), "some-file.text");
        
        touch(touchFile);
        
        WatcherItem item = profile.watchItems.get(0);
        
        long sleepoMillis = item.quietPeriod.toMillis() / 4;  // wait a quarter of quite period 
        
        sleepo(sleepoMillis);
                
        // make sure the command goes off immediately after initialization 
        //      (no inital quiet period)
        File infile = new File(item.outpath);
        assertFileContains(infile, immediateEcho);
        
        return profile;
    }
}
