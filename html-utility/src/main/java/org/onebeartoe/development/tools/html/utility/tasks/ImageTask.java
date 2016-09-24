
package org.onebeartoe.development.tools.html.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.TimerTask;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;

import org.onebeartoe.filesystem.FileHelper;
import org.onebeartoe.html.ImageTag;

/**
 * author: Roberto H. Marquez
 */
public class ImageTask extends TimerTask 
{
    private File sourceDirectory;
    
    private final ScrollableTextArea statusPanel;
    
    public ImageTask(File sourceDirectory, ScrollableTextArea statusPanel)
    {
        this.sourceDirectory = sourceDirectory;
        
        this.statusPanel = statusPanel;
    }
    
    public void run() 
    {
        try
        {
            String file_name = sourceDirectory.getPath() + File.separator + "image-tags.html";
            File outfile = new File(file_name);
            OutputStream outstream = new FileOutputStream(outfile);
            PrintWriter writer = new PrintWriter(outstream);
            
            String statusMessage = "\n\n" + "outputning to: " + outfile.getAbsolutePath();
            System.out.println(statusMessage);
            statusPanel.appendText(statusMessage + "\n");
            
            File[] contents = sourceDirectory.listFiles();
            for (int x = 0; x < contents.length; x++)
            {
                if (FileHelper.isImageFile(contents[x].getName())) 
                {
                    String image = contents[x].getName();
                    
                    String altText = image;
                    ImageTag imageTag = new ImageTag(image, 600, 400, altText);
                    String tag = imageTag.toString();
                            
                    writer.println(tag);
                    writer.println("<br>\n</br>");
                    
                    statusMessage = "generating HTML for: " + contents[x].getName();
                    System.out.println(statusMessage);
                    statusPanel.appendText(statusMessage + "\n");
                }
            }
            
            writer.flush();
            writer.close();
            
            System.out.println(" done.");
        }
        catch(Exception ioe) 
        { 
                ioe.printStackTrace(); 
        }
    }
    
}
