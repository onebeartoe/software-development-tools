
package org.onebeartoe.maven.system.dependencies;

import java.io.IOException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.CommandLineInterfaceApplet;
import org.onebeartoe.application.RunProfile;

/**
 * This class generates system-scoped Maven dependency tags for every JAR file found in the 
 * specified directory.
 * 
 */
public class SystemDependenciesFromJarDirectory extends CommandLineInterfaceApplet
{
    protected AppletService getService()
    {
        return new SystemDependenciesService();
    }
    
    public static void main(String [] args) throws IOException, Exception
    {                
        CommandLineInterfaceApplet app = new SystemDependenciesFromJarDirectory();
        app.execute(args);
    }
    
    /**
     * @param args This method expects two arguments.
     * 
     * arg[0] -> basePath -> "C:\\home\\owner\\workspace\\build-conversions\\some-project";
     *   
     * arg[1] -> subPath -> "WEB-INF\\lib";
     * 
     * @param options
     * 
     * @return
     * @throws ParseException 
     */
    @Override
    protected RunProfile parseRunProfile(final String[] args, Options options) throws ParseException
    {
        SystemDependenciesRunProfile runProfile = new SystemDependenciesRunProfile();
        
        if(args.length != 2)
        {
            String message = "Two arguements are requried by this applicaton" +
                    "The first is the path to the root of the project.  " + 
                    "The second is the subpath to the directory with the JAR files.";
            
            throw new ParseException(message);
        }
        
        runProfile.setProjectRoot(args[0]);
        
        runProfile.setJarSubpath(args[1]);
        
        return runProfile;
    }
}
