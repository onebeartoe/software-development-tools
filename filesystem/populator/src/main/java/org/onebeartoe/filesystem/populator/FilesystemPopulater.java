
package org.onebeartoe.filesystem.populator;

import java.io.File;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.CommandLineInterfaceApplet;
import org.onebeartoe.application.RunProfile;

/**
 * This class is entry point for a file system populator application.
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
public class FilesystemPopulater extends CommandLineInterfaceApplet
{    
    private Logger logger;
    
    public static final String ONLY_SHOW_NON_OPENSCAD_DIRS = "onlyShowNonOpenscadDirs";
    
    public static final String ONLY_SHOW_OPENSCAD_DIRS = "onlyShowOpenscadDirs";
    
    public static final String POPULATION_FILE = "populationFile";
    
    public FilesystemPopulater()
    {        
        String name = getClass().getSimpleName();
        
        logger = Logger.getLogger(name);
    }
    
    @Override
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

    @Override
    protected AppletService getService()
    {
        return new FilesystemPopulaterService();
    }
    
    public static void main(String [] args) throws Exception
    {               
        CommandLineInterfaceApplet app = new FilesystemPopulater();
        app.execute(args);        
    }
    
    @Override
    public RunProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        CommandLine command = parser.parse(options, args);
        
        FilesystemPopulatorRunProfile runProfile = new FilesystemPopulatorRunProfile();
        
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
}