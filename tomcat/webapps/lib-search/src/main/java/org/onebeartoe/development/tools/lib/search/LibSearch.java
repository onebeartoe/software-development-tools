
package org.onebeartoe.development.tools.lib.search;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class LibSearch 
{
    public static void main(String[] args) 
    {
        System.out.println("Hello WEB-INF/lib/ World!");
        
        LibSearch app = new LibSearch();
        
        String webappPath = args[0];
        
        String searchTarget = args[1];
        
        Map<String, List<String>> searchResults = app.search(webappPath, searchTarget);
        
        app.print(searchResults);
    }

    public Map<String, List<String>> search(String webappsPath, String searchTarget) 
    {
        File webappsDir = new File(webappsPath);
        
        if(webappsPath == null || !webappsDir.isDirectory() )
        {
            throw new IllegalArgumentException("the webapps path does not represent a valid path");
        }
        
        File [] files = webappsDir.listFiles();
        
        if(files == null || files.length == 0)
        {
            throw new IllegalArgumentException("the webapps path is empty");
        }
        
        List<File> webApps = new ArrayList();
        
        for(File file : files)
        {
            if(file.isDirectory())
            {
                webApps.add(file);
            }
        }
        
        Map<String, List<String>> searchResults = new HashMap();
        
        for(File webapp : webApps)
        {
            File libDir = new File(webapp, "WEB-INF/lib");
            
            File [] matchedFiles = libDir.listFiles( (file) -> file.isFile() &&
                                                                file.getName()
                                                                    .toLowerCase()
                                                                    .contains(searchTarget)            
                                                    );
            
            if(matchedFiles != null && matchedFiles.length > 0)
            {
                List<String> matches = new ArrayList();
                
                for(File match : matchedFiles)
                {
                    matches.add(match.getName());
                }
                
                String key = webapp.getName();
                
                searchResults.put(key, matches);
            }            
        }
        
        return searchResults;
    }
    
    private void print(Map<String, List<String>> searchResults)
    {
        Set<String> keySet = searchResults.keySet();
        
        for(String key : keySet)
        {
            List<String> results = searchResults.get(key);
            
            System.out.println(key);
            
            for(String result : results)
            {
                String entry = "\t" + result;
                
                System.out.println(entry);
            }
            
            System.out.println();
        }
    }
}
