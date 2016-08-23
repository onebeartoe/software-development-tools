
package org.onebeartoe.development.tools.html.utility;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.TimerTask;

import org.onebeartoe.filesystem.FileHelper;
import org.onebeartoe.html.AnchorTag;
import org.onebeartoe.html.LinkTag;

/**
 * 
 * author: Roberto H. Marquez
 * 
*/
public class IndexTask extends TimerTask 
{

	public void run() {
		boolean has_index;

		try {
			File source_dir = HtmlUtility.getSourceDir();
			has_index = FileHelper.hasIndexFile(source_dir);
			String file_name = source_dir.getPath() + File.separator + (has_index ? "another_index.html" : "index.html");
			PrintWriter index_file = new PrintWriter( new FileWriter(file_name) );

			index_file.println("<br>");
			File [] contents = source_dir.listFiles();
			for(int x=0; x<contents.length; x++) 
                        {
				String link_item = contents[x].getName();
				link_item += contents[x].isDirectory() ? "/index.html" : "";
                                
                                AnchorTag tag = new AnchorTag(link_item, link_item);
                                String link = tag.toString();
				index_file.println(link + "<br>" );
			}
			index_file.close();
		}
		catch( java.io.IOException ioe ) { 
			ioe.printStackTrace(); 
		}
	}

}
