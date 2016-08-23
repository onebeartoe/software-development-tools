
package org.onebeartoe.development.tools.html.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.TimerTask;

import org.onebeartoe.filesystem.FileHelper;
import org.onebeartoe.html.ImageTag;

/**
 * author: Roberto H. Marquez
 */
public class ImageTask extends TimerTask 
{

    public void run() 
    {
        try
        {
            File source_dir = HtmlUtility.getSourceDir();
            String file_name = source_dir.getPath() + File.separator + "image-tags.html";
            File outfile = new File(file_name);
            OutputStream outstream = new FileOutputStream(outfile);
            PrintWriter writer = new PrintWriter(outstream);

            System.out.println("outputning to: " + outfile.getAbsolutePath() );
            
            File[] contents = source_dir.listFiles();
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
                    System.out.println("generating HTML for: " + contents[x].getName() );
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
