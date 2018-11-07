
package org.onebeartoe.maven.system.dependencies;

import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class SystemDependenciesServiceSpecification
{
    private SystemDependenciesService implementation;
    
    public SystemDependenciesServiceSpecification()
    {
        implementation = new SystemDependenciesService();
    }
    
    @Test(groups = {"unit"})
    public void serviceRequest() throws Exception
    {
        SystemDependenciesRunProfile runProfile = new SystemDependenciesRunProfile();
        runProfile.setProjectRoot(".");
        runProfile.setJarSubpath("target/");
        
        implementation.serviceRequest(runProfile);
    }
}
