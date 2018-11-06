
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
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.RunProfile;

/**
 *
 * @author Roberto Marquez
 */
public class FilesystemPopulaterService extends AppletService
{
    private void populate(FilesystemPopulatorRunProfile runProfile) throws FileNotFoundException
    {
        runProfile.openScadDirectories.forEach((String d) -> 
        {
            Path source = runProfile.populationFile.toPath();
            
            try 
            {
                File outfile = new File(d, runProfile.populationFile.getName() );
                
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
                String message = "An error occured - " 
                                    + ex.getMessage() 
                                    + " - "
                                    + ex.toString();
                
                logger.severe(message);
            }
        });
    }    
    
    public void serviceRequest(RunProfile runProfile) throws Exception
    {
        FilesystemPopulatorRunProfile fspRunProfile = (FilesystemPopulatorRunProfile) runProfile;
        
        DirectoryVisitor populater = new DirectoryVisitor(fspRunProfile);
        
        File pwd = new File(".");
        
        Path start = pwd.toPath();
        
        Files.walkFileTree(start, populater);

        if( fspRunProfile.onlyShowNonOpenscadDirs )
        {
            System.out.println("Non OpenScad Directories");

            fspRunProfile.nonOpenScadDirectories
                     .forEach(System.out::println);
        }

        if( fspRunProfile.onlyShowOpenScadDirs )
        {
            System.out.println("OpenScad Directories");

            fspRunProfile.openScadDirectories
                     .forEach(System.out::println);
        }
        else
        {
            populate(fspRunProfile);
        }                
    }
    
    private class DirectoryVisitor extends SimpleFileVisitor<Path>
    {
        private FilesystemPopulatorRunProfile fspRunProfile;
        
        public DirectoryVisitor(FilesystemPopulatorRunProfile runProfile)
        {
            this.fspRunProfile = runProfile;
        }
        
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
        {
            File f = dir.toFile();

            boolean hadOpenscadFile = false;

            for(String filename : f.list() )
            {
                if( filename.endsWith(".scad") )
                {
                    fspRunProfile.openScadDirectories.add( f.getPath() );

                    hadOpenscadFile = true;

                    break;
                }
            }

            if( !hadOpenscadFile )
            {
                fspRunProfile.nonOpenScadDirectories.add(f.getPath() );
            }

            return FileVisitResult.CONTINUE;
        }        
    }
}
