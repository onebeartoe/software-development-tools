
package org.onebeartoe.development.tools.html.utility.tasks;

import java.io.File;
import java.util.logging.Logger;
import org.onebeartoe.filesystem.FileHelper;
import org.onebeartoe.html.ImageTag;

/**
 *
 * @author Roberto Marquez
 */
public class ImageTaskService
{
    private Logger logger = Logger.getLogger( ImageTaskService.class.getName() );
    
    public String index(File sourceDirectory, boolean includeJeePath)
    {
        StringBuilder html = new StringBuilder();

        File[] contents = sourceDirectory.listFiles();

        for (int x = 0; x < contents.length; x++)
        {
            if (FileHelper.isImageFile(contents[x].getName())) 
            {
                String image = contents[x].getName();

                if(includeJeePath)
                {
                    String jeePath = includePath(sourceDirectory);

                    image = jeePath + "/" + image;
                }

                String altText = image;
                ImageTag imageTag = new ImageTag(image, 600, 400, altText);
                String tag = imageTag.toString();

                html.append("\n");
                html.append(tag);
                html.append("\n<br>\n</br>\n");

                String statusMessage = "generating HTML for: " + contents[x].getName();

                logger.info(statusMessage);
            }
        }

        return html.toString();
    }
    
    private String includePath(File sourceDirectory)
    {
        String includePath = "jee/path/not/found";
        
        String fullPath = sourceDirectory.getAbsolutePath();
        
        int webappIndex = fullPath.indexOf("/webapp/");
        
        if(webappIndex != -1)
        {
            int beginIndex = webappIndex + 8;
            
            String contextPath = "<%= request.getContextPath() %>/";
            
            includePath = contextPath + fullPath.substring(beginIndex);
        }
        
        return includePath;
    }    
}
