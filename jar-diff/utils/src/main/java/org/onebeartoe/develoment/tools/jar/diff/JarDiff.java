
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
import java.util.zip.ZipFile;

/**
 * @author Roberto Marquez
 */
public class JarDiff 
{
//    public JarDiff(String infile1, String infile2)
//    {
//        this.
//    }
    
    public JarDiffReport diff(String infile1, String infile2) throws IOException
    {        
        List<String> uniqueToPojo1 = new ArrayList<String>();
        List<String> uniqueToPojo2 = new ArrayList<String>();
        List<String> commonToBoth = new ArrayList<String>();
        
        List<String> lines1 = loadLines(infile1);
        List<String> lines2 = loadLines(infile2);
        
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

        JarDiffReport report = new JarDiffReport(infile1, infile2);
        report.commonToBoth = commonToBoth;
        report.uniqueToJar1 = uniqueToPojo1;
        report.uniqueToJar2 = uniqueToPojo2;        
        
        System.out.println();
        
        return report;
    }
    
    private List<String> loadLines(String inPath) throws IOException
    {
        File infile = new File(inPath);
        ZipFile zip = new ZipFile(infile);
        
        List<String> entries = zip.stream()
                .map(e -> e.getName())
                .collect( Collectors.toList() );

        return entries;
    }
    
    /**
     * @Relocate
     * This is a good example of loading lines of text from a file with Java 8 try with resources.
     * 
     * @param inPath
     * @return
     * @throws IOException 
     */
    private List<String> loadLinesRelocate(String inPath) throws IOException
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