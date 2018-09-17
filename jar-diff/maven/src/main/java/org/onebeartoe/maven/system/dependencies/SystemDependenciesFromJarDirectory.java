
package org.onebeartoe.maven.system.dependencies;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * This class generates system-scoped Maven dependency tags for every JAR file found in the 
 * specified directory.
 * 
 */
public class SystemDependenciesFromJarDirectory 
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

                String populatedTag = String.format(blankTag, artifact, name);

                populatedTag = populatedTag.replaceAll("> <", ">\n<");

                tags.add(populatedTag);
            }
        }
        
        return tags;
    }
    
    public static void main(String [] args) throws IOException
    {
        String basePath = "C:\\home\\owner\\workspace\\build-conversions\\some-project";
        
        String subPath = "WEB-INF\\lib";
        
        SystemDependenciesFromJarDirectory app = new SystemDependenciesFromJarDirectory();
        
        List<String> tags = app.generateDependencyTags(basePath, subPath);
        
        tags.forEach( t -> 
        {
            System.out.println(t);
            System.out.println();
        });
    }
}
