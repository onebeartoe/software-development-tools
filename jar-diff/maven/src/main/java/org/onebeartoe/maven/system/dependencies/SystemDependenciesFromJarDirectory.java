
package org.onebeartoe.maven.system.dependencies;

import java.io.IOException;
import org.onebeartoe.network.mail.AppletService;
import org.onebeartoe.network.mail.CommandLineInterfaceApplet;

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
        String basePath = "C:\\home\\owner\\workspace\\build-conversions\\some-project";
        
        String subPath = "WEB-INF\\lib";
                
        CommandLineInterfaceApplet app = new SystemDependenciesFromJarDirectory();
        app.execute(args);
    }
}
