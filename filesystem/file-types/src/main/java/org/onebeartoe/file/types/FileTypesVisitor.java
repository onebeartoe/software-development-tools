/*
 */

package org.onebeartoe.file.types;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Roberto Marquez
 */
public class FileTypesVisitor extends SimpleFileVisitor<Path>
{
    Map<String, Integer> typesWithExtention;
        
    Map<String, Integer> noExtentions;

    Map<String, Integer> others;
    
    public FileTypesVisitor()
    {
        typesWithExtention = new HashMap();
        
        noExtentions = new HashMap();
        
        others = new HashMap();
    }
    
    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) //throws IOException
    {
        File file = path.toFile();
        
        String name = file.getName();
        
        int lastDot = name.lastIndexOf(".");
        
        if(lastDot == -1)
        {
            Integer count = noExtentions.get(name);
            if(count == null )
            {
                count = 1;
            }
            else
            {
                count += 1;
            }
            
            noExtentions.put(name, count);
        }
        else
        {
            String extention = name.substring(lastDot);
            
            Integer count = typesWithExtention.get(extention);
            if(count == null)
            {
                count = 1;
            }
            else
            {
                count += 1;
            }
            typesWithExtention.put(extention, count);
        }
        
        return FileVisitResult.CONTINUE;
    }

    Map<String, Integer> getFiletypes() 
    {
        return typesWithExtention;
    }

    Map<String, Integer> getNoExtensions() 
    {
        return noExtentions;
    }

    Map<String, Integer> getOthers() 
    {
        return others;
    }
}
