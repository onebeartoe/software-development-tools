
package org.onebeartoe.file.types;

import java.io.IOException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.network.mail.AppletService;
import org.onebeartoe.network.mail.CommandLineInterfaceApplet;
import org.onebeartoe.network.mail.RunProfile;

/**
 * @author Roberto Marquez
 */
public class FileTypes extends CommandLineInterfaceApplet
{
    @Override
    public Options buildOptions()
    {
        return new Options();
    }
    
    @Override
    protected AppletService getService()
    {
        return new FileTypesService();
    }
    
    public static void main(String [] args) throws IOException, ParseException, Exception
    {        
        CommandLineInterfaceApplet app = new FileTypes();
        app.execute(args);
    }
    
    @Override
    protected RunProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        String inpath;
        
        if(args.length == 0)
        {
            // use the current directory if not path is given
            inpath = ".";
        }
        else
        {
            inpath = args[0];
        }
        
        FileTypesRunProfile runProfile = new FileTypesRunProfile();
        runProfile.setInpath(inpath);
                
        return runProfile;
    }
}
