/*
 */
package org.onebeartoe.development.tools.jar.signer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.onebeartoe.network.mail.AppletService;
import org.onebeartoe.network.mail.RunProfile;

/**
 *
 * @author Roberto Marquez
 */
public class JarSignerCommandService extends AppletService
{
    private String commandFor(String jarName, JarSignerRunProfile rp) throws IOException
    {
        String propertiesPath = "/jarsigner.properties";
        Properties props = new Properties();
        InputStream instream = getClass().getResourceAsStream(propertiesPath);
        props.load(instream);
        String blankCommand = props.getProperty("command");        
        
        String populatedCommand = String.format(blankCommand,
                                                    rp.getKeystore(),
                                                    rp.getStorepass(),
                                                    rp.getKeypass(),
                                                    jarName,
                                                    rp.getAlias());
                
        return populatedCommand;
    }
    
    public void serviceRequest(RunProfile runProfile) throws Exception
    {       
       JarSignerRunProfile rp = (JarSignerRunProfile) runProfile;
       
       String inpath = rp.getJarsPath();
       
       File indir = new File(inpath);
        
       File[] jarFiles = indir.listFiles((File dir, String name) -> name.endsWith(".jar"));
       
       for(File f : jarFiles)
       {
           String jarsignerCommand = commandFor(f.getName(), rp);
           
           System.out.println(jarsignerCommand);
           System.out.println();
       }
    }
}
