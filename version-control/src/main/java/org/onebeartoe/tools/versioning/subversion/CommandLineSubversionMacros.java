
package org.onebeartoe.tools.versioning.subversion;

import java.time.Instant;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.onebeartoe.application.duration.DurationService;
import org.onebeartoe.tools.versioning.subversion.service.SubversionService;

/**
 * To create a creation-date.text file for a WEB/jsp/ directory use this find command:
 * 
 *     find . -type f -not -name properties.jsp -not -name properties.jspf -not -name bottom.jsp -not -name "*.section.properties.jspf" -not -name "*section-properties.jspf" -not -name "*.section-properties.jsp" -not -name "*page.properties.jspf" -not -name "*section-properties.jsp" -not -name "*.properties.jsp" -not -name "*-properties.jsp" -not -name "*page-properties.jspf"  -not -name "*section-propeerties.jspf"  -not -name "*.section.propeerties.jspf" -not -name "*top.jspf" > creation-date-targets.text
 * 
 * For the regular non index.jsp files (search.jsp, about.jsp, ect...) that need a timestamp appended 
 * use this find command:
 * 
see command REGULAR_NON_INDEX_COMMAND
 * 
 * @author Roberto Marquez
 */
// REGULAR_NON_INDEX_COMMAND
// find . -type f -not -path "*/WEB-INF/*" -and -not -path "*/index.jsp" -and -path "*.html*" -o -name "*.jsp*" -not -name index.jsp -not -name "*properties.jsp*" -not -path "*results.htmlf" | grep -v results.htmlf | grep -v tabs.jspf | grep -v "/WEB-INF/" | grep -v "/usage.jsp" | grep -v "/screen-shots.jsp" | grep -v "embed.htmlf" | grep -v "embed.jspf" > creation-date-targets.text
public class CommandLineSubversionMacros
{
    private static final String REVERT_MODIFIED = "revertModified";
    
    private static final String APPEND_CREATE_DATE = "appendCreateDate";
    
    private final SubversionService subversionService;
    
    public CommandLineSubversionMacros()
    {
        subversionService = new SubversionService();
    }
    
    private Options buildOptions()
    {
        Option revertModified = Option.builder()
                    .required(false)
                    .longOpt(REVERT_MODIFIED)
                    .build();
        
        Option appendCreateDate = Option.builder()
                                    .required(false)
                                    .longOpt(APPEND_CREATE_DATE)
                                    .build();
        
        Options options = new Options();
        options.addOption(appendCreateDate);
        options.addOption(revertModified);
        
        return options;
    }
    
    public static void main(String [] args) throws Exception
    {
        System.out.println("Welcome to the command line version of the onebeartoe" + 
                           " Subversion creation date application.");
        
        System.out.println("The following files are set to have the creation date appended:");
    
        CommandLineSubversionMacros app = new CommandLineSubversionMacros();
        
        Options options = app.buildOptions();
        try
        {
            SubversionMacrosRunProfile runProfile = app.parseRunProfile(args, options);

            Instant start = Instant.now();
            
            app.service(runProfile);

            Instant end = Instant.now();

            DurationService durationService = new DurationService();
            String message = durationService.durationMessage(start, end);
            System.out.println();
            System.out.println(message);
        }
        catch(UnrecognizedOptionException uoe)
        {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar openscad-test-suite.jar [path]", options);
        }        
    }
    
    private SubversionMacrosRunProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        SubversionMacrosRunProfile runProfile = new SubversionMacrosRunProfile();
        
        // find the mode based on the given command line options
        runProfile.mode = cmd.hasOption(REVERT_MODIFIED) ? SubversionMacroModes.REVERT_MODIFIED : 
                          cmd.hasOption(APPEND_CREATE_DATE) ? SubversionMacroModes.APPEND_CREATION_DATE : 
                          SubversionMacroModes.UNKNOWN;
        
        List<String> remainingArgs = cmd.getArgList();
        
        System.out.println("Remaining args:");
        remainingArgs.forEach(a -> System.out.println(a));

        if( remainingArgs.isEmpty() )
        {
            // by default, use the current directory as the path if no 
            // argument is given
            runProfile.inputDirectory = ".";
        }
        else
        {
            // use the first argument as the path to the .scad files
            runProfile.inputDirectory = remainingArgs.get(0);
        }
        
        return runProfile;
    }
    
    private void service(SubversionMacrosRunProfile runProfile) throws Exception
    {
        switch (runProfile.mode)            
        {
            case APPEND_CREATION_DATE:
            {
                subversionService.appendCreationDate();
                
                break;
            }
            case REVERT_MODIFIED:
            {
                subversionService.revertModified();
                
                break;
            }
            default:
            {
                System.out.println();
                System.out.println("No mode was specified.");
                System.out.println();
            }
        }
    }
}
