
package org.onebeartoe.filesystem.watcher;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.io.TextFileReader;
import org.onebeartoe.io.buffered.BufferedTextFileReader;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class FilesystemWatherSpecification_runProfileParams 
{
    FilesystemWatcherService service = new FilesystemWatcherService();

    /**
//TODO: assert this acceptance critieria!!!!!!!!!
     * US01AC02_minimal
     */
    @Test
     public void parseRunProfile_prpertiesFileOnCommandLIne_minimal() throws ParseException
     {
        String propertiesInfilePath = "src/test/resources/run-profiles/minimal-profile.properties";
        
        File infile = new File(propertiesInfilePath);
        
        FileWatcherProfile fwp = configFileToProfile(infile);
        
        assertMinimalProfile(fwp);
     }
    
    
    /**
//TODO: assert this acceptance critieria!!!!!!!!!
     * US01AC02_Full, 
     * 
//TODO: assert this acceptance critieria!!!!!!!!!
     * US01AC04, 
     * 
//TODO: assert this acceptance critieria!!!!!!!!!
     * US01AC08
     * 
     * @throws IOException
     * @throws InvalidFileWatcherParamsException
     */
     @Test
     public void parseRunProfile_prpertiesFileOnCommandLIneAndLogFileAndQuietPeriodPerFilePattern()
             throws InvalidFileWatcherParamsException, IOException, ParseException 
     { 
        String propertiesInfilePath = "src/test/resources/run-profiles/full-profile.properties";
        
        File infile = new File(propertiesInfilePath);
        
        FileWatcherProfile fwp = configFileToProfile(infile);
      
        assertFullProfile(fwp);
      }

    private void assertFullProfile(FileWatcherProfile fwp)
    {
        String expectedPattern1 = "*.jpg"; 
        assertEquals( fwp.pattern1, expectedPattern1);       
        
        String expectedCommand1 = "ls -ltr"; 
        assertEquals( fwp.command1, expectedCommand1);

        Duration expectedQuietPeriod1 = Duration.ofMinutes(21); 
        assertEquals( fwp.quietPeriod1, expectedQuietPeriod1);        
   
        String expectedPattern2 = "*.java" ;
        assertEquals( fwp.pattern2, expectedPattern2);


        String expectedCommand2 = "mvn test";
        assertEquals( fwp.command2, expectedCommand2);

        Duration expectedQuietPeriod2 = Duration.ofMinutes(1); 
        assertEquals( fwp.quietPeriod2, expectedQuietPeriod2);
        
        String expectedLogFile = "watcher.log"; 
        assertEquals(fwp.logFile, expectedLogFile);      
    }

    private void assertMinimalProfile(FileWatcherProfile profile)
    {
        String expectedPattern1 = "*.text"; 
         assertEquals(profile.pattern1, expectedPattern1);       
        
         String expectedCommand1 = "wc -l"; 
         assertEquals(profile.command1, expectedCommand1);
    }    
    
//TODO: is this production code?
//TODO: is this even used?    
private FileWatcherProfile propsToProfile(String classpathInfile) throws IOException, InvalidFileWatcherParamsException, ParseException
{
                                // classpathPropertiesToArgs()
    String [] propsToStringArray = propsToStringArray(classpathInfile);

    FileWatcherApplication fwApp = new FileWatcherApplication();

    Options options = fwApp.buildOptions();

    FileWatcherProfile profile = fwApp.parseRunProfile(propsToStringArray, options);

    return profile;
}


// TODO: is this production code?
    /**
     * This method takes a classpath path and reads text from it to create an array of strings
     * 
     * @param peropertiesFile
     * @return
     * @throws IOException
     */
//TODO: rename this classpathPropertiesToArgs
    private String[] propsToStringArray(String classpathInfile) throws IOException
    {
        TextFileReader reader = new BufferedTextFileReader();

        List<String> allLines = reader.readTextLinesFromClasspath(classpathInfile);

        return service.linesToArgs(allLines);
    }
    
    /**
     * US01AC01_minimal - profile via command line args
     * 
     * @throws IOException
     * @throws InvalidFileWatcherParamsException
     */
    @Test
    public void profileViaCommandLineArgs_minimalProfile() throws IOException, InvalidFileWatcherParamsException, ParseException 
    {
        String [] args = minimalProfilePropsToStringArray();
//TODO: use parseArgs()
        FileWatcherApplication fwpApp = new FileWatcherApplication(); 
        
        Options options = fwpApp.buildOptions();

        FileWatcherProfile profile = fwpApp.parseRunProfile(args, options);        
        
        assertMinimalProfile(profile); 
    }
    
    /**
     * US01AC01_full - profile via command line args
     * 
     * @throws IOException
     * @throws InvalidFileWatcherParamsException
     */
    @Test
    public void profileViaCommandLineArgs_fullProfile() throws IOException, InvalidFileWatcherParamsException, ParseException 
    { 
        String [] args = fullProfilePropsToStringArray();

        FileWatcherProfile profile = parseArgs(args);
        
        assertFullProfile(profile); 
    }

    private String[] fullProfilePropsToStringArray() throws IOException 
    {
        String classpathInfile = "/run-profiles/full-profile.properties";
        
        return propsToStringArray(classpathInfile);
    }

    private String[] minimalProfilePropsToStringArray() throws IOException
    {
        String classpathInfile = "/run-profiles/minimal-profile.properties";
        
        return propsToStringArray(classpathInfile);
    }

    private FileWatcherProfile parseArgs(String [] args) throws ParseException
    {
        FileWatcherApplication fwpApp = new FileWatcherApplication(); 
        
        Options options = fwpApp.buildOptions();

        FileWatcherProfile profile = fwpApp.parseRunProfile(args, options);
            
        return profile;
    }
            
    private FileWatcherProfile configFileToProfile(File infile) throws ParseException
    {
        String path = infile.getPath();
        
        String [] args = {"--configFile", path};        
    
        return parseArgs(args);
    }

 //TODO: reenable this test
/**
 * US01AC03 log file with other param{ertes
 */
// @Test(expectedExceptions = InvalidFileWatcherParamsException.class)
// public void logfileWithOHtreParmeters() throws InvalidFileWatcherParamsException 
// { 
//     String [] args =
//     {
//         "--fileWatrcherLogFile", "watcher.log", 
//         "--pattern1", "*.txt", 
//         "--command1", "ls -la"
//     };
  
//     FileWatcherApplication fwApp = new FileWatcherApplication();
  
//     Options options = null;

//     fwApp.parseRunProfile(args, options); //excepton expected here }
// }


//TODO: !!!!!!!!!!!!ENABLE THIS TEST !!!!!!!!!!!!!!    
//    @Test(expectedExceptions = InvalidFileWatcherParamsException.class)
    /**
     * US01AC05
     */
    // public void parseRunProfileFails_missingRequiredParams() throws Exception
    // {
    //         String classPath = "/run-profiles/invalid-command.propertries";
            
    //         String [] args = propsToStringArray(classPath);

    //         FileWatcherApplication fwApp = new FileWatcherApplication();

    //         Options options = null;

    //         fwApp.parseRunProfile(args, options); // exception expected here
    // }
}
