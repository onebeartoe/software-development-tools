
package org.onebeartoe.tools.versioning.subversion;

import java.util.Arrays;
import java.util.List;
import org.onebeartoe.system.CommandResults;
import org.onebeartoe.system.command.SystemCommand;
import org.onebeartoe.system.command.SystemCommandProfile;

/**
 *
 * @author Roberto Marquez
 */
public class SubversionService
{
    /**
     * This Subversion svn command returns the first revision for a repository path.
     * 
     * The command and parameters are from this stackoverflow answer:
     * 
     *      svn log -r 1:HEAD --limit 1 <reposiotry_URL>
     * 
     *      https://stackoverflow.com/a/1632214/803890
     * 
     * @param repositoryPath
     * @return a string representing the date of creation for the repository path.
     */
    public String creationDate(String repositoryPath) throws Exception
    {
        SystemCommandProfile profile = new SystemCommandProfile();
        String c = "svn log -r 1:HEAD --limit 1 " + repositoryPath;
        String [] strs = c.split("\\s+");
        List <String> commandAndArgs = Arrays.asList(strs);
        
        profile.commandAndArgs = commandAndArgs;
        profile.processStdErr = true;
        profile.processStdOut = true;
        
        SystemCommand command = new SystemCommand();
        command.setCommandProfile(profile);
        
        CommandResults results = command.execute();
        
// results.processedStdOut is something like the following        
//------------------------------------------------------------------------
//r11 | fisto | 2009-05-21 13:13:35 -0500 (Thu, 21 May 2009) | 1 line
//
//intial import
//------------------------------------------------------------------------        

//        System.out.println("stdout: " + "\n" + results.processedStdOut);

        int firstPipe = results.processedStdOut.indexOf("|") + 1;
        int secondPipe = results.processedStdOut.indexOf("|", firstPipe ) + 1;
        int lastPipe = results.processedStdOut.indexOf("|", secondPipe);
        
        int begin = secondPipe;
        int end = lastPipe;
        
        String date = results.processedStdOut.substring(begin, end);

        date = date.trim();
        
        return date;
    }
}
