
package org.onebeartoe.maven.system.dependencies;

import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class SystemDependenciesFromJarDirectorySpecification
{
    private SystemDependenciesFromJarDirectory implementation;
    
    public SystemDependenciesFromJarDirectorySpecification()
    {
        implementation = new SystemDependenciesFromJarDirectory();
    }
    
   @Test(groups = {"unit"})
    public void  execute() throws Exception
    {
        String [] args = {".", "target"};
        
        implementation.execute(args);
    }
}
