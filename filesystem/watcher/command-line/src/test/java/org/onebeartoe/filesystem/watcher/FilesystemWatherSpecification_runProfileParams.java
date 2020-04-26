
package org.onebeartoe.filesystem.watcher;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.io.TextFileReader;
import org.onebeartoe.io.buffered.BufferedTextFileReader;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class FilesystemWatherSpecification_runProfileParams 
{
//TODO: !!!!!!!!!!!!ENABLE THIS TEST !!!!!!!!!!!!!!    
    /**
     * US01AC02_Full, US01AC04, US01AC08
     * 
     * @throws IOException
     * @throws InvalidFileWatcherParamsException
     */
    // @Test
    // public void prpertiesFileOnCommandLIneAndLogFileAndQuietPeriodPerFilePattern()
    //         throws InvalidFileWatcherParamsException, IOException 
    // { 
    //     String classpathInfile = "/run-profiles/full-profile.properties";
        
    //     FileWatcherProfile fwp = propsToProfile( classpathInfile);
//assertFullProfile(fwp      );
    //  }

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
private FileWatcherProfile propsToProfile(String classpathInfile) throws IOException, InvalidFileWatcherParamsException, ParseException
{
    String [] propsToStringArray = propsToStringArray(classpathInfile);

    Options options = null;

    FileWatcherApplication fwApp = new FileWatcherApplication();

    FileWatcherProfile profile = fwApp.parseRunProfile(propsToStringArray, options);

    return profile;
}


// TODO: is this production code?
    /**
     * This method take an input stream and read text from it to create an array of strings
     * 
     * @param peropertiesFile
     * @return
     * @throws IOException
     */
    private String[] propsToStringArray(String classpathInfile) throws IOException
    {
        TextFileReader reader = new BufferedTextFileReader();

        List<String> allLines = reader.readTextLinesFromClasspath(classpathInfile);

        List <String> lines = allLines
                        .stream()
                        .filter(line -> !line.startsWith("#") )        
                        .collect( Collectors.toList() );

        List<String> propertyLines = lines.stream()
                                    .filter(l -> l.contains("="))
                                    .collect( Collectors.toList() );

        List<String> tokens = new ArrayList();

        for(String line : propertyLines)
        {
            String [] split = line.split("=");

            tokens .add ("--" + split[0]);

            tokens.add(split[1]);
        }

        return tokens.toArray( new String[0] );
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

        FileWatcherApplication fwpApp = new FileWatcherApplication(); 
        
        Options options = fwpApp.buildOptions();

        FileWatcherProfile profile = fwpApp.parseRunProfile(args, options);
    
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
