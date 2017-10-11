
package org.onebeartoe.tools.versioning.subversion;

import org.onebeartoe.tools.versioning.subversion.log.CreationDateAppender;
import java.io.File;
import java.time.Instant;
import java.util.List;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.onebeartoe.application.duration.DurationService;

/**
 * To create a creation-date.text file for a WEB/jsp/ directory use this find command:
 * 
 *     find . -type f -not -name properties.jsp -not -name properties.jspf -not -name bottom.jsp -not -name "*.section.properties.jspf" -not -name "*section-properties.jspf" -not -name "*.section-properties.jsp" -not -name "*page.properties.jspf" -not -name "*section-properties.jsp" -not -name "*.properties.jsp" -not -name "*-properties.jsp" -not -name "*page-properties.jspf"  -not -name "*section-propeerties.jspf"  -not -name "*.section.propeerties.jspf" -not -name "*top.jspf" > creation-date-targets.text
 * 
 * For the regular non index.jsp files (search.jsp, about.jsp, ect...) that need a timestamp appended 
 * use this find command:
 * 
 *     !!!!!   STILL NEED THE COMMAND  !!!!!!   
 * 
 * @author Roberto Marquez
 */
public class CommandLineSubversionMacros
{
    private static final String REVERT_MODIFIED = "revertModified";
    
    private DurationService durationService;
    
    public CommandLineSubversionMacros()
    {
        durationService = new DurationService();
    }
    
    private Options buildOptions()
    {
        Option revertModified = Option.builder()
                    .required(false)
                    .longOpt(REVERT_MODIFIED)
                    .build();
        
        Options options = new Options();
        options.addOption(revertModified);
        
        return options;
    }
    
    public static void main(String [] args) throws Exception
//    public static void main(String [] args) throws IOException, Exception
    {
        System.out.println("Welcome to the command line version of the onebeartoe" + 
                           " Subversion creation date application.");
        
        System.out.println("The following files are set to have the creation date appended:");
    
        CommandLineSubversionMacros app = new CommandLineSubversionMacros();
        
        Options options = app.buildOptions();
        try
        {
//            RunProfile runProfile = parseRunProfile(args, options);

            Instant start = Instant.now();

            CreationDateAppender appender = new CreationDateAppender();

            String inputDir = ".";

            File f = new File(inputDir);
            System.out.println("input directory: " + f.getAbsolutePath() );

            List<File> targetFiles = appender.findTargetFiles(inputDir);

            appender.appendCreationDate(targetFiles);

            Instant end = Instant.now();

            String message = durationService.durationMessage(start, end);
            System.out.println();
            System.out.println(message);
        }
        catch(UnrecognizedOptionException uoe)
        {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar openscad-test-suite.jar [path]", options);
//            Help h = new Help();
//            h.printHelp();
        }        
    } 
}
