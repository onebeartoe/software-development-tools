
package org.onebeartoe.file.types;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.RunProfile;

/**
 * @author Roberto Marquez
 */
public class FileTypesService extends AppletService
{
    private void printTally(Map<String, Integer> tallies, String label)
    {
        Set<String> keySet = tallies.keySet();
        List<String> list = new ArrayList(keySet);
        
        Collections.sort(list);
        
        list
                          .forEach( k ->
        {
            Integer count = tallies.get(k);
            System.out.println(k + " -> " + count);
        });        
    }    
    
    @Override
    public void serviceRequest(RunProfile runProfile) throws Exception
    {
        FileTypesRunProfile ftrp = (FileTypesRunProfile) runProfile;
        
        String inpath = ftrp.getInpath();
        
        File pwd = new File(inpath);
        
        Path start = pwd.toPath();
        
        FileTypesVisitor visitor = new FileTypesVisitor();
        
        Files.walkFileTree(start, visitor);
        
        Map<String, Integer> typesWithExtention = visitor.getFiletypes();

        printTally(typesWithExtention, "extensions:");
                        
        System.out.println();
        System.out.println("no extenstion:");        
        Map<String, Integer> noExtentions = visitor.getNoExtensions();
        noExtentions.keySet()
                
                          .forEach( k ->
        {
            Integer count = noExtentions.get(k);
            System.out.println(k + " -> " + count);
        });
        
        System.out.println();
        System.out.println("others:");
        System.out.println();
        Map<String, Integer> others = visitor.getOthers();
        others.keySet()
                          .forEach( k ->
        {
            Integer count = others.get(k);
            System.out.println(k + " -> " + count);
        });        
    }
}
