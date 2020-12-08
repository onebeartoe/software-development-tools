
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
     * US01AC02_minimal & US01AC07
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
     * US01AC02_Full, 
     * 
     * US01AC04, 
     * 
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
         
        Duration actualQuitePeriod = profile.quietPeriod1;
        assertEquals(actualQuitePeriod, Duration.ofSeconds(30) );
    }    
    
    /**
     * This method takes a classpath path and reads text from it to create an array of strings
     * 
     * @param peropertiesFile
     * @return
     * @throws IOException
     */
//TODO: rename this classpathPropertiesToArgs
    private String[] classpathPropertiesToArgs(String classpathInfile) throws IOException
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
        
        FileWatcherProfile profile = parseArgs(args);
        
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
        
        return classpathPropertiesToArgs(classpathInfile);
    }

    private String[] minimalProfilePropsToStringArray() throws IOException
    {
        String classpathInfile = "/run-profiles/minimal-profile.properties";
        
        return classpathPropertiesToArgs(classpathInfile);
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

    /**
     * US01AC03 log file with other paramertes
     */
    @Test
    public void parseRunProfile_configFileWithOtherParmeters()
    { 
     
        String [] args =
        {
            "--configFile", "watcher.properties", 
            "--pattern1", "*.txt", 
            "--command1", "ls -la"
        };
       
        ParseException exception = null;
     
        try
        {
            parseArgs(args); // exception expected here
        } 
        catch (ParseException ex)
        {
            exception = ex;
        }
        
        String actual = exception.getMessage();
        
        String expected = "other options are not accepted with config file option";
        
        assertEquals(actual, expected);
    }



    
    /**
     * US01AC05_one
     */
    @Test
    public void parseRunProfileFails_missingRequiredParams_one() throws Exception
    {
        String classPath = "/run-profiles/invalid-command.propertries";
        
        String [] args = classpathPropertiesToArgs(classPath);

        ParseException exception = null;
     
        try
        {
            parseArgs(args); // exception expected here
        } 
        catch (ParseException ex)
        {
            exception = ex;
        }
        
        String actual = exception.getMessage();
        
        String expected = "pattern1 is missing command1 parameter";
        
        assertEquals(actual, expected);
    }
    
    /**
     * US01AC05_many
     */
    @Test//(expectedExceptions = InvalidFileWatcherParamsException.class)    
    public void parseRunProfileFails_missingRequiredParams_many() throws Exception
    {
        String [] args = 
        {
            "--pattern1", "*.txt", 
            "--command1", "ls -la",
            "--pattern2", "*.txt", 
            "--command2", "ls -la",
            "--pattern3", "*.txt"
        };

        ParseException exception = null;
     
        try
        {
            parseArgs(args); //excepton expected here
        } 
        catch (ParseException ex)
        {
            exception = ex;
        }
        
        String actual = exception.getMessage();
                
        String expected = "pattern3 is missing command3 parameter";
        
        assertEquals(actual, expected);
    }
}
