
package org.onebeartoe.network.mail;

import javax.mail.AuthenticationFailedException;
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
    
    @Test(expectedExceptions = AuthenticationFailedException.class)
    public void execute_fail_badCredentials() throws Exception
    {
        String [] args = {"--attachment", "pom.xml",
                            "--messageText", "body",
                            "--subject", "subject",
                            "--smtpPropertiesPath", "src/test/resources/fake-smtp.properties",
                            "--to", "face.recipient@fake-host.tdl"
                         };
        
        implementation.execute(args);
    }
}
