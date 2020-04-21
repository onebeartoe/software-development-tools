
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

    private String [] allArguments(SendetteRunProfile rp)
    {
        String [] args = {"--attachment", "pom.xml",
                            "--messageText", "",
                            "--subject", "subject",
                            "--smtpPropertiesPath", rp.getSmtpPropertiesPath(),
                            "--to", "face.recipient@fake-host.tdl"
                         };
        
        return args;
    }
    
    private String [] allArgumentsRemoveMessageText(SendetteRunProfile rp)
    {
        String [] allArgs = allArguments(rp);
        
        String [] args = null;
        
        return args;
    }
    
    @BeforeTest
    public void setup()
    {
        implementation = new Sendlet();
    }
    
    //TODO: MOVE THIS TO AN INTEGRATION TEST
//    @Test(enabled = false)
    @Test(enabled = false,
        expectedExceptions = AuthenticationFailedException.class)
    public void execute_fail_badCredentials() throws Exception
    {
        SendetteRunProfile rp = new SendetteRunProfile();
        
        String path = "src/test/resources/fake-smtp.properties";
        rp.setSmtpPropertiesPath(path);
        
        String[] args = allArguments(rp);
        
        implementation.execute(args);
    }
    
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void execute_fail_noCredentials() throws Exception
    {
        SendetteRunProfile rp = new SendetteRunProfile();
        
        String path = "src/test/resources/no-smtp.properties";
        rp.setSmtpPropertiesPath(path);
        
        String[] args = allArguments(rp);
        
        implementation.execute(args);
    }

    @Test
    public void execute_noAttachment()
    {
        
        String [] args = new SendletArgumentsBuilder()
        .build();
        
// is this correct?
        implementation.equals(args);
    }

    @Test
    public void execute_noMessageText()
    {
        
    }
}
