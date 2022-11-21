
package org.onebeartoe.maven.system.dependencies;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.RunProfile;

/**
 *
 * @author Roberto Marquezcd
 * 
 */
public class SystemDependenciesService extends AppletService
{
    public List<String> generateDependencyTags(String basePath, String subPath) throws IOException
    {
        String inpath = basePath + File.separator + subPath;
        
        File indir = new File(inpath);
        
        File[] jarFiles = indir.listFiles((File dir, String name) -> name.endsWith(".jar"));

        List<String> tags = new ArrayList();
        
        if(jarFiles == null)
        {
            System.err.println("No JAR files were found in path: " + inpath);
        }
        else
        {
            String propertiesPath = "/dependency-tag.properties";
            Properties props = new Properties();
            InputStream instream = getClass().getResourceAsStream(propertiesPath);
            props.load(instream);
            String blankTag = props.getProperty("dependency.tag");
        
            for(File j : jarFiles)
            {
                String artifact = j.getName();

                int end = artifact.length() - 4;
                artifact = artifact.substring(0, end);

                String name = j.getName();

                String systemPath = subPath + '/' + name;
                
                String populatedTag = String.format(blankTag, artifact, systemPath);

                populatedTag = populatedTag.replaceAll("> <", ">\n<");

                tags.add(populatedTag);
            }
        }
        
        return tags;
    }
    
    @Override
    public void serviceRequest(RunProfile runProfile) throws Exception
    {
//TODO: Find a way to do this without casting objects.        
        SystemDependenciesRunProfile dependenciesRunProfile = (SystemDependenciesRunProfile) runProfile;
        
        String projectRoot = dependenciesRunProfile.getProjectRoot();
        String jarSubpath = dependenciesRunProfile.getJarSubpath();
        
        List<String> tags = generateDependencyTags(projectRoot, jarSubpath);
        
        tags.forEach( t -> 
        {
            System.out.println(t);
            System.out.println();
        });
    }
}
