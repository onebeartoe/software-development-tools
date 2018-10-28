
package org.onebeartoe.file.types;

import java.io.IOException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.CommandLineInterfaceApplet;
import org.onebeartoe.application.RunProfile;

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
