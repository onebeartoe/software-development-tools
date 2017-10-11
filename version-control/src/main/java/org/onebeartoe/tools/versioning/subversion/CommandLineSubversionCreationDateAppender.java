
package org.onebeartoe.tools.versioning.subversion;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * To create a creation-date.text file for a WEB/jsp/ directory use this find command:
 * 
 *     find . -type f -not -name properties.jsp -not -name properties.jspf -not -name bottom.jsp -not -name "*.section.properties.jspf" -not -name "*section-properties.jspf" -not -name "*.section-properties.jsp" -not -name "*page.properties.jspf" -not -name "*section-properties.jsp" -not -name "*.properties.jsp" -not -name "*-properties.jsp" -not -name "*page-properties.jspf"  -not -name "*section-propeerties.jspf"  -not -name "*.section.propeerties.jspf" -not -name "*top.jspf" > creation-date-targets.text
 * 
 * For the regular non index.jsp files (search.jsp, about.jsp, ect...) that need a timestamp appended 
 * use this find command:
 * 
 *     !!!!!   STILL NEED THE COMMAND  !!!!!!   
 * 
 * @author Roberto Marquez
 */
public class CommandLineSubversionCreationDateAppender
{
    public static void main(String [] args) throws Exception
//    public static void main(String [] args) throws IOException, Exception
    {
        System.out.println("Welcome to the command line version of the onebeartoe" + 
                           " Subversion creation date application.");
        
        System.out.println("The following files are set to have the creation date appended:");
    
        SubversionCreationDateAppender appender = new SubversionCreationDateAppender();
        
        String inputDir = ".";
        
        File f = new File(inputDir);
        System.out.println("input directory: " + f.getAbsolutePath() );

        List<File> targetFiles = appender.findTargetFiles(inputDir);
        
        appender.appendCreationDate(targetFiles);
    } 
}
