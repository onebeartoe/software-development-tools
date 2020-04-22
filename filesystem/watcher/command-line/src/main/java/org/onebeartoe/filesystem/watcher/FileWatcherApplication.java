package org.onebeartoe.filesystem.watcher;

import java.io.IOException;
import java.text.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;

import org.apache.commons.cli.Options;
import org.onebeartoe.application.CommandLineInterfaceApplet;
import org.onebeartoe.application.RunProfile;
import org.onebeartoe.application.logging.SysoutLoggerFactory;

public class FileWatcherApplication extends CommandLineInterfaceApplet
{

    @Override
    public Options buildOptions()
    {
        final String COMMAND_1 = "command1";
        
        final String PATTERN_1 = "pattern1";
        
        final String COMMAND_2 = "command2";
        
        final String PATTERN_2 = "pattern2";
        
        final String QUIET_PERIOD_2 = "quietPeriod2";
        
        final String LOG_FILE = "logFile";
        
        Option command1 = Option.builder()
                                .hasArg()
                                .longOpt(COMMAND_1)
                                .required()
                                .build();
        
        Option pattern1 = Option.builder()
                                .hasArg()
                                .longOpt(PATTERN_1)
                                .required()
                                .build();
                                
        Option command2 = Option.builder()
                                .hasArg()
                                .longOpt(COMMAND_2)
                                .build();
                                
        Option pattern2 = Option.builder()
                                .hasArg()
                                .longOpt(PATTERN_2)
                                .build();
        
        Option quietPeriod2 = Option.builder()
                                .hasArg()
                                .longOpt(QUIET_PERIOD_2)
                                .build();
        
        Option logFile = Option.builder()
                                .hasArg()
                                .longOpt(LOG_FILE)
                                .build();
                
        Options options = new Options();
        
        options.addOption(command1);
        
        options.addOption(pattern1);
        
        options.addOption(command2);
        
        options.addOption(pattern2);
        
        options.addOption(quietPeriod2);
        
        options.addOption(logFile);
        
        return options;
    }

    public static void main(String[] args) throws IOException, Exception
    {
        CommandLineInterfaceApplet app = new FileWatcherApplication();

        app.execute(args);
    }

    @Override
    protected FileWatcherProfile parseRunProfile(final String[] args, Options options) throws InvalidFileWatcherParamsException, org.apache.commons.cli.ParseException
    {
        CommandLineParser parser = new DefaultParser();
        
        CommandLine cl = parser.parse(options, args);

        String command1 = cl.getOptionValue("--command1");
        
        String pattern1 = cl.getOptionValue("--pattern1");
        
        FileWatcherProfile profile = new FileWatcherProfile();
        
        final String LOG_FILE = "logFile";
        
        if( cl.hasOption(LOG_FILE) )
        {
            profile.logFile = cl.getOptionValue(LOG_FILE);
        }
        
        return profile;
    }
}
