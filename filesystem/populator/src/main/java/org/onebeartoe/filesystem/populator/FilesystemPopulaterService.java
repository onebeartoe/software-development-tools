/*
 */

package org.onebeartoe.filesystem.populator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.RunProfile;

/**
 *
 * @author Roberto Marquez
 */
public class FilesystemPopulaterService extends AppletService
{
    public FilesystemPopulaterService()
    {
        
        
        
    }

    private void populate(File populationFile) throws FileNotFoundException
    {
        openScadDirectories.forEach((String d) -> 
        {
            Path source = populationFile.toPath();
            
            try 
            {
                File outfile = new File(d, populationFile.getName() );
                
                if( outfile.exists() )
                {
                    logger.warning("the outfile exists and was skipped: " + outfile.getPath() );
                }
                else
                {
                    FileOutputStream outstream = new FileOutputStream(outfile);

                    Files.copy(source, outstream);   
                }
            } 
            catch (IOException  ex) 
            {
                ex.printStackTrace();
                
                logger.severe("error:" + ex.toString() );
            }
        });
    }    
    
    public void serviceRequest(RunProfile runProfile) throws Exception
    {
        List<String> openScadDirectories = new ArrayList();
    
        List<String> nonOpenScadDirectories = new ArrayList();        
        
        FilesystemPopulatorRunProfile fspRunProfile = (FilesystemPopulatorRunProfile) runProfile;
        
        DirectoryVisitor populater = new DirectoryVisitor();
        
        File pwd = new File(".");
        
        Path start = pwd.toPath();
        
        Files.walkFileTree(start, populater);

        if( fspRunProfile.onlyShowNonOpenscadDirs )
        {
            System.out.println("Non OpenScad Directories");

            nonOpenScadDirectories
                     .forEach(System.out::println);
        }

        if( fspRunProfile.onlyShowOpenScadDirs )
        {
            System.out.println("OpenScad Directories");

            openScadDirectories
                     .forEach(System.out::println);
        }
        else
        {
            File populationFile = fspRunProfile.populationFile;
            populate(populationFile);
        }                
    }
    
    private class DirectoryVisitor extends SimpleFileVisitor<Path>
    {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
        {
            File f = dir.toFile();

            boolean hadOpenscadFile = false;

            for(String filename : f.list() )
            {
                if( filename.endsWith(".scad") )
                {
                    openScadDirectories.add( f.getPath() );

                    hadOpenscadFile = true;

                    break;
                }
            }

            if( !hadOpenscadFile )
            {
                nonOpenScadDirectories.add(f.getPath() );
            }

            return FileVisitResult.CONTINUE;
        }        
    }
}
