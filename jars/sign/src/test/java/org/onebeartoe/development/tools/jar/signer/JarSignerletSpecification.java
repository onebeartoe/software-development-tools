
package org.onebeartoe.development.tools.jar.signer;
        
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class JarSignerletSpecification 
{
    private final JarSignerlet implementation;
    
    public JarSignerletSpecification()
    {
        implementation = new JarSignerlet();
    }

    @Test(groups = {"unit"})
    public void execute() throws Exception
    {
        String [] args = {"--alias", "a", "--keystore", "ks", "--storepass", "sp", "remaining-arg-0"};
        
        implementation.execute(args);
    }
}



