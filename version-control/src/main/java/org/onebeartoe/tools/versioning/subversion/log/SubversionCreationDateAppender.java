
package org.onebeartoe.tools.versioning.subversion.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.onebeartoe.io.TextFileWriter;
import org.onebeartoe.tools.versioning.subversion.service.SubversionService;

/**
 * @author Roberto Marquez
 */
public class SubversionCreationDateAppender
{
    private final SubversionService subversionService;
    
    private final TextFileWriter textFileWriter;
    
    public SubversionCreationDateAppender()
    {
        subversionService = new SubversionService();
        
        textFileWriter = new TextFileWriter();
    }
    
    public List<File> findTargetFiles(String inputDirectory) throws IOException
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
        
    public void appendCreationDate(List<File> targetFiles) throws Exception
    {
        List<String> errors = new ArrayList();
        targetFiles.parallelStream()
                   .forEach( f -> 
        {
            try//                   {
//        for(File f: targetFiles)
            {
                String repositoryPath = f.toString();
                
                String creationDate = subversionService.creationDate(repositoryPath);
                String appendText = "\n" + "Date Created: " + creationDate + "\n";
                textFileWriter.writeText(f, appendText, true);
                
                System.out.println(creationDate + " - " + f.toString() );
            } catch (Exception ex)
            {
                ex.printStackTrace();
                
                errors.add(f.toString() );
            }
        });
        
        if(errors.size() > 0)
        {
            System.out.println("Errors occured with these files:");
        
            errors.forEach(System.out::println);
        }
    }
}
