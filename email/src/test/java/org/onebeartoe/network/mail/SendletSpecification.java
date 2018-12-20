
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

    private String [] allArguements(SendetteRunProfile rp)
    {
        String [] args = {"--attachment", "pom.xml",
                            "--messageText", "",
                            "--subject", "subject",
                            "--smtpPropertiesPath", rp.getSmtpPropertiesPath(),
                            "--to", "face.recipient@fake-host.tdl"
                         };
        
        return args;
    }
    
    @BeforeTest
    public void setup()
    {
        implementation = new Sendlet();
    }
    
    @Test(expectedExceptions = AuthenticationFailedException.class)
    public void execute_fail_badCredentials() throws Exception
    {
        SendetteRunProfile rp = new SendetteRunProfile();
        
        String path = "src/test/resources/fake-smtp.properties";
        rp.setSmtpPropertiesPath(path);
        
        String[] args = allArguements(rp);
        
        implementation.execute(args);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void execute_fail_noCredentials() throws Exception
    {
        SendetteRunProfile rp = new SendetteRunProfile();
        
        String path = "src/test/resources/no-smtp.properties";
        rp.setSmtpPropertiesPath(path);
        
        String[] args = allArguements(rp);
        
        implementation.execute(args);
    }    
}
