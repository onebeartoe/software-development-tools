package org.onebeartoe.filesystem.watcher;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.application.CommandLineInterfaceApplet;

public class FileWatcherApplication extends CommandLineInterfaceApplet
{
    final String COMMAND_1 = "command1";

    final String PATTERN_1 = "pattern1";

    final String QUIET_PERIOD_1 = "quietPeriod1";
    
    final String COMMAND_2 = "command2";

    final String PATTERN_2 = "pattern2";

    final String QUIET_PERIOD_2 = "quietPeriod2";
    
    final String COMMAND_3 = "command3";

    final String PATTERN_3 = "pattern3";

    final String QUIET_PERIOD_3 = "quietPeriod3";
    
    final String LOG_FILE = "logFile";
    
    final String CONFIG_FILE = "configFile";
    
    final FilesystemWatcherService service = new FilesystemWatcherService();
    
    @Override
    public Options buildOptions()
    {
        Option configFile = Option.builder()
                                .hasArg()
                                .longOpt(CONFIG_FILE)
                                .build();
        
        Option command1 = Option.builder()
                                .hasArg()
                                .longOpt(COMMAND_1)
                                .build();
        
        Option pattern1 = Option.builder()
                                .hasArg()
                                .longOpt(PATTERN_1)
                                .build();

        Option quietPeriod1 = Option.builder()
                                .hasArg()
                                .longOpt(QUIET_PERIOD_1)
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
                                
        Option pattern3 = Option.builder()
                                .hasArg()
                                .longOpt(PATTERN_3)
                                .build();

        Option command3 = Option.builder()
                                .hasArg()
                                .longOpt(COMMAND_3)
                                .build();
        
        Option quietPeriod3 = Option.builder()
                                .hasArg()
                                .longOpt(QUIET_PERIOD_3)
                                .build();
        
        Option logFile = Option.builder()
                                .hasArg()
                                .longOpt(LOG_FILE)
                                .build();
                
        Options options = new Options();
        
        options.addOption(command1);
        
        options.addOption(pattern1);
        
        options.addOption(quietPeriod1);
        
        options.addOption(command2);
        
        options.addOption(pattern2);
        
        options.addOption(quietPeriod2);
        
        options.addOption(command3);
        
        options.addOption(pattern3);
        
        options.addOption(quietPeriod3);
        
        options.addOption(logFile);
        
        options.addOption(configFile);
        
        return options;
    }

    @Override
    public FilesystemWatcherService getService()
    {
        return service;
    }
    
    public static void main(String[] args) throws Exception
    {
        CommandLineInterfaceApplet app = new FileWatcherApplication();

        app.execute(args);
    }

    private Duration parseDuration(String s) throws ParseException
    {
        String [] tokens = s.split("-");
        
        String amountStr = tokens[0];
        
        int amount = Integer.valueOf(amountStr);
        
        String unit = tokens[1];
        
        Duration duration;
        
        switch (unit)        
        {
            case "minute":
            {
                duration = Duration.ofMinutes(amount);
                
                break;
            }
            default:
            {
                throw new ParseException("unknown duration type: " + unit);
            }
        }
        
        return duration;
    }    
    
    /**
     * This method currently parses up to 3 pattern/command parameters.
     * 
     * @param args
     * @param options
     * @return
     * @throws ParseException 
     */
    @Override
    protected FileWatcherProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        
        CommandLine cli = parser.parse(options, args);

        FileWatcherProfile profile = new FileWatcherProfile();
        
        if( cli.hasOption(CONFIG_FILE) )
        {
            if( cli.getOptions().length > 1 )
            {
                String message = "other options are not accepted with config file option";
                
                throw new InvalidFileWatcherParamsException(message);
            }
            
            String configPath = cli.getOptionValue(CONFIG_FILE);
            
            File infile = new File(configPath);
            
            try
            {
                String [] configArgs = service.configFileToArgs(infile);
                
                // reset the command line interface with the contents of the properties file
                cli = parser.parse(options, configArgs);
            } 
            catch (IOException ex)
            {                
                logger.severe( ex.getMessage() );
            }
        }
                
        String command1 = cli.getOptionValue("command1");
        
        profile.command1 = command1;
        
        String pattern1 = cli.getOptionValue("pattern1");
        
        profile.pattern1 = pattern1;
        
        if(profile.pattern1 == null)
        {
            String message = "pattern1 is missing command1 parameter";
            
            throw new ParseException(message);
        }
        
        if( cli.hasOption(QUIET_PERIOD_1) )
        {
            String s = cli.getOptionValue(QUIET_PERIOD_1);
            
            profile.quietPeriod1 = parseDuration(s);
        }

        if( cli.hasOption(PATTERN_2) )
        {
            profile.pattern2 = cli.getOptionValue(PATTERN_2);
            
            profile.command2 = cli.getOptionValue(COMMAND_2);
            
            if(profile.command2 == null)
            {
                String message = "command2 is missing pattern2";

                throw new ParseException(message);                
            }
        }
        
        if( cli.hasOption(QUIET_PERIOD_2) )
        {
            String s = cli.getOptionValue(QUIET_PERIOD_2);
                        
            profile.quietPeriod2 = parseDuration(s);
        }
        
        if(cli.hasOption(PATTERN_3))
        {
            profile.pattern3 = cli.getOptionValue(PATTERN_3);
            
            profile.command3 = cli.getOptionValue(COMMAND_3);
            
            if(profile.command3 == null)
            {
                String message = "pattern3 is missing command3 parameter";

                throw new ParseException(message);                
            }            
        }
              
        final String LOG_FILE = "logFile";
        
        if( cli.hasOption(LOG_FILE) )
        {
            profile.logFile = cli.getOptionValue(LOG_FILE);
        }
        
        return profile;
    }
}
