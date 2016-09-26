
package org.onebeartoe.development.tools.html.utility.tasks;

import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import org.onebeartoe.web.utilities.jsp.JspSeederService;
import org.onebeartoe.web.utilities.jsp.StreamedJspSeederService;

/**
 *
 * 
 * @author Roberto Marquez <https://www.youtube.com/user/onebeartoe>
 * 
 */
public class JspSeederTask extends TimerTask
{
    private final JspSeederService seederService;
    
    private final File webRoot;
    
    private final String childDirectoryPath;

    public JspSeederTask(File webRoot, String childDirectoryPath)
    {
        seederService = new StreamedJspSeederService();
        
        this.webRoot = webRoot;
        this.childDirectoryPath = childDirectoryPath;
    }
    
    @Override
    public void run() 
    {
        try 
        {
            seederService.seedIndex(webRoot, childDirectoryPath);
        } 
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
    }
}
