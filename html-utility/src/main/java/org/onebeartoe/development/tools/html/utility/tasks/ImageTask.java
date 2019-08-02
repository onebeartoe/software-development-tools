
package org.onebeartoe.development.tools.html.utility.tasks;

import java.io.File;
import java.util.TimerTask;
import java.util.logging.Logger;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;

import org.onebeartoe.filesystem.FileHelper;
import org.onebeartoe.html.ImageTag;

/**
 * @author Roberto Marquez
 */
public class ImageTask extends TimerTask 
{
    private File sourceDirectory;
    
    private final ScrollableTextArea statusPanel;
    
    private Logger logger;
    
    private boolean includeJeePath;
    
    public ImageTask(File sourceDirectory, ScrollableTextArea statusPanel)
    {
//        boolean includePath = false;
        
        this(sourceDirectory, statusPanel, false);
    }
    
    public ImageTask(File sourceDirectory, ScrollableTextArea statusPanel, boolean includeJeePath)
    {
        this.includeJeePath = includeJeePath;
        
        this.sourceDirectory = sourceDirectory;
        
        this.statusPanel = statusPanel;
        
        logger = Logger.getLogger( getClass().getName() );
    }
    
    public void run() 
    {
        try
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
                        String jeePath = includePath();
                        
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

            statusPanel.setText(html.toString() + "\n");
            

            logger.info(" done.");
        }
        catch(Exception ioe) 
        { 
            ioe.printStackTrace(); 
        }
    }
    
    private String includePath()
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
