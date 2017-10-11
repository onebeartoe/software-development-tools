
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
        
        String date = null;
        
        CommandResults results = command.execute();
        
        return date;
    }
}
