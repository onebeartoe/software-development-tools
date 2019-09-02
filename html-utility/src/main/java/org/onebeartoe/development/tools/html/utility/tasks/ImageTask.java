
package org.onebeartoe.development.tools.html.utility.tasks;

import java.io.File;
import java.util.TimerTask;
import java.util.logging.Logger;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;

/**
 * This class runs asynchronously to perform the image tag generation.
 * 
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
            ImageTaskService imageService = new ImageTaskService();
            
            String html = imageService.index(sourceDirectory, includeJeePath);

            statusPanel.setText(html + "\n");

            logger.info(" done.");
        }
        catch(Exception ioe) 
        { 
            ioe.printStackTrace(); 
        }
    }
}
