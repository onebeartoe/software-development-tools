
package org.onebeartoe.filesystem.watcher;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.google.common.io.Files;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import org.onebeartoe.application.AppletService;
import org.onebeartoe.application.RunProfile;

/**
 * This class overrides the serviceRequest() method.
 */
public class FilesystemWatcherService extends AppletService
{
    public String [] configFileToArgs(File infile) throws IOException
    {
        List<String> allLines = Files.readLines(infile, StandardCharsets.UTF_8);
        
        return linesToArgs(allLines);
    }
    
    public String [] linesToArgs(List<String> allLines)
    {
        List <String> lines = allLines
                        .stream()
                        .filter(line -> !line.startsWith("#") )        
                        .collect( Collectors.toList() );

        List<String> propertyLines = lines.stream()
                                    .filter(l -> l.contains("="))
                                    .collect( Collectors.toList() );

        List<String> tokens = new ArrayList();

        for(String line : propertyLines)
        {
            String [] split = line.split("=");

            tokens .add ("--" + split[0]);

            tokens.add(split[1]);
        }

        return tokens.toArray( new String[0] );        
    }

    public void serviceRequest(RunProfile runProfile) throws Exception
    {
        System.out.println("hello world");
    }
}