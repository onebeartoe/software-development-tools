
package org.onebeartoe.develoment.tools.jar.diff;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Roberto Marquez
 */
public class JarDiff 
{
    public List<String> uniqueToPojo1;
    public List<String> uniqueToPojo2;
    public List<String> commonToBoth;
    
    List<String> lines1;
    List<String> lines2;
        
    public JarDiff(String infile1, String infile2) throws IOException
    {
        uniqueToPojo1 = new ArrayList<String>();
        uniqueToPojo2 = new ArrayList<String>();
        commonToBoth = new ArrayList<String>();
        
        lines1 = loadLines(infile1);
        lines2 = loadLines(infile2);
    }
    
    public void diff()
    {
        // find properties unique to pojo 1 and common to both
        for(String currentPojo1Property : lines1)
        {
            boolean currentP1AttributeExistsInP2 = lines2.contains(currentPojo1Property);
            if(currentP1AttributeExistsInP2)
            {
                    commonToBoth.add(currentPojo1Property);
            }
            else
            {
                    uniqueToPojo1.add(currentPojo1Property);
            }
        }

        // find properties unique to pojo 2
        for(String currentPojo2Property : lines2)
        {
            boolean currentP2AttributeDoesNotExistInCommonToBoth = !commonToBoth.contains(currentPojo2Property);
            if(currentP2AttributeDoesNotExistInCommonToBoth)
            {				
                    uniqueToPojo2.add(currentPojo2Property);
            }
        }		

        System.out.println();
    }
    
    private List<String> loadLines(String inPath) throws IOException
    {
        List<String> lines;
        
        File localFile = new File(inPath);
        Path path = localFile.toPath();
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8))
        {
            lines = reader.lines()
                          .collect( Collectors.toList() );
        }
        
        return lines;
    }
}