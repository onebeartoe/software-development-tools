
package org.onebeartoe.development.tools.jar.signer;

import java.io.IOException;
import java.util.List;
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
    private final String ALIAS = "alias";
    private final String KEYSTORE = "keystore";
    private final String STOREPASS = "storepass";
    private final String KEYPASS = "keypass";
    
    @Override
    public Options buildOptions()
    {
        Option alias = Option.builder()
                        .required()
                        .longOpt(ALIAS)
                        .hasArg()
                        .build();
                
        Option keystore = Option.builder()
                        .required()
                        .longOpt(KEYSTORE)
                        .hasArg(true)
                        .build();
        
        Option storePass = Option.builder()
                                .required()
                                .longOpt(STOREPASS)
                                .hasArg()
                                .build();
        
        Option keyPass = Option.builder()
                                .required(false)
                                .longOpt(KEYPASS)
                                .hasArg()
                                .build();                                
        
        Options options = new Options();
        options.addOption(alias);
        options.addOption(keystore);
        options.addOption(storePass);
        options.addOption(keyPass);

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
        
        List<String> remainingArgs = cmd.getArgList();
        
        String jarsPath = null;
        
        if( remainingArgs.size() < 1)
        {
            String message = "At least one more paramter is needed as the path to the JAR files.";
            
            throw new ParseException(message);
        }
        else
        {
            jarsPath = remainingArgs.get(0);
        }
        
        String storepass = cmd.getOptionValue(STOREPASS);
        
        String alias = cmd.getOptionValue(ALIAS);
        
        String keypass;
        
        if( cmd.hasOption(KEYPASS) )
        {
            keypass = cmd.getOptionValue(KEYPASS);
        }
        else
        {
            keypass = storepass;
        }
        
        String keystore = cmd.getOptionValue(KEYSTORE);
        
        JarSignerRunProfile profile = new JarSignerRunProfile();        
        profile.setAlias(alias);
        profile.setJarsPath(jarsPath);
        profile.setKeypass(keypass);
        profile.setKeystore(keystore);
        profile.setStorepass(storepass);
        
        return profile;
    }
}
