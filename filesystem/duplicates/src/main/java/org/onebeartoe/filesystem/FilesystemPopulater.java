//TODO: move this to its own module
package org.onebeartoe.filesystem;

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
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

/**
 * This class is entry point for a file system populater application.
 * 
 * This class places a user specified text file in each directory under the target 
 * directory.
 * 
 * For more information see the user story:
 * 
 *      https://github.com/onebeartoe/software-development-tools/issues/1
 * 
 * @author roberto marquez
 */
public class FilesystemPopulater extends SimpleFileVisitor<Path>
{
    private List<String> openScadDirectories;
    
    private List<String> nonOpenScadDirectories;
    
    private Logger logger;
    
    public static final String ONLY_SHOW_NON_OPENSCAD_DIRS = "onlyShowNonOpenscadDirs";
    
    public static final String ONLY_SHOW_OPENSCAD_DIRS = "onlyShowOpenscadDirs";
    
    public static final String POPULATION_FILE = "populationFile";
    
    public FilesystemPopulater()
    {
        openScadDirectories = new ArrayList();
        
        nonOpenScadDirectories = new ArrayList();
        
        String name = getClass().getSimpleName();
        
        logger = Logger.getLogger(name);
    }
    
    public Options buildOptions()
    {
        Option onlyShowNonOpenscadDirs = Option.builder()
                                               .required(false)
                                               .longOpt(ONLY_SHOW_NON_OPENSCAD_DIRS)
                                               .build();
        
        Option onlyShowOpenscadDirs = Option.builder()
                                               .required(false)
                                               .longOpt(ONLY_SHOW_OPENSCAD_DIRS)
                                               .build();
        
        Option populationFile = Option.builder()
                                      .longOpt(POPULATION_FILE)
                                      .hasArg(true)
                                      .build();
        
        Options options = new Options();
        options.addOption(onlyShowNonOpenscadDirs);
        options.addOption(onlyShowOpenscadDirs);
        options.addOption(populationFile);
        
        return options;
    }
    
    public static void main(String [] args) throws IOException, ParseException
    {
        File pwd = new File(".");
        
        Path start = pwd.toPath();
        
        FilesystemPopulater populater = new FilesystemPopulater();
        
        Options options = populater.buildOptions();
        try
        {
            RunProfile runProfile = populater.parseRunProfile(args, options);

            Files.walkFileTree(start, populater);

            if( runProfile.onlyShowNonOpenscadDirs )
            {
                System.out.println("Non OpenScad Directories");

                populater.nonOpenScadDirectories
                         .forEach(System.out::println);
            }

            if( runProfile.onlyShowOpenScadDirs )
            {
                System.out.println("OpenScad Directories");

                populater.openScadDirectories
                         .forEach(System.out::println);
            }
            else
            {
                File populationFile = runProfile.populationFile;
                populater.populate(populationFile);
            }        
        }
        catch(UnrecognizedOptionException uoe)
        {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar openscad-test-suite.jar [path]", options);        
            
            String errorMessage = uoe.getMessage();
            System.out.println("Error details: " + errorMessage);
        }
    }
    
    private RunProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        CommandLine command = parser.parse(options, args);
        
        RunProfile runProfile = new RunProfile();
        
        runProfile.onlyShowNonOpenscadDirs = command.hasOption(ONLY_SHOW_NON_OPENSCAD_DIRS);
        
        runProfile.onlyShowOpenScadDirs = command.hasOption(ONLY_SHOW_OPENSCAD_DIRS);
        
        if( !runProfile.onlyShowOpenScadDirs )
        {
            if( command.hasOption(POPULATION_FILE) )
            {
                String s = command.getOptionValue(POPULATION_FILE);
System.out.println("opf>" + s + "<opf");
command.getArgList().forEach(System.out::println);

                File f = new File(s);
                
                runProfile.populationFile = f;
            }
            else
            {
                String message = "A population file is needed, but missing." + 
                                   "  Use the --" + POPULATION_FILE + " option.";
                        
                throw new ParseException(message);
            }
        }
        
        return runProfile;
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
    
    private class RunProfile            
    {
        boolean onlyShowNonOpenscadDirs;
        
        boolean onlyShowOpenScadDirs;
        
        File populationFile;
    }
}
