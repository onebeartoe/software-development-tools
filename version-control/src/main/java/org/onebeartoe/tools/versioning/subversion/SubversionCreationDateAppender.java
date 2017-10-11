
package org.onebeartoe.tools.versioning.subversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Roberto Marquez
 */
public class SubversionCreationDateAppender
{
    public List<File> findTargetFiles(String inputDirectory) throws IOException
    {        
        Path inpath = Paths.get("creation-date-targets.text");
        BufferedReader br = Files.newBufferedReader(inpath);
        
        List<File> targetFiles = br.lines()
                                 .filter( line -> line.trim().length() > 0 )
                                 .map(File::new)
                                 
                                 .collect( Collectors.toList() );
        
        // validate the files
        targetFiles.forEach( f -> 
        {
            if( !f.exists() )
            {
                String message = "This input file does not exist: " + f.toString();
                throw new IllegalArgumentException(message);
            }
        });
        
        return targetFiles;
    }
        
    public void appendCreationDate(List<File> targetFiles)
    {
        System.out.println("not gonna do it");
    }
}
