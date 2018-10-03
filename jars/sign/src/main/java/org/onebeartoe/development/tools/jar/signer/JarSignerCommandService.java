/*
 */
package org.onebeartoe.development.tools.jar.signer;

import java.io.File;
import org.onebeartoe.network.mail.AppletService;
import org.onebeartoe.network.mail.RunProfile;

/**
 *
 * @author Roberto Marquez
 */
public class JarSignerCommandService extends AppletService
{
    public void serviceRequest(RunProfile runProfile) throws Exception
    {
       System.out.println("jarsigner output") ;
       
       JarSignerRunProfile rp = (JarSignerRunProfile) runProfile;
       
       String inpath = rp.getJarsPath();
       
       File indir = new File(inpath);
        
       File[] jarFiles = indir.listFiles((File dir, String name) -> name.endsWith(".jar"));
       
       for(File f : jarFiles)
       {
           System.out.println("path: " + f.getAbsolutePath() );
       }
    }
}
