
package org.onebeartoe.development.tools.html.utility.tasks;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.TimerTask;
import org.onebeartoe.application.ui.swing.ScrollableTextArea;

import org.onebeartoe.filesystem.FileHelper;
import org.onebeartoe.html.AnchorTag;

/**
 * 
 * author: Roberto Marquez
 * 
*/
public class IndexTask extends TimerTask
{
    private File sourceDirectory;
    
    private final ScrollableTextArea statusPanel;
    
    public IndexTask(File sourceDirectory, ScrollableTextArea statusPanel)
    {
        this.sourceDirectory = sourceDirectory;
        
        this.statusPanel = statusPanel;
    }
    
    public void run() 
    {
        try 
        {
            File source_dir = sourceDirectory;
            boolean has_index = FileHelper.hasIndexFile(source_dir);
            String file_name = source_dir.getPath() + File.separator + (has_index ? "another-index-listing.html" : "index-listing.html");

            try( PrintWriter index_file = new PrintWriter( new FileWriter(file_name) ) )
            {
                index_file.println("<br>");
                File [] contents = source_dir.listFiles();
                for(int x=0; x<contents.length; x++) 
                {
                    String link_item = contents[x].getName();
                    link_item += contents[x].isDirectory() ? "/index.html" : "";

                    AnchorTag tag = new AnchorTag(link_item, link_item);
                    String link = tag.toString();
                    index_file.println(link + "<br>" );

                    String statusMessage = "generating HTML for: " + contents[x].getName();
                    System.out.println(statusMessage);
                    statusPanel.appendText(statusMessage + "\n");                                
                }
                index_file.close();

                statusPanel.setText("\n");
            }
        }
        catch( java.io.IOException ioe ) 
        {
            ioe.printStackTrace(); 
        }
    }
}
