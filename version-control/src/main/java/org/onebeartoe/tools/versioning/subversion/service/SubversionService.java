
package org.onebeartoe.tools.versioning.subversion.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.onebeartoe.io.TextFileWriter;
import org.onebeartoe.system.CommandResults;
import org.onebeartoe.system.command.SystemCommand;
import org.onebeartoe.system.command.SystemCommandProfile;

/**
 *
 * @author Roberto Marquez
 */
public class SubversionService
{
//    private final SubversionService subversionService;
    
    private final TextFileWriter textFileWriter;
    
    public SubversionService()
    {
//        subversionService = new SubversionService();
        
        textFileWriter = new TextFileWriter();
    }            
    
    public void appendCreationDate() throws Exception
    {
        List<File> targetFiles = loadTargetFiles();
        
        List<String> errors = new ArrayList();
        
        targetFiles.parallelStream()
                   .forEach( f -> 
        {
            try
            {
                String repositoryPath = f.toString();
                
                String creationDate = creationDate(repositoryPath);
                String appendText = "\n" + "Date Created: " + creationDate + "\n";
                textFileWriter.writeText(f, appendText, true);
                
                System.out.println(creationDate + " - " + f.toString() );
            } 
            catch (Exception ex)
            {
                ex.printStackTrace();
                
                errors.add(f.toString() );
            }
        });
        
        printErrorFiles(errors);
    }    
    
    /**
     * This Subversion svn command returns the first revision for a repository path.
     * 
     * The command and parameters are from this stackoverflow answer:
     * 
     *      svn log -r 1:HEAD --limit 1 <reposiotry_URL>
     * 
     *      https://stackoverflow.com/a/1632214/803890
     * 
     * @param repositoryPath
     * @return a string representing the date of creation for the repository path.
     */
    public String creationDate(String repositoryPath) throws Exception
    {
        SystemCommandProfile profile = new SystemCommandProfile();
        String c = "svn log -r 1:HEAD --limit 1 " + repositoryPath;
        String [] strs = c.split("\\s+");
        List <String> commandAndArgs = Arrays.asList(strs);
        
        profile.commandAndArgs = commandAndArgs;
        profile.processStdErr = true;
        profile.processStdOut = true;
        
        SystemCommand command = new SystemCommand();
        command.setCommandProfile(profile);
        
        CommandResults results = command.execute();
        
// results.processedStdOut is something like the following        
//------------------------------------------------------------------------
//r11 | fisto | 2009-05-21 13:13:35 -0500 (Thu, 21 May 2009) | 1 line
//
//intial import
//------------------------------------------------------------------------        

        System.out.println("stdout: " + "\n" + results.processedStdOut);
        System.out.println();
        System.out.println("stderr: " + "\n" + results.processedStdErr);

        int firstPipe = results.processedStdOut.indexOf("|") + 1;
        int secondPipe = results.processedStdOut.indexOf("|", firstPipe ) + 1;
        int lastPipe = results.processedStdOut.indexOf("|", secondPipe);
        
        int begin = secondPipe;
        int end = lastPipe;
        
        String date = results.processedStdOut.substring(begin, end);

        date = date.trim();
        
        return date;
    }
    
    public List<File> loadTargetFiles() throws IOException
    {
        Path inpath = Paths.get("creation-date-targets.text");
        BufferedReader br = Files.newBufferedReader(inpath);
        
        List<File> targetFiles = br.lines()
//                                 .filter( line -> line.trim().length() == 0 )
                                 .map(File::new)
                                 
                                 .collect( Collectors.toList() );
        
        // validate the files
        targetFiles.parallelStream()
                   .forEach( f -> 
        {
            if( !f.exists() )
            {
                String message = "This input file does not exist: " + f.toString();
                throw new IllegalArgumentException(message);
            }
        });
        
        return targetFiles;
    }
    
    private void printErrorFiles(List<String> errors)
    {
        if(errors.size() > 0)
        {
            System.out.println("Errors occured with these files:");
        
            errors.forEach(System.out::println);
        }        
    }
    
    public void revertModified() throws IOException
    {
        List<File> targetFiles = loadTargetFiles();
        
        List<String> errors = new ArrayList();
        
        targetFiles.parallelStream()
                   .forEach( f -> 
        {
            try
            {
                String repositoryPath = f.toString();
                
                SystemCommandProfile profile = new SystemCommandProfile();
                String c = "svn revert " + repositoryPath;
                String [] strs = c.split("\\s+");
                List <String> commandAndArgs = Arrays.asList(strs);

                profile.commandAndArgs = commandAndArgs;
                profile.processStdErr = true;
                profile.processStdOut = true;

                SystemCommand command = new SystemCommand();
                command.setCommandProfile(profile);

                CommandResults results = command.execute();
                
                if(results.exitCode == 0)
                {
//                    System.out.println(repositoryPath + " - reverted");
                }
                else
                {
                    errors.add(repositoryPath);
                }                
            } 
            catch (Exception ex)
            {
                ex.printStackTrace();
                
                errors.add(f.toString() );
            }
            
            
        });        
        
        printErrorFiles(errors);
    }
}
