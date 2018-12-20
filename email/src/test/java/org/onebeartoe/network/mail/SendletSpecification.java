
package org.onebeartoe.network.mail;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class SendletSpecification
{
    private Sendlet implementation;
    
    @BeforeTest
    public void setup()
    {
        implementation = new Sendlet();
    }
    
    @Test
    public void execute() throws Exception
    {
        String [] args = {};
        
        implementation.execute(args);
    }
}
