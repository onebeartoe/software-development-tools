
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
import org.onebeartoe.develoment.tools.jar.diff.JarDiffette.JarDiffCliRunProfile;

import org.onebeartoe.network.mail.AppletService;
import org.onebeartoe.network.mail.RunProfile;

/**
 * @author Roberto Marquez
 */
public class JarDiffService extends AppletService
{    
    public JarDiffReport diff(String infile1, String infile2) throws IOException
    {        
        List<String> uniqueToPojo1 = new ArrayList();
        List<String> uniqueToPojo2 = new ArrayList();
        List<String> commonToBoth = new ArrayList();
        
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
    
    @Override
    public void serviceRequest(RunProfile runProfile) throws Exception
    {
        //TODO: find a way to do this without having to cast!
        JarDiffCliRunProfile jarDiffRunProfile = (JarDiffCliRunProfile) runProfile;
                
        try
        {
            String jar1 = jarDiffRunProfile.jarPath1;
            String jar2 = jarDiffRunProfile.jarPath2;
            
            JarDiffService diff = new JarDiffService();
            JarDiffReport report = diff.diff(jar1, jar2);
            
            System.out.println("unique to " + jar1);
            report.uniqueToJar1.stream()
                    .forEach( System.out::println );

            System.out.println();
            System.out.println("unique to " + jar2);
            report.uniqueToJar2.stream()
                               .forEach( System.out::println );

            System.out.println();            
            System.out.println("common to both:");
            report.commonToBoth
                  .stream()
                  .forEach(System.out::println);
        }
        catch(IndexOutOfBoundsException ioobe)
        {
            String message = "At leat two arguments are required: jarPath1 jarPath2";
            System.err.println(message);
            ioobe.printStackTrace();
        }
    }
}
