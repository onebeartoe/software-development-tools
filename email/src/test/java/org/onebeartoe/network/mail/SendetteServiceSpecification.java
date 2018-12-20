
package org.onebeartoe.network.mail;

import javax.mail.MessagingException;
import org.onebeartoe.application.RunProfile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * @author Roberto Marquez
 */
public class SendetteServiceSpecification
{
    private SendetteService implementation;
 
    @BeforeTest
    public void setup()
    {
        implementation = new SendetteService();
    }
    
    @Test
    public void serviceRequest() throws MessagingException
    {
        RunProfile rp = new SendetteRunProfile();
        
        implementation.serviceRequest(rp);
    }
}
