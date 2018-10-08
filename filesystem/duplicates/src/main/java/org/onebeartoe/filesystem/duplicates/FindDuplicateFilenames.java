
package org.onebeartoe.filesystem.duplicates;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class finds duplication filenames, with a specified extention, that are 
 * listed in a text file.
 */
public class FindDuplicateFilenames 
{
    public static void main(String [] args) throws IOException
    {
        // the inpath is the location of the text file with a list of all the 
        // files names to process.        
        String inpath = args[0];
        
        File f = new File(inpath);
        Path path = f.toPath();
        List<String> allLines = Files.readAllLines(path);
        
        List<String> javaPaths = allLines.stream()
//                                         .filter(p -> p.endsWith(".java") )
                                         .filter(p -> !p.endsWith("/")) // remove directory entries
                                         .collect( Collectors.toList() );
        
        Map<String, List<String> > nameToPaths = new HashMap();
        
        javaPaths.forEach(jp -> 
        {
            System.out.println("jp: " + jp);
            int begin = jp.lastIndexOf("/") + 1;
            int end = jp.length() > 5 ? jp.length() - 5 : jp.length();
            
            String className = jp.substring(begin, end);
            
            List<String> paths = nameToPaths.get(className);
            if(paths == null)
            {
                paths = new ArrayList();
                
                nameToPaths.put(className, paths);
            }
            paths.add(jp);
        });
        
        // print the name to paths mapping
        nameToPaths.keySet()
                   .forEach(key -> 
                   {
                        List<String> paths = nameToPaths.get(key);
                        
                        if(paths.size() > 1)
                        {
                            System.out.println("\n" + key + ":");

                            paths.forEach(p -> 
                            {
                                String line = "\t\t" + p;
                                System.out.println(line);
                            });
                        }
                   });
    }
}
