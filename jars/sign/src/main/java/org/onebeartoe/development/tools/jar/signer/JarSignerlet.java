/*
 */
package org.onebeartoe.development.tools.jar.signer;

import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.network.mail.AppletService;
import org.onebeartoe.network.mail.CommandLineInterfaceApplet;
import org.onebeartoe.network.mail.RunProfile;

/**
 *
 * @author Roberto Marquez
 */
public class JarSignerlet extends CommandLineInterfaceApplet
{
    @Override
    public Options buildOptions()
    {
        Option outfile = Option.builder()
                        .required(true)
                        .longOpt("keystore")
                        .hasArg(true)
                        .build();
        
        Options options = new Options();
        options.addOption(outfile);

        return options;        
    }
    @Override
    protected AppletService getService()
    {
        return new JarSignerCommandService();
    }
    
    public static void main(String [] args) throws ParseException, IOException, Exception
    {
        CommandLineInterfaceApplet app = new JarSignerlet();
        app.execute(args);
    }

    @Override
    protected RunProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        
        
        
        RunProfile profile = new JarSignerRunProfile();
        
        return profile;
    }
}
