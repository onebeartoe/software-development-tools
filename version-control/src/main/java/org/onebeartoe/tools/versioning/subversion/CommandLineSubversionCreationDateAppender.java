
package org.onebeartoe.tools.versioning.subversion;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Roberto Marquez
 */
public class CommandLineSubversionCreationDateAppender
{
    public static void main(String [] args) throws IOException
    {
        System.out.println("Welcome to the command line version of the onebeartoe" + 
                           " Subversion creation date application.");
        
        System.out.println("The following files are set to have the creation date appended:");
    
        SubversionCreationDateAppender appender = new SubversionCreationDateAppender();
        
        String inputDir = ".";
        
        File f = new File(inputDir);
        System.out.println("input directory: " + f.getAbsolutePath() );

        List<File> targetFiles = appender.findTargetFiles(inputDir);
        
        appender.appendCreationDate(targetFiles);
    } 
}
