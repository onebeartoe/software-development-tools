
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
    
    public ImageTask(File sourceDirectory, ScrollableTextArea statusPanel)
    {
        this.sourceDirectory = sourceDirectory;
        
        this.statusPanel = statusPanel;
        
        logger = Logger.getLogger( getClass().getName() );
    }
    
    public void run() 
    {
        try
        {
            StringBuilder html = new StringBuilder();
            {
                File[] contents = sourceDirectory.listFiles();
                for (int x = 0; x < contents.length; x++)
                {
                    if (FileHelper.isImageFile(contents[x].getName())) 
                    {
                        String image = contents[x].getName();

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
            }

            logger.info(" done.");
        }
        catch(Exception ioe) 
        { 
            ioe.printStackTrace(); 
        }
    }    
}
