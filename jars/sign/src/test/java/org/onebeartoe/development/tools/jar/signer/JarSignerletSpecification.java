
package org.onebeartoe.development.tools.jar.signer;
        
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
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
    
//    @Test(groups = {"unit"})
//    public void buildOptions()
//    {
//        implementation.buildOptions();
//    }
//    
//    @Test(groups = {"unit"})
//    public void parseRunProfile() throws ParseException
//    {
//        String [] args = {"path1"};
//        Options options = new Options();
//        
//        implementation.parseRunProfile(args, options);
//    }
    
//TODO: Why not just vall implementatin.execute() to test all methods?
    
    @Test(groups = {"unit"})
    public void execute() throws Exception
    {
        String [] args = {"path1"};
        
        implementation.execute(args);
    }
}
